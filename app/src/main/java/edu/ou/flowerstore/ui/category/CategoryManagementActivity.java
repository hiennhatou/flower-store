package edu.ou.flowerstore.ui.category;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
        adapter = new CategoryAdapter(categories, this, new CategoryAdapter.OnCategoryItemClickListener() {
            @Override
            public void onUpdateCategory(Category category) {
                // Hiển thị Dialog cập nhật
                showUpdateDialog(category);
            }

            @Override
            public void onDeleteCategory(Category category, int position) {
                // Hiển thị AlertDialog cảnh báo xóa
                showDeleteDialog(category, position);
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
                                fetchCategories(); // Làm mới danh sách danh mục
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
                            category.setCode(document.getId()); // Set mã danh mục (document ID)
                            categories.add(category);
                        }
                        adapter.notifyDataSetChanged(); // Cập nhật RecyclerView sau khi lấy xong dữ liệu
                    } else {
                        Toast.makeText(this, "Không thể lấy dữ liệu từ Firestore!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    private void showUpdateDialog(Category category) {

        final android.widget.EditText editTextName = new android.widget.EditText(this);
        final android.widget.EditText editTextCode = new android.widget.EditText(this);


        editTextName.setText(category.getName());
        editTextCode.setText(category.getCode());


        new AlertDialog.Builder(this)
                .setTitle("Cập nhật danh mục")
                .setMessage("Cập nhật mã và tên danh mục")
                .setView(editTextName)
                .setPositiveButton("Cập nhật", (dialog, which) -> {
                    // Lấy giá trị mới từ EditText
                    String newName = editTextName.getText().toString();
                    String newCode = editTextCode.getText().toString();

                    // Kiểm tra tính hợp lệ của dữ liệu nhập
                    if (newName.isEmpty() || newCode.isEmpty()) {
                        Toast.makeText(CategoryManagementActivity.this, "Mã và tên danh mục không được để trống", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Cập nhật dữ liệu trong Firestore
                    FirebaseFirestore.getInstance().collection("categories")
                            .document(category.getCode()) // Dùng mã danh mục hiện tại làm document ID
                            .update("name", newName, "code", newCode) // Cập nhật tên và mã danh mục
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {

                                    category.setName(newName);
                                    category.setCode(newCode);
                                    adapter.notifyItemChanged(categories.indexOf(category));
                                    Toast.makeText(CategoryManagementActivity.this, "Cập nhật danh mục thành công!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(CategoryManagementActivity.this, "Cập nhật danh mục thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(CategoryManagementActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }


    private void showDeleteDialog(Category category, int position) {
        new AlertDialog.Builder(CategoryManagementActivity.this)
                .setTitle("Xóa danh mục")
                .setMessage("Bạn có chắc chắn muốn xóa danh mục: " + category.getName() + " không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    String categoryCode = category.getCode();

                    if (categoryCode == null || categoryCode.isEmpty()) {
                        Toast.makeText(CategoryManagementActivity.this, "Mã danh mục không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Xóa danh mục khỏi Firestore
                    FirebaseFirestore.getInstance().collection("categories")
                            .document(categoryCode) // Dùng mã danh mục làm document ID
                            .delete()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Xóa danh mục khỏi danh sách và cập nhật RecyclerView
                                    categories.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    Toast.makeText(CategoryManagementActivity.this, "Xóa danh mục thành công!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(CategoryManagementActivity.this, "Xóa danh mục thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(CategoryManagementActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton("Không", null)
                .show();
    }
}
