package edu.ou.flowerstore.ui.productadmin;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;

import edu.ou.flowerstore.databinding.DialogEditProductBinding;


public class EditProductDialog extends Dialog {
    private final Product product;
    private final OnProductEditedListener listener;

    public interface OnProductEditedListener {
        void onProductEdited(Product product);
    }

    public EditProductDialog(@NonNull Context context, Product product, OnProductEditedListener listener) {
        super(context);
        this.product = product;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogEditProductBinding binding = DialogEditProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Hiển thị thông tin sản phẩm hiện tại
        binding.etMaSanPham.setText(product.getMaSanPham());
        binding.etMaSanPham.setEnabled(false);  // Không cho phép sửa mã sản phẩm
        binding.etTenSP.setText(product.getTenSP());
        binding.etSoLuong.setText(String.valueOf(product.getSoLuong()));
        binding.etGia.setText(String.valueOf(product.getGia()));
        binding.etHinhAnh.setText(product.getHinhAnh());
        binding.etMoTa.setText(product.getMoTa());
        binding.etMaLoaiHoa.setText(product.getMaLoai());

        // Lắng nghe sự kiện khi nhấn nút "Lưu"
        binding.btnLuu.setOnClickListener(v -> {
            String tenSP = binding.etTenSP.getText().toString().trim();
            String soLuongStr = binding.etSoLuong.getText().toString().trim();
            String giaStr = binding.etGia.getText().toString().trim();
            String hinhAnh = binding.etHinhAnh.getText().toString().trim();
            String moTa = binding.etMoTa.getText().toString().trim();
            String maLoai = binding.etMaLoaiHoa.getText().toString().trim();

            // Kiểm tra tính hợp lệ của dữ liệu
            if (tenSP.isEmpty() || soLuongStr.isEmpty() || giaStr.isEmpty() || hinhAnh.isEmpty() || moTa.isEmpty() || maLoai.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Cập nhật thông tin sản phẩm
            double soLuong = Double.parseDouble(soLuongStr);
            double gia = Double.parseDouble(giaStr);

            product.setTenSP(tenSP);
            product.setSoLuong(soLuong);
            product.setGia(gia);
            product.setHinhAnh(hinhAnh);
            product.setMoTa(moTa);
            product.setMaLoai(maLoai);

            // Lưu thông tin sản phẩm đã chỉnh sửa vào Firestore
            FirebaseFirestore.getInstance().collection("products")
                    .document(product.getMaSanPham())  // Sử dụng mã sản phẩm làm document_id
                    .set(product)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Thông báo và gọi listener khi sửa thành công
                            Toast.makeText(getContext(), "Sửa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                            listener.onProductEdited(product);  // Gọi listener để xử lý sửa sản phẩm
                            dismiss(); // Đóng dialog
                        } else {
                            // Thông báo khi sửa thất bại
                            Toast.makeText(getContext(), "Sửa sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Lắng nghe sự kiện khi nhấn nút "Hủy"
        binding.btnHuy.setOnClickListener(v -> dismiss());
    }
}

