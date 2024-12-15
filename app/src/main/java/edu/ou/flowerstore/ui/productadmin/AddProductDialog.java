package edu.ou.flowerstore.ui.productadmin;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;

import edu.ou.flowerstore.databinding.DialogAddProductBinding;

public class AddProductDialog extends Dialog {
    private final OnProductsAddedListener listener;

    public interface OnProductsAddedListener {
        void onProductAdded(Product product);
    }

    public AddProductDialog(@NonNull Context context, OnProductsAddedListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogAddProductBinding binding = DialogAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Lắng nghe sự kiện khi nhấn nút "Thêm"
        binding.btnThem.setOnClickListener(v -> {
            // Lấy dữ liệu từ các trường nhập liệu
            String maSanPham = binding.etMaSanPham.getText().toString().trim();
            String tenSP = binding.etTenSP.getText().toString().trim();
            String soLuongStr = binding.etSoLuong.getText().toString().trim();
            String giaStr = binding.etGia.getText().toString().trim();
            String hinhAnh = binding.etHinhAnh.getText().toString().trim();
            String moTa = binding.etMoTa.getText().toString().trim();
            String maLoai = binding.etMaLoaiHoa.getText().toString().trim();

            // Kiểm tra tính hợp lệ của dữ liệu
            if (maSanPham.isEmpty() || tenSP.isEmpty() || soLuongStr.isEmpty() || giaStr.isEmpty() || hinhAnh.isEmpty() || moTa.isEmpty() || maLoai.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Chuyển đổi số lượng và giá sang kiểu double
            double soLuong = Double.parseDouble(soLuongStr);
            double gia = Double.parseDouble(giaStr);

            // Tạo đối tượng Product
            Product product = new Product(maSanPham, tenSP, soLuong, gia, hinhAnh, moTa, maLoai);

            // Lưu sản phẩm vào Firestore
            FirebaseFirestore.getInstance().collection("products")
                    .document(maSanPham)  // Sử dụng mã sản phẩm làm document_id
                    .set(product)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Thông báo và gọi listener khi thêm thành công
                            Toast.makeText(getContext(), "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                            listener.onProductAdded(product);  // Gọi listener để xử lý thêm sản phẩm
                            dismiss(); // Đóng dialog
                        } else {
                            // Thông báo khi thêm thất bại
                            Toast.makeText(getContext(), "Thêm sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Lắng nghe sự kiện khi nhấn nút "Hủy"
        binding.btnHuy.setOnClickListener(v -> dismiss());
    }
}
