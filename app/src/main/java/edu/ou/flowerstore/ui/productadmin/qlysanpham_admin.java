package edu.ou.flowerstore.ui.productadmin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.ou.flowerstore.databinding.ActivityQlySanPhamAdminBinding;

public class QlySanPhamAdminActivity extends AppCompatActivity {
    private ActivityQlySanPhamAdminBinding binding;
    private List<Product> products;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQlySanPhamAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        products = new ArrayList<>();
        adapter = new ProductAdapter(products, this, new ProductAdapter.OnProductItemClickListener() { // Thêm listener
            @Override
            public void onUpdateProduct(Product product) {
                // Xử lý cập nhật sản phẩm
                Toast.makeText(QlySanPhamAdminActivity.this, "Cập nhật sản phẩm: " + product.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteProduct(Product product) {
                // Xử lý xóa sản phẩm
                Toast.makeText(QlySanPhamAdminActivity.this, "Xóa sản phẩm: " + product.getName(), Toast.LENGTH_SHORT).show();
                deleteProduct(product);
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        binding.addProductBtn.setOnClickListener(v -> {
            AddProductDialog dialog = new AddProductDialog(this, product -> {
                FirebaseFirestore.getInstance().collection("products")
                        .add(product)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                                fetchProducts();
                            } else {
                                Toast.makeText(this, "Thêm sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
            });
            dialog.show();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchProducts();
    }

    private void fetchProducts() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        products.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Product product = document.toObject(Product.class);
                            products.add(product);
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

    private void deleteProduct(Product product) {
        FirebaseFirestore.getInstance().collection("products")
                .document(product.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Sản phẩm đã được xóa!", Toast.LENGTH_SHORT).show();
                    fetchProducts();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Xóa sản phẩm thất bại!", Toast.LENGTH_SHORT).show());
    }
}
