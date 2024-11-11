package edu.ou.flowerstore;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyArrayAdapter extends RecyclerView.Adapter<MyArrayAdapter.ViewHolder> {
    private Context context;
    private int layoutId;
    private ArrayList<Item> itemList;

    public MyArrayAdapter(Context context, int layoutId, ArrayList<Item> itemList) {
        this.context = context;
        this.layoutId = layoutId;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.imgItem.setImageResource(item.getImage());
        holder.txtName.setText(item.getName());
        holder.txtPrice.setText(String.format("%,d VNĐ", item.getPrice()));
        holder.txtRating.setText(String.valueOf(item.getRate()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView txtName, txtPrice, txtRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.img_item);
            txtName = itemView.findViewById(R.id.txt_tensp);
            txtPrice = itemView.findViewById(R.id.txt_giasp);
            txtRating = itemView.findViewById(R.id.txt_danhgia);
        }
    }
}
