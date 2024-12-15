package edu.ou.flowerstore.ui.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import edu.ou.flowerstore.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private final List<OrderOverview> orderList;
    private final OnOrderClickListener onOrderClickListener;

    public OrderAdapter(List<OrderOverview> list, OnOrderClickListener listener) {
        orderList = list;
        onOrderClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderOverview order = orderList.get(position);
        holder.orderId.setText(String.format("Mã: %s", order.id));
        holder.orderName.setText(String.format("Sản phẩm: %s", order.name));
        holder.orderDate.setText(String.format("Ngày: %s", order.date));
        holder.orderPrice.setText(String.format("Tổng giá: %s", order.price));
        holder.orderStatus.setText(String.format("Trạng thái: %s", order.status));

        // Thêm sự kiện nhấp
        holder.itemView.setOnClickListener(v -> onOrderClickListener.onOrderClick(order.id));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, orderName, orderDate, orderPrice, orderStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_id);
            orderName = itemView.findViewById(R.id.order_name);
            orderDate = itemView.findViewById(R.id.order_date);
            orderPrice = itemView.findViewById(R.id.order_price);
            orderStatus = itemView.findViewById(R.id.order_status);
        }
    }

    public static class OrderOverview {
        String id;
        String name;
        String date;
        String status;
        String price;

        public OrderOverview(String id, String name, String date, String status, String price) {
            this.id = id;
            this.name = name;
            this.date = date;
            this.status = status;
            this.price = price;
        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(String orderId);
    }
}
