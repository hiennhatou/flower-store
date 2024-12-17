package edu.ou.flowerstore.ui.admin.orders;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.firebase.firestore.Query;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import edu.ou.flowerstore.FlowerStoreApplication;
import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.ActivityOrderManagementBinding;
import edu.ou.flowerstore.utils.firebase.AppFirebase;

public class OrderManagementActivity extends AppCompatActivity implements OrderAdapter.OnOrderClickListener {
    ActivityOrderManagementBinding binding;
    List<OrderAdapter.OrderOverview> orderList = new ArrayList<>();
    OrderAdapter adapter;
    AppFirebase appFirebase = FlowerStoreApplication.getInstance().getAppFirebase();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.backBtn.setOnClickListener(v -> finish());
        binding.orders.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderAdapter(orderList, this);
        binding.orders.setAdapter(adapter);

        loadOrders();
    }

    private void loadOrders() {
        appFirebase.getOrdersCollection()
                .orderBy("created_date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    orderList.clear();
                    if (task.isSuccessful()) {
                        task.getResult().forEach(doc -> {
                            List<HashMap<String, Object>> products = (List<HashMap<String, Object>>) doc.get("products");
                            if (products == null) return;

                            String id = doc.getId();
                            String name = products.stream().map(p -> (String) p.get("name")).reduce("", (a, b) -> a + ", " + b);
                            String date = doc.getDate("created_date") != null ? dateFormat.format(doc.getDate("created_date")) : "N/A";
                            String status = doc.getString("status");
                            long totalPrice = products.stream()
                                    .mapToLong(p -> ((Long) p.get("unitPrice")) * ((Long) p.get("quantity")))
                                    .sum();
                            String price = currencyFormat.format(totalPrice);

                            orderList.add(new OrderAdapter.OrderOverview(id, name, date, status, price));
                        });
                    }
                    adapter.notifyDataSetChanged();
                });
    }

    @Override
    public void onOrderClick(String orderId) {
        Intent intent = new Intent(this, AdminOrderDetailActivity.class);
        intent.putExtra("orderId", orderId); // Truyền orderId
        startActivity(intent);
    }
}
