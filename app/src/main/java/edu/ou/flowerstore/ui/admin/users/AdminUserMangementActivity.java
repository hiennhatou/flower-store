package edu.ou.flowerstore.ui.admin.users;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.ActivityAdminProductManagementBinding;
import edu.ou.flowerstore.databinding.ActivityAdminUserMangementBinding;
import edu.ou.flowerstore.utils.firebase.AppFirebase;

public class AdminUserMangementActivity extends AppCompatActivity {
    ActivityAdminUserMangementBinding binding;
    AdminUserManagementViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAdminUserMangementBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(AdminUserManagementViewModel.class);

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.backBtn.setOnClickListener(v -> finish());
        binding.userList.setLayoutManager(new LinearLayoutManager(this));
        viewModel.adapter.setOnClick(user -> {
            Intent intent = new Intent(AdminUserMangementActivity.this, AdminUserDetailActivity.class);
            intent.putExtra("id", user.id);
            startActivity(intent);
        });
        binding.userList.setAdapter(viewModel.getAdapter());
    }
}