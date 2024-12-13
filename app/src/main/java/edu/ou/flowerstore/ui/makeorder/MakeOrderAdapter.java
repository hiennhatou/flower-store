package edu.ou.flowerstore.ui.makeorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.ui.cart.CartItem;

public class MakeOrderAdapter extends RecyclerView.Adapter<MakeOrderAdapter.Holder> {
    private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "vn"));
    private Context context;
    private List<CartItem> products;

    public MakeOrderAdapter (List<CartItem> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_make_order, parent, false);
        context = parent.getContext();
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        CartItem product = products.get(position);
        Picasso.get().load(product.getImage()).into(holder.image);
        holder.productName.setText(product.getName());
        holder.price.setText(numberFormat.format(product.getPrice()));
        holder.quantity.setText(String.valueOf(product.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    static public class Holder extends RecyclerView.ViewHolder {

        TextView productName, quantity, price;
        ShapeableImageView image;
        public Holder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.product_img);
        }
    }

    public void setProducts(List<CartItem> products) {
        this.products = products;
    }
}
