package edu.ou.flowerstore.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.db.entities.ProductEntity;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    MutableLiveData<List<ProductEntity>> products;
    Locale locale = new Locale("vi", "vn");
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

    public ProductsAdapter(MutableLiveData<List<ProductEntity>> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_product_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductEntity product = products.getValue().get(position);
        if (product == null) return;
        holder.getTitleTextView().setText(product.name);
        holder.getPriceTextView().setText(currencyFormat.format(product.price));
        holder.getStarNumber().setText("2.3");
        holder.getThumbnailImg().setImageResource(product.thumbnail);
    }

    @Override
    public int getItemCount() {
        return products.getValue().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView starNumber;
        private ShapeableImageView thumbnailImg;
        private TextView priceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title);
            starNumber = itemView.findViewById(R.id.star_number);
            thumbnailImg = itemView.findViewById(R.id.thumbnail_image);
            priceTextView = itemView.findViewById(R.id.price);
        }


        public TextView getTitleTextView() {
            return titleTextView;
        }

        public TextView getStarNumber() {
            return starNumber;
        }

        public ShapeableImageView getThumbnailImg() {
            return thumbnailImg;
        }

        public TextView getPriceTextView() {
            return priceTextView;
        }
    }
}
