package edu.ou.flowerstore.utils.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.Timestamp;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import edu.ou.flowerstore.R;

public class OverviewProductAdapter extends RecyclerView.Adapter<OverviewProductAdapter.ViewHolder> {
    List<OverviewProduct> products;
    Locale locale = new Locale("vi", "vn");
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

    public OverviewProductAdapter(List<OverviewProduct> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_overview_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OverviewProduct product = products.get(position);
        if (product == null) return;
        holder.getTitleTextView().setText(product.getName());
        holder.getPriceTextView().setText(currencyFormat.format(product.getPrice()));
        holder.getWishBtn().setSelected(product.isWish());

        holder.getWishBtn().setOnClickListener(v -> {
            product.setWish(!product.isWish());
            v.setSelected(product.isWish());
        });

        Picasso.get().load(product.getThumbnail()).placeholder(R.drawable.placeholder).into(holder.getThumbnailImg());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final ShapeableImageView thumbnailImg;
        private final TextView priceTextView;
        private final Button wishBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title);
            thumbnailImg = itemView.findViewById(R.id.thumbnail_image);
            priceTextView = itemView.findViewById(R.id.price);
            wishBtn = itemView.findViewById(R.id.add_wish_btn);
        }


        public TextView getTitleTextView() {
            return titleTextView;
        }

        public ShapeableImageView getThumbnailImg() {
            return thumbnailImg;
        }

        public TextView getPriceTextView() {
            return priceTextView;
        }

        public Button getWishBtn() {
            return wishBtn;
        }
    }

    public static class OverviewProduct {
        private String id;
        private String name;
        private long price;
        private String thumbnail;
        private boolean wish;

        private Timestamp createdDate;

        public OverviewProduct(String id, String name, long price, String thumbnail, boolean isWish, Timestamp createdDate) {
            this.setId(id);
            this.setName(name);
            this.setPrice(price);
            this.setThumbnail(thumbnail);
            this.setWish(isWish);
            this.setCreatedDate(createdDate);
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

        public long getPrice() {
            return price;
        }

        public void setPrice(long price) {
            this.price = price;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public boolean isWish() {
            return wish;
        }

        public void setWish(boolean wish) {
            this.wish = wish;
        }

        public Timestamp getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(Timestamp createdDate) {
            this.createdDate = createdDate;
        }
    }
}
