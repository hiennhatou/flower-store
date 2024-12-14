package edu.ou.flowerstore.ui.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.ActivityAdminBinding;
import edu.ou.flowerstore.ui.category.CategoryManagementActivity;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Thiết lập Edge-to-Edge UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Quay lại trang trước
        binding.backBtn.setOnClickListener(v -> finish());

        // Chuyển sang màn hình quản lý danh mục
        binding.categoryManagementBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, CategoryManagementActivity.class);
            startActivity(intent);
        });
    }
}
