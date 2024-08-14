package com.example.ung_dung_dat_hang.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.ung_dung_dat_hang.R;
import com.example.ung_dung_dat_hang.View.GioHang.CartItem;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    private List<CartItem> cartItems;

    public GioHangAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.tieudesanphamgiohang.setText(item.getTenSP());
        holder.giasanphamgiohang.setText(String.format("%,.0f VND", item.getGiaSP()));
        holder.soluongsanphamgiohang.setText("Số lượng: " + item.getSoLuong());

        // Use Glide to load image
        Glide.with(holder.itemView.getContext())
                .load(item.getAnhSP())  // Image URL or resource
                .placeholder(R.drawable.backgroud_sanpham)  // Placeholder image
                .into(holder.hinhsanphamgiohang);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView hinhsanphamgiohang;
        TextView tieudesanphamgiohang, giasanphamgiohang, soluongsanphamgiohang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hinhsanphamgiohang = itemView.findViewById(R.id.hinhsanphamgiohang);
            tieudesanphamgiohang = itemView.findViewById(R.id.tieudesanphamgiohang);
            giasanphamgiohang = itemView.findViewById(R.id.giasanphamgiohang);
            soluongsanphamgiohang = itemView.findViewById(R.id.soluong);
        }
    }
}
