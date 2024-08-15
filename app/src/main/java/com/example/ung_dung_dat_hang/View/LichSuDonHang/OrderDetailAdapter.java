package com.example.ung_dung_dat_hang.View.LichSuDonHang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.ChiTietDonDatHang;
import com.example.ung_dung_dat_hang.R;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {
    private List<ChiTietDonDatHang> orderDetailList;

    public OrderDetailAdapter(List<ChiTietDonDatHang> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item, parent, false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        ChiTietDonDatHang detail = orderDetailList.get(position);

        // Load the product image using Glide
        Glide.with(holder.itemView.getContext())
                .load(detail.getHinh())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.imgProduct);

        // Set the product name, quantity, and price
        holder.txtProductName.setText(detail.getTenSP());
        holder.txtQuantity.setText("Số lượng: " + detail.getSoluong());
        holder.txtPrice.setText("Giá: " + detail.getGia().toString());
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    public static class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtProductName, txtQuantity, txtPrice;

        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtPrice = itemView.findViewById(R.id.txtPrice); // Add this line
        }
    }
}
