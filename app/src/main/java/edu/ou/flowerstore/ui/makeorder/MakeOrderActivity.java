package edu.ou.flowerstore.ui.makeorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import edu.ou.flowerstore.FlowerStoreApplication;
import edu.ou.flowerstore.databinding.ActivityMakeOrderBinding;
import edu.ou.flowerstore.ui.authen.Login;
import edu.ou.flowerstore.ui.cart.CartItem;
import edu.ou.flowerstore.ui.payment.ZaloPayPaymentActivity;
import edu.ou.flowerstore.utils.firebase.AppFirebase;

public class MakeOrderActivity extends AppCompatActivity {
    private ActivityMakeOrderBinding binding;
    private LiveData<List<CartItem>> cart;
    private FlowerStoreApplication application;
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "vn"));
    private HashMap<String, Object> address = (new HashMap<>());
    private String phoneNumber;
    private boolean isOpenInformInput = (false);
    private boolean isFullInform = (false);
    private Long totalPrice;
    private AppFirebase appFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakeOrderBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        application = FlowerStoreApplication.getInstance();
        cart = application.getCartItemsLiveData();
        appFirebase = application.getAppFirebase();
        if (application.getAppFirebase().getFirebaseAuth().getCurrentUser() == null) {
            startActivity(new Intent(MakeOrderActivity.this, Login.class));
            finish();
        }

        MakeOrderAdapter makeOrderAdapter = new MakeOrderAdapter(cart.getValue());

        cart.observe(this, cartItems -> {
            makeOrderAdapter.setProducts(cartItems);
            makeOrderAdapter.notifyDataSetChanged();
            totalPrice = cartItems.stream().map(cartItem -> cartItem.getPrice() * cartItem.getQuantity()).reduce(0L, (a, b) -> a + b);
            binding.totalPrice.setText(numberFormat.format(totalPrice));
        });

        binding.addAddressBtn.setOnClickListener(v -> {
            binding.cityEdt.setText((String) address.get("city"));
            binding.districtEdt.setText((String) address.get("district"));
            binding.wardEdt.setText((String) address.get("ward"));
            binding.addressEdt.setText((String) address.get("address"));
            binding.phoneNumberEdt.setText(phoneNumber);
            isOpenInformInput = true;
            renderInform();
        });

        binding.addressDetail.setOnClickListener(v -> {
            isOpenInformInput = true;
            renderInform();
        });

        binding.addressDetail.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (binding.addressDetail.getVisibility() == View.VISIBLE) {
                binding.addressTv.setText(String.format("Địa chí: %s, %s, %s, %s", address.get("address"), address.get("ward"), address.get("district"), address.get("city")));
                binding.phoneNumberTv.setText(String.format("Số điện thoại: %s", phoneNumber));
            }
        });

        binding.saveInform.setOnClickListener(v -> {
            saveInform();
        });

        binding.itemList.setAdapter(makeOrderAdapter);
        binding.itemList.setLayoutManager(new LinearLayoutManager(this));

        binding.backBtn.setOnClickListener(v -> {
            finish();
        });

        binding.makeOrderBtn.setOnClickListener(v -> {
            createOrder();
        });
    }

    private void renderInform() {
        if (isOpenInformInput) {
            binding.addAddressBtn.setVisibility(View.GONE);
            binding.addAddress.setVisibility(View.VISIBLE);
            binding.addressDetail.setVisibility(View.GONE);
        } else {
            binding.addAddress.setVisibility(View.GONE);
            if (isFullInform) {
                binding.addAddressBtn.setVisibility(View.GONE);
                binding.addressDetail.setVisibility(View.VISIBLE);
            } else {
                binding.addAddressBtn.setVisibility(View.VISIBLE);
                binding.addressDetail.setVisibility(View.GONE);
            }
        }

        binding.makeOrderBtn.setEnabled(isFullInform);
    }

    private boolean isEnoughInformation() {
        return address.containsKey("city") && address.containsKey("district") && address.containsKey("ward") && address.containsKey("address") && phoneNumber != null;
    }

    private void saveInform() {
        {
            String city = binding.cityEdt.getText() != null ? binding.cityEdt.getText().toString().trim() : null,
                    district = binding.districtEdt.getText() != null ? binding.districtEdt.getText().toString().trim() : null,
                    ward = binding.wardEdt.getText() != null ? binding.wardEdt.getText().toString().trim() : null,
                    address = binding.addressEdt.getText() != null ? binding.addressEdt.getText().toString().trim() : null,
                    phoneNumberInp = binding.phoneNumberEdt.getText() != null ? binding.phoneNumberEdt.getText().toString().trim() : null;

            if (city != null && !city.isEmpty() && district != null && !district.isEmpty() && ward != null && !ward.isEmpty() && address != null && !address.isEmpty() && phoneNumberInp != null && !phoneNumberInp.isEmpty()) {
                this.address.put("city", city);
                this.address.put("district", district);
                this.address.put("ward", ward);
                this.address.put("address", address);
                phoneNumber = phoneNumberInp;
                isFullInform = isEnoughInformation();
                isOpenInformInput = false;
                renderInform();
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void createOrder() {
        FirebaseUser user = application.getAppFirebase().getFirebaseAuth().getCurrentUser();
        if (!isEnoughInformation() || appFirebase.getFirebaseAuth().getCurrentUser() == null) {
            Toast.makeText(this, "Lỗi!", Toast.LENGTH_LONG).show();
            return;
        }
        List<HashMap<String, Object>> orderProducts = sequelizeCartProducts();
        HashMap<String, Object> order = new HashMap<>();
        order.put("user", appFirebase.getUsersCollection().document(user.getUid()));
        order.put("status", "paying");
        order.put("created_date", FieldValue.serverTimestamp());
        order.put("updated_date", FieldValue.serverTimestamp());
        order.put("completed_date", null);
        order.put("payment_date", null);
        order.put("address", address);
        order.put("phone_number", phoneNumber);
        order.put("products", orderProducts);
        appFirebase.getOrdersCollection().add(order).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Intent intent = new Intent(MakeOrderActivity.this, ZaloPayPaymentActivity.class);
                intent.putExtra("orderId", task.getResult().getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                application.getRoomDB().cartDAO().deleteAll();
                finish();
            } else {
                Toast.makeText(this, "Lỗi!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<HashMap<String, Object>> sequelizeCartProducts() {
        return application.getCartItemsLiveData().getValue().stream().map(product -> {
            HashMap<String, Object> result = new HashMap<>();
            result.put("name", product.getName());
            result.put("product", appFirebase.getProductsCollection().document(product.getId()));
            result.put("unitPrice", product.getPrice());
            result.put("quantity", product.getQuantity());
            return result;
        }).collect(Collectors.toList());
    }
}