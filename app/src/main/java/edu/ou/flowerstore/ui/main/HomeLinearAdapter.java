package edu.ou.flowerstore.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.ou.flowerstore.R;
import edu.ou.flowerstore.ui.cart.CartActivity;

public class HomeLinearAdapter extends RecyclerView.Adapter<HomeLinearAdapter.ViewHolder> {
    @NonNull
    @Override
    public HomeLinearAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_home_linear, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getSeeMoreCategories().setOnClickListener(v -> {
            BottomNavigationView bottomNav = holder.getRootView().getRootView().findViewById(R.id.bottom_navigation);
            bottomNav.setSelectedItemId(R.id.nav_categories);
        });

        holder.getCartBtn().setOnClickListener(v -> {
            Intent intent = new Intent(holder.getContext(), CartActivity.class);
            holder.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView seeMoreCategories;
        private final Button cartBtn;
        private final View rootView;
        private final Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.seeMoreCategories = itemView.findViewById(R.id.see_more_categories);
            this.cartBtn = itemView.findViewById(R.id.cart_btn);
            this.rootView = itemView.getRootView();
            this.context = itemView.getContext();
        }

        public TextView getSeeMoreCategories() {
            return seeMoreCategories;
        }

        public Button getCartBtn() {
            return cartBtn;
        }

        public View getRootView() {
            return rootView;
        }

        public Context getContext() {
            return context;
        }
    }
}
