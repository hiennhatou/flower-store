package edu.ou.flowerstore.ui.productadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
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
        holder.binding.tvMoTa.setText(String.valueOf(product.getGia()));
        holder.binding.tvGia.setText(String.valueOf(product.getSoLuong()));

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
}
