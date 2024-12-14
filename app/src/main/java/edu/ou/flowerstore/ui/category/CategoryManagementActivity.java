package edu.ou.flowerstore.ui.category;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.ou.flowerstore.databinding.ActivityCategoryManagementBinding;

public class CategoryManagementActivity extends AppCompatActivity {
    private ActivityCategoryManagementBinding binding;
    private List<Category> categories;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categories = new ArrayList<>();
        adapter = new CategoryAdapter(categories, this, new CategoryAdapter.OnCategoryItemClickListener() { // Thêm listener
            @Override
            public void onUpdateCategory(Category category) {
                // Xử lý cập nhật danh mục
                Toast.makeText(CategoryManagementActivity.this, "Cập nhật danh mục: " + category.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteCategory(Category category) {
                // Xử lý xóa danh mục
                Toast.makeText(CategoryManagementActivity.this, "Xóa danh mục: " + category.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        binding.addCategoryBtn.setOnClickListener(v -> {
            AddCategoryDialog dialog = new AddCategoryDialog(this, category -> {
                FirebaseFirestore.getInstance().collection("categories")
                        .add(category)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "Thêm danh mục thành công!", Toast.LENGTH_SHORT).show();
                                fetchCategories();
                            } else {
                                Toast.makeText(this, "Thêm danh mục thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });

            });
            dialog.show();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchCategories();
    }

    private void fetchCategories() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("categories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        categories.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Category category = document.toObject(Category.class);
                            categories.add(category);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Không thể lấy dữ liệu từ Firestore!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}