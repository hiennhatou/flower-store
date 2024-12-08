package edu.ou.flowerstore.ui.cart;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import edu.ou.flowerstore.FlowerStoreApplication;
import edu.ou.flowerstore.R;
import edu.ou.flowerstore.db.RoomDB;
import edu.ou.flowerstore.ui.productdetail.ProductDetailActivity;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Locale locale = new Locale("vi", "vn");
    NumberFormat currentCurrencyFormat = NumberFormat.getCurrencyInstance(locale);
    private final RoomDB roomDB = FlowerStoreApplication.getInstance().getRoomDB();
    private final List<CartItem> cartItems;

    public CartAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.productNameTx.setText(cartItem.getName());
        holder.priceTx.setText(currentCurrencyFormat.format(cartItem.getPrice()));
        holder.quantityTx.setText(String.valueOf(cartItem.getQuantity()));
        Picasso.get().load(cartItem.getImage()).into(holder.imgView);
        holder.imgView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.imgView.getContext(), ProductDetailActivity.class);
            intent.putExtra("id", cartItem.getId());
            holder.itemView.getContext().startActivity(intent);
        });
        holder.incrementBtn.setOnClickListener(v -> {
            roomDB.cartDAO().increaseProductInCart(cartItem.getId());
        });
        holder.decrementBtn.setOnClickListener(v -> {
            roomDB.cartDAO().decreaseProductInCart(cartItem.getId());
        });
        holder.trashBtn.setOnClickListener(v -> {
            roomDB.cartDAO().deleteProductByProductId(cartItem.getId());
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTx;
        TextView priceTx;
        ImageView imgView;
        TextView quantityTx;
        Button incrementBtn;
        Button decrementBtn;
        Button trashBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTx = itemView.findViewById(R.id.tvProductName);
            priceTx = itemView.findViewById(R.id.tvProductPrice);
            imgView = itemView.findViewById(R.id.ivProductImage);
            quantityTx = itemView.findViewById(R.id.tvQuantity);
            incrementBtn = itemView.findViewById(R.id.btnIncreaseQuantity);
            decrementBtn = itemView.findViewById(R.id.btnDecreaseQuantity);
            trashBtn = itemView.findViewById(R.id.btn_trash);
        }
    }
}
