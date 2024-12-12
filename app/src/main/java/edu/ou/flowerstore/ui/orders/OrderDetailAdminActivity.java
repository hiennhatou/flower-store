package edu.ou.flowerstore.ui.orders;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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

public class OrderDetailAdminActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    private TextView orderIdTextView, customerNameTextView, shippingAddressTextView, totalPriceTextView, orderStatusTextView;
    private Button rejectButton, completeButton;
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_admin);

        db = FirebaseFirestore.getInstance();

        orderIdTextView = findViewById(R.id.order_id);
        customerNameTextView = findViewById(R.id.customer_name);
        shippingAddressTextView = findViewById(R.id.shipping_address);
        totalPriceTextView = findViewById(R.id.total_price);
        orderStatusTextView = findViewById(R.id.order_status);
        rejectButton = findViewById(R.id.reject_button);
        completeButton = findViewById(R.id.complete_button);

        productRecyclerView = findViewById(R.id.product_list);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productList);
        productRecyclerView.setAdapter(productAdapter);

        loadOrderDetails("tL5yTg5nE5wh7npTenc9"); // ID mẫu trong Firestore, test order khác thì đổi ở đây

        rejectButton.setOnClickListener(v -> {
            updateOrderStatus("denied", null);
            Toast.makeText(this, "Đã từ chối đơn hàng!", Toast.LENGTH_SHORT).show();
        });

        completeButton.setOnClickListener(v -> {
            updateOrderStatus("completed", System.currentTimeMillis());
            Toast.makeText(this, "Đã hoàn thành đơn hàng!", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadOrderDetails(String orderId) {
        db.collection("orders").document(orderId).get().addOnSuccessListener(orderSnapshot -> {
            if (orderSnapshot.exists()) {
                orderIdTextView.setText("Mã đơn hàng: " + orderId);
                customerNameTextView.setText("Tên khách hàng: Nguyễn Văn A");
                shippingAddressTextView.setText("Địa chỉ giao hàng: 1 Quang Trung, P.1, Q.1, TP.HCM");

                String status = orderSnapshot.getString("status");
                orderStatusTextView.setText("Trạng thái: " + translateStatus(status));

                // Xử lý kích hoạt/tắt nút dựa trên trạng thái
                if ("pending".equals(status)) {
                    updateButtonState(rejectButton, true);
                    updateButtonState(completeButton, true);
                } else if ("paying".equals(status)) {
                    updateButtonState(rejectButton, true);
                    updateButtonState(completeButton, false);
                } else {
                    updateButtonState(rejectButton, false);
                    updateButtonState(completeButton, false);
                }

                List<Map<String, Object>> products = (List<Map<String, Object>>) orderSnapshot.get("products");
                if (products != null) {
                    AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
                    for (Map<String, Object> productMap : products) {
                        DocumentReference productRef = (DocumentReference) productMap.get("product");
                        String productId = productRef.getId();
                        db.collection("products").document(productId).get().addOnSuccessListener(productSnapshot -> {
                            if (productSnapshot.exists()) {
                                String productName = productSnapshot.getString("name");
                                String productDescription = productSnapshot.getString("description");
                                double productPrice = productSnapshot.getDouble("price");
                                List<String> images = (List<String>) productSnapshot.get("image");
                                String productImage = images != null && !images.isEmpty() ? images.get(0) : null;
                                int quantity = ((Long) productMap.get("quantity")).intValue();
                                totalPrice.updateAndGet(v -> v + productPrice * quantity);

                                productList.add(new Product(productName, productDescription, String.valueOf(productPrice), productImage, quantity));
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
        String orderId = "tL5yTg5nE5wh7npTenc9"; // ID mẫu, đổi ở đây theo id order ở trên để cập nhật
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
            button.setBackgroundTintList(getResources().getColorStateList(R.color.green, null)); // Màu xanh
            button.setTextColor(getResources().getColor(R.color.white, null)); // Chữ màu trắng
        } else {
            button.setBackgroundTintList(getResources().getColorStateList(R.color.grey, null)); // Màu xám
            button.setTextColor(getResources().getColor(R.color.grey_light, null)); // Chữ xám
        }
    }
}
