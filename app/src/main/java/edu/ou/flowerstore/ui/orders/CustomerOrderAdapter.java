package edu.ou.flowerstore.ui.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ou.flowerstore.R;

public class CustomerOrderAdapter extends RecyclerView.Adapter<CustomerOrderAdapter.ViewHolder> {
    private Context context;
    private final List<CustomerOrderOverview> customerOrderOverviewList;

    public CustomerOrderAdapter(List<CustomerOrderOverview> list) {
        customerOrderOverviewList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_order, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustomerOrderOverview order = customerOrderOverviewList.get(position);
        holder.nameTx.setText(order.name);
        holder.priceTx.setText(order.price);
        holder.dateTx.setText(order.date);
        holder.orderId.setText(String.format("Mã: %s", order.id));
        switch (order.state) {
            case "paying":
                holder.stateTx.setText("Đang đợi thanh toán.");
                break;
            case "completed":
                holder.stateTx.setText("Đơn hàng đã hoàn thành");
                break;
            case "pending":
                holder.stateTx.setText("Đang xử lý đơn hàng");
                break;
            case "denied":
                holder.stateTx.setText("Đang hàng bị từ chối");
                break;
            default:
                holder.stateTx.setText("");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return customerOrderOverviewList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTx, priceTx, dateTx, stateTx, orderId;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTx = itemView.findViewById(R.id.name_tx);
            priceTx = itemView.findViewById(R.id.price_tx);
            dateTx = itemView.findViewById(R.id.date_tx);
            stateTx = itemView.findViewById(R.id.state_tx);
            orderId = itemView.findViewById(R.id.order_id);
            view = itemView;
        }
    }

    public static class CustomerOrderOverview {
        String id;
        String name;
        String date;
        String state;
        String price;

        public CustomerOrderOverview(String id, String name, String date, String state, String price) {
            this.id = id;
            this.name = name;
            this.date = date;
            this.state = state;
            this.price = price;
        }
    }
}
