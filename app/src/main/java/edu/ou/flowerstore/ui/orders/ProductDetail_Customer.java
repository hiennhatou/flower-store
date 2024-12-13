package edu.ou.flowerstore.ui.orders;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import edu.ou.flowerstore.R;

public class ProductDetail_Customer extends AppCompatActivity {

    private FirebaseFirestore db;

    private TextView orderIdTextView, customerNameTextView, shippingAddressTextView, totalPriceTextView, orderStatusTextView;
    private Button btn_checkout;
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_customer);

        db = FirebaseFirestore.getInstance();

        orderIdTextView = findViewById(R.id.txt_order_id);
        customerNameTextView = findViewById(R.id.txt_customer_name);
        shippingAddressTextView = findViewById(R.id.txt_address);
        totalPriceTextView = findViewById(R.id.txt_total_price);
        orderStatusTextView = findViewById(R.id.txt_status);
        btn_checkout = findViewById(R.id.btn_checkout);

        productRecyclerView = findViewById(R.id.product_list);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, this);
        productRecyclerView.setAdapter(productAdapter);

        loadOrderDetails("SEDfZJRBMrqnmaHsQqgs"); // ID mẫu trong Firestore, test order khác thì đổi ở đây
    }

    private void loadOrderDetails(String orderId) {
        db.collection("orders").document(orderId).get().addOnSuccessListener(orderSnapshot -> {
            if (orderSnapshot.exists()) {
                // Cập nhật thông tin mã đơn hàng
                orderIdTextView.setText("Mã đơn hàng: " + orderId);


                DocumentReference userRef = (DocumentReference) orderSnapshot.get("user");
                if (userRef != null) {
                    userRef.get().addOnSuccessListener(userSnapshot -> {
                        if (userSnapshot.exists()) {
                            String customerName = userSnapshot.getString("name");
                            customerNameTextView.setText("Tên khách hàng: " + customerName);
                        } else {
                            customerNameTextView.setText("Tên khách hàng: N/A");
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(this, "Không thể tải thông tin khách hàng!", Toast.LENGTH_SHORT).show();
                    });
                }

                // Lấy địa chỉ giao hàng
                Map<String, Object> addressMap = (Map<String, Object>) orderSnapshot.get("address");
                if (addressMap != null) {
                    String address = (String) addressMap.get("address");
                    String city = (String) addressMap.get("city");
                    String district = (String) addressMap.get("district");
                    String ward = (String) addressMap.get("ward");

                    String fullAddress = address + ", " + district + ", " + ward + ", " + city;
                    shippingAddressTextView.setText("Địa chỉ giao hàng: " + fullAddress);
                }

                // Cập nhật trạng thái đơn hàng
                String status = orderSnapshot.getString("status");
                orderStatusTextView.setText("Trạng thái: " + translateStatus(status));

                // kích hoạt/tắt nút dựa trên trạng thái
                if ("pending".equals(status) || "paying".equals(status)) {
                    updateButtonState(btn_checkout, true);
                    btn_checkout.invalidate(); // Làm mới giao diện
                } else {
                    updateButtonState(btn_checkout, false);
                    btn_checkout.invalidate(); // Làm mới giao diện
                }


                // Cập nhật danh sách sản phẩm
                List<Map<String, Object>> products = (List<Map<String, Object>>) orderSnapshot.get("products");
                if (products != null) {
                    AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
                    for (Map<String, Object> productMap : products) {
                        DocumentReference productRef = (DocumentReference) productMap.get("product");
                        String productId = productRef.getId();
                        db.collection("products").document(productId).get().addOnSuccessListener(productSnapshot -> {
                            if (productSnapshot.exists()) {
                                String productName = productSnapshot.getString("name");
                                double productPrice = productSnapshot.getDouble("price");
                                List<String> images = (List<String>) productSnapshot.get("image");
                                String productImage = images != null && !images.isEmpty() ? images.get(0) : null;
                                int quantity = ((Long) productMap.get("quantity")).intValue();
                                totalPrice.updateAndGet(v -> v + productPrice * quantity);

                                productList.add(new Product(quantity, productImage, String.valueOf(productPrice), productName));
                                productAdapter.notifyDataSetChanged();

                                totalPriceTextView.setText("Tổng tiền: " + String.format("%,.0f", totalPrice.get()) + " đ");
                            }
                        }).addOnFailureListener(e -> {
                            Toast.makeText(this, "Không thể tải thông tin sản phẩm!", Toast.LENGTH_SHORT).show();
                        });
                    }
                }
            }
        }).addOnFailureListener(e -> Toast.makeText(this, "Không thể tải thông tin đơn hàng!", Toast.LENGTH_SHORT).show());
    }


    private String translateStatus(String status) {
        switch (status) {
            case "pending": return "Đang đợi xử lý";
            case "paying": return "Đang đợi thanh toán";
            case "completed": return "Đã hoàn thành";
            case "denied": return "Đã từ chối";
            default: return "Không xác định";
        }
    }

    private void updateOrderStatus(String status, Long completedDate) {
        String orderId = "SEDfZJRBMrqnmaHsQqgs"; // ID mẫu, đổi ở đây theo id order ở trên để cập nhật
        Map<String, Object> updates = new HashMap<>();
        updates.put("status", status);
        if (completedDate != null) {
            updates.put("completed_date", completedDate);
        }
        db.collection("orders").document(orderId).update(updates).addOnSuccessListener(aVoid -> {
            loadOrderDetails(orderId); // Load lại UI
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Cập nhật trạng thái thất bại!", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateButtonState(Button button, boolean isEnabled) {
        button.setEnabled(isEnabled);
        if (isEnabled) {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red, null))); // Màu đỏ
        } else {
            button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey, null))); // Màu xám
        }

    }


}