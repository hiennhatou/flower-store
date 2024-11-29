package edu.ou.flowerstore.utils.adapters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.ou.flowerstore.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<OverviewCategory> categories;

    public CategoryAdapter(List<OverviewCategory> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OverviewCategory category = categories.get(position);
        Picasso.get().load(category.getThumbnail()).placeholder(R.drawable.placeholder).into(holder.getThumbnailImageView());
        holder.getTitleTextView().setText(category.name);
        holder.getThumbnailImageView().getDrawable().setColorFilter(Color.parseColor("#38000000"), PorterDuff.Mode.DARKEN);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView thumbnailImageView;
        private final TextView titleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.background_img);
            titleTextView = itemView.findViewById(R.id.category_title);
        }

        public ImageView getThumbnailImageView() {
            return thumbnailImageView;
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }
    }

    public static class OverviewCategory {
        private String id;
        private String name;
        private String thumbnail;

        public OverviewCategory(String id, String name, String thumbnail) {
            this.setId(id);
            this.setName(name);
            this.setThumbnail(thumbnail);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}
