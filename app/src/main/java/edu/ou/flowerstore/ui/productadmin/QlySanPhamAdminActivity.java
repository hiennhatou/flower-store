package edu.ou.flowerstore.ui.productadmin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.ou.flowerstore.R;
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

    private void showAddSanPhamDialog() {
        // Inflate custom layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_product, null);

        // Get references to EditTexts
        EditText etMaSanPham = dialogView.findViewById(R.id.etMaSanPham);
        EditText etTenSP = dialogView.findViewById(R.id.etTenSP);
        EditText etSoLuong = dialogView.findViewById(R.id.etSoLuong);
        EditText etGia = dialogView.findViewById(R.id.etGia);
        EditText etHinhAnh = dialogView.findViewById(R.id.etHinhAnh);
        EditText etMoTa = dialogView.findViewById(R.id.etMoTa);
        EditText etMaLoaiHoa = dialogView.findViewById(R.id.etMaLoaiHoa);

        Button btnThem = dialogView.findViewById(R.id.btnThem);
        Button btnHuy = dialogView.findViewById(R.id.btnHuy);

        // Create AlertDialog
        androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Thêm Sản Phẩm Mới")
                .setView(dialogView)
                .setCancelable(false) // Disable dialog close when clicking outside
                .create();

        // Handle the "Thêm" button click
        btnThem.setOnClickListener(v -> {
            // Get user input
            String maSanPham = etMaSanPham.getText().toString().trim();
            String tenSP = etTenSP.getText().toString().trim();
            String dinhLuong = etSoLuong.getText().toString().trim();
            String gia = etGia.getText().toString().trim();
            String hinhAnh = etHinhAnh.getText().toString().trim();
            String moTa = etMoTa.getText().toString().trim();
            String maLoaiHoa = etMaLoaiHoa.getText().toString().trim();

            // Validate the input
            if (maSanPham.isEmpty() || tenSP.isEmpty() || dinhLuong.isEmpty() || gia.isEmpty() || hinhAnh.isEmpty() || moTa.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create Product object
            Product sanPham = new Product(maSanPham, tenSP, Double.parseDouble(dinhLuong), Double.parseDouble(gia), hinhAnh, moTa, maLoaiHoa);

            // Save to Firebase
            FirebaseDatabase.getInstance().getReference("SanPham")
                    .child(maSanPham)
                    .setValue(sanPham)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Sản phẩm đã được thêm", Toast.LENGTH_SHORT).show();
                            dialog.dismiss(); // Close dialog after success
                        } else {
                            Toast.makeText(this, "Lỗi khi thêm sản phẩm", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Handle the "Hủy" button click (just close the dialog)
        btnHuy.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

}

