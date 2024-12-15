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
        adapter = new ProductAdapter(products, this, new ProductAdapter.OnProductItemClickListener() {
            @Override
            public void onEditProduct(Product product) {
                // Xử lý cập nhật sản phẩm
                EditProductDialog dialog = new EditProductDialog(QlySanPhamAdminActivity.this, product, updatedProduct -> {
                    FirebaseFirestore.getInstance().collection("products")
                            .document(product.getMaSanPham())
                            .set(updatedProduct)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(QlySanPhamAdminActivity.this, "Cập nhật sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                                fetchProducts();
                            })
                            .addOnFailureListener(e -> Toast.makeText(QlySanPhamAdminActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show());
                });
                dialog.show();
            }

            @Override
            public void onDeleteProduct(Product product) {
                FirebaseFirestore.getInstance().collection("products")
                        .document(product.getMaSanPham())
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(QlySanPhamAdminActivity.this, "Xóa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                            fetchProducts();
                        })
                        .addOnFailureListener(e -> Toast.makeText(QlySanPhamAdminActivity.this, "Xóa sản phẩm thất bại!", Toast.LENGTH_SHORT).show());
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        // Thêm sản phẩm
        binding.addProductBtn.setOnClickListener(v -> {
            AddProductDialog dialog = new AddProductDialog(this, product -> {
                FirebaseFirestore.getInstance().collection("products")
                        .add(product)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                            fetchProducts();
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, "Thêm sản phẩm thất bại!", Toast.LENGTH_SHORT).show());
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
                            product.setMaSanPham(document.getId());
                            products.add(product);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Không thể lấy dữ liệu từ Firestore!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
