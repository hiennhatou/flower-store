package edu.ou.flowerstore.ui.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.ActivityAdminBinding;
import edu.ou.flowerstore.ui.admin.orders.OrderManagementActivity;
import edu.ou.flowerstore.ui.admin.products.AdminProductManagementActivity;
import edu.ou.flowerstore.ui.admin.users.AdminUserMangementActivity;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.backBtn.setOnClickListener(v -> {
            finish();
        });


        binding.orderManagementBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, OrderManagementActivity.class);
            startActivity(intent);
        });

        binding.productManagementBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminProductManagementActivity.class);
            startActivity(intent);
        });

        binding.userManagementBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, AdminUserMangementActivity.class));
        });
    }
}
