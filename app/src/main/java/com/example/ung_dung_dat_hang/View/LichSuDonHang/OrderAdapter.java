package com.example.ung_dung_dat_hang.View.LichSuDonHang;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ung_dung_dat_hang.Model.ObjeactClass.DonDatHang;
import com.example.ung_dung_dat_hang.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<DonDatHang> orderList;

    public OrderAdapter(List<DonDatHang> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        DonDatHang order = orderList.get(position);
        holder.txtOrderId.setText("Mã đơn hàng: " + order.getMadondathang());
        holder.txtTotalPrice.setText("Tổng tiền: " + order.getTongtien());

        // Set click listener to navigate to order details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), OrderDetailActivity.class);
            intent.putExtra("Madondathang", order.getMadondathang());
            v.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderId, txtTotalPrice;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtTotalPrice = itemView.findViewById(R.id.txtTotalPrice);
        }
    }
}
