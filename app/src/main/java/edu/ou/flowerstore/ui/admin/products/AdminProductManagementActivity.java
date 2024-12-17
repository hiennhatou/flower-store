package edu.ou.flowerstore.ui.admin.products;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import edu.ou.flowerstore.databinding.ActivityAdminProductManagementBinding;

public class AdminProductManagementActivity extends AppCompatActivity {
    ActivityAdminProductManagementBinding binding;
    AdminProductManagementViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAdminProductManagementBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(AdminProductManagementViewModel.class);

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel.getProductAdapter().setOnClick(product -> {
            Intent intent = new Intent(AdminProductManagementActivity.this, AdminProductDetailActivity.class);
            intent.putExtra("id", product.id);
            startActivity(intent);
        });

        binding.backBtn.setOnClickListener(v -> finish());
        binding.productList.setLayoutManager(new LinearLayoutManager(this));
        binding.productList.setAdapter(viewModel.getProductAdapter());
        binding.addProductBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AdminProductManagementActivity.this, AdminProductDetailActivity.class);
            startActivity(intent);
        });
    }
}