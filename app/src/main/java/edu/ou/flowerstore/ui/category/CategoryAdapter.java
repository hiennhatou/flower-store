package edu.ou.flowerstore.ui.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.databinding.ItemCategoryAdminBinding;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private final List<Category> categories;
    private final Context context;
    private OnCategoryItemClickListener listener;

    public CategoryAdapter(List<Category> categories, Context context, OnCategoryItemClickListener listener) {
        this.categories = categories;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryAdminBinding binding = ItemCategoryAdminBinding.inflate(LayoutInflater.from(context), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.binding.categoryName.setText(category.getName());

        holder.binding.optionsMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, holder.binding.optionsMenu);
            popup.getMenuInflater().inflate(R.menu.category_options_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.action_update) {
                    listener.onUpdateCategory(category);
                } else if (itemId == R.id.action_delete) {
                    listener.onDeleteCategory(category, position);
                }
                return true;
            });
            popup.show();
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryAdminBinding binding;

        public CategoryViewHolder(ItemCategoryAdminBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnCategoryItemClickListener {
        void onUpdateCategory(Category category);
        void onDeleteCategory(Category category, int position); // Cập nhật listener để nhận thêm vị trí
    }
}
