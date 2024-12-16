package edu.ou.flowerstore.ui.productadmin;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;
import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.ItemProductAdminBinding;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final List<Product> products;
    private final Context context;
    private final OnProductItemClickListener listener;

    public interface OnProductItemClickListener {
        void onEditProduct(Product product);
        void onDeleteProduct(Product product);
    }

    public ProductAdapter(List<Product> products, Context context, OnProductItemClickListener listener) {
        this.products = products;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductAdminBinding binding = ItemProductAdminBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);

        // Gán dữ liệu vào giao diện
        holder.binding.tvTenSP.setText(product.getTenSP());
        holder.binding.tvMoTa.setText(String.valueOf(product.getMoTa()));

        // Định dạng giá sản phẩm
        double productPrice = product.getGia(); // Get the price as a double
        DecimalFormat decimalFormat = new DecimalFormat("#,###"); // Format the price with commas
        String formattedPrice = decimalFormat.format(productPrice) + " đ"; // Append " đ" for Vietnamese Dong

        holder.binding.tvGia.setText(formattedPrice); // Gán giá đã định dạng vào TextView

        // Gán hình ảnh vào ImageView bằng Picasso
        String imageUrl = product.getHinhAnh(); // Giả sử 'getHinhAnh()' trả về URL của hình ảnh
        Picasso.get()
                .load(imageUrl) // Tải hình ảnh từ URL
                .placeholder(R.drawable.ic_avatar_placeholder) // Hiển thị placeholder khi hình ảnh đang tải
                .into(holder.binding.imgHinhAnh); // Gán vào ImageView

        // Gán thông tin chi tiết sản phẩm
        holder.binding.tvTenSP.setText("Tên sản phẩm: " + product.getTenSP());
        holder.binding.tvMoTa.setText("Mô tả: " + product.getMoTa());
        holder.binding.tvGia.setText("Giá: " + formattedPrice); // Use formatted price here

        // Load hình ảnh sử dụng Glide hoặc thư viện khác
        Picasso.get()
                .load(product.getHinhAnh())
                .into(holder.binding.imgHinhAnh);

        // Xử lý sự kiện click vào optionsMenu
        holder.binding.imgMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, holder.binding.imgMenu);
            popup.getMenuInflater().inflate(R.menu.bottom_nav_sua_xoa, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.action_edit) {
                    listener.onEditProduct(product); // Gọi listener để chỉnh sửa
                } else if (itemId == R.id.action_delete) {
                    listener.onDeleteProduct(product); // Gọi listener để xóa
                }
                return true;
            });
            popup.show();
        });
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ItemProductAdminBinding binding;

        public ProductViewHolder(ItemProductAdminBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void deleteSanPham(int position) {
        // Lấy sản phẩm cần xóa
        Product product = products.get(position);
        if (product == null || product.getMaSanPham() == null) {
            Toast.makeText(context, "Sản phẩm không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Xác nhận việc xóa sản phẩm
        new AlertDialog.Builder(context)
                .setTitle("Xóa sản phẩm")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    // Xóa sản phẩm khỏi danh sách
                    products.remove(position);

                    // Xóa sản phẩm khỏi Firebase Realtime Database
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                    dbRef.child("SanPham").child(product.getMaSanPham()).removeValue()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Thông báo thành công
                                    Toast.makeText(context, "Sản phẩm đã được xóa", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Xử lý lỗi
                                    Toast.makeText(context, "Lỗi khi xóa sản phẩm", Toast.LENGTH_SHORT).show();
                                }
                            });

                    // Thông báo adapter cập nhật
                    notifyDataSetChanged();
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private boolean handleMenuItemClick(MenuItem item, int position) {
        // Xử lý sự kiện cho các mục trong menu
        int itemId = item.getItemId();

        if (itemId == R.id.action_edit) {
            editSanPham(position);
            return true;
        } else if (itemId == R.id.action_delete) {
            deleteSanPham(position);
            return true;
        } else {
            return false;
        }
    }

    private void editSanPham(int position) {
        // Lấy sản phẩm cần sửa
        Product product = products.get(position);
        if (product == null) {
            return;
        }

        // Hiển thị hộp thoại sửa sản phẩm
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_edit_product, null);
        builder.setView(dialogView);

        TextView tvMaSanPham = dialogView.findViewById(R.id.etMaSanPham);
        EditText etTenSP = dialogView.findViewById(R.id.etTenSP);
        EditText etMoTa = dialogView.findViewById(R.id.etMoTa);
        EditText etGia = dialogView.findViewById(R.id.etGia);

        // Đặt giá trị ban đầu cho các trường
        tvMaSanPham.setText("Mã sản phẩm: " + product.getMaSanPham());
        etTenSP.setText(product.getTenSP());
        etMoTa.setText(product.getMoTa());
        etGia.setText(String.valueOf(product.getGia()));

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            // Cập nhật thông tin sản phẩm
            product.setTenSP(etTenSP.getText().toString());
            product.setMoTa(etMoTa.getText().toString());
            product.setGia(Double.parseDouble(etGia.getText().toString()));

            // Cập nhật thông tin trong Firebase
            updateSanPhamInDatabase (product);

            // Thông báo adapter cập nhật
            notifyDataSetChanged();
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateSanPhamInDatabase(Product sanPham) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("SanPham");

        dbRef.child(sanPham.getMaSanPham()).setValue(sanPham)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Thông báo thành công
                        Toast.makeText(context, "Sản phẩm đã được cập nhật", Toast.LENGTH_SHORT).show();
                    } else {
                        // Xử lý lỗi
                        Toast.makeText(context, "Lỗi khi cập nhật sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}