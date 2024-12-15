package edu.ou.flowerstore.ui.orders;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Query;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import edu.ou.flowerstore.FlowerStoreApplication;
import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.ActivityCustomerOrdersBinding;
import edu.ou.flowerstore.utils.firebase.AppFirebase;

public class CustomerOrdersActivity extends AppCompatActivity {
    ActivityCustomerOrdersBinding binding;
    List<CustomerOrderAdapter.CustomerOrderOverview> customerOrderOverviewList = new ArrayList<>();
    CustomerOrderAdapter adapter = new CustomerOrderAdapter(customerOrderOverviewList);
    FlowerStoreApplication application = FlowerStoreApplication.getInstance();
    AppFirebase appFirebase = application.getAppFirebase();
    FirebaseUser user = appFirebase.getFirebaseAuth().getCurrentUser();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("vi", "vn"));
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "vn"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCustomerOrdersBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.orders.setAdapter(adapter);
        binding.orders.setLayoutManager(new LinearLayoutManager(this));
        binding.backBtn.setOnClickListener(v -> {
            finish();
        });
        if (user == null) finish();
        appFirebase.getOrdersCollection().whereEqualTo("user", appFirebase.getUsersCollection().document(user.getUid())).orderBy("created_date", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(task -> {
                    customerOrderOverviewList.clear();
                    if (task.isSuccessful()) {
                        task.getResult().getDocuments().forEach(doc -> {
                            List<HashMap<String, Object>> listProduct = (List<HashMap<String, Object>>) doc.get("products");
                            if (listProduct == null) return;
                            String id = doc.getId();
                            String state = doc.getString("status");
                            String date = doc.getDate("created_date") == null ? null : dateFormat.format(doc.getDate("created_date"));
                            String name = listProduct.stream().map(product -> (String) product.get("name")).collect(Collectors.joining(", "));
                            long totalPrice = listProduct.stream().map(product -> (Long) product.get("unitPrice") * (Long) product.get("quantity")).reduce(0L, Long::sum);
                            String price = currencyFormat.format(totalPrice);
                            customerOrderOverviewList.add(new CustomerOrderAdapter.CustomerOrderOverview(id, name, date, state, price));
                        });
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}