package com.example.ung_dung_dat_hang.Adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ung_dung_dat_hang.ConnnectInternet.SessionManager;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.SanPham;
import com.example.ung_dung_dat_hang.R;
import com.example.ung_dung_dat_hang.View.ChiTietSanPham.ChiTietSanPhamActivity;

import java.util.List;

public class SPnhavadoisongAdapter extends RecyclerView.Adapter<SPnhavadoisongAdapter.SanPhamViewHolder> {

    private List<SanPham> sanPhamList;
    private SessionManager sessionManager;

    public SPnhavadoisongAdapter(List<SanPham> sanPhamList, SessionManager sessionManager) {
        this.sanPhamList = sanPhamList;
        this.sessionManager = sessionManager;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sanpham, parent, false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        holder.tvTenSP.setText(sanPham.getTenSP());
        holder.tvThongTin.setText(sanPham.getThongTin());

        // Load image with Glide
        String imageUrl = sanPham.getAnh();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(holder.anhsp);
        } else {
            holder.anhsp.setImageResource(R.drawable.error);
        }

        double giaGoc = sanPham.getGia();
        // Fetch discount percentage from session
        double phanTramKhuyenMai = sessionManager.getDiscountPercentage(sanPham.getMaSP());
        if (phanTramKhuyenMai > 0 ) {
            // Calculate discounted price
            double giaSauGiam = giaGoc * (1 - phanTramKhuyenMai / 100);

            // Display discount percentage
            holder.txtPhanTramGiamGia.setText(String.format("%,.0f%%", phanTramKhuyenMai));
            holder.txtPhanTramGiamGia.setVisibility(View.VISIBLE);

            // Display original price with strikethrough
            holder.giagoc.setText(String.format("Giá gốc: %,.0f VND", giaGoc));
            holder.giagoc.setPaintFlags(holder.giagoc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            // Display discounted price
            holder.tvGia.setText(String.format("Giá sau giảm: %,.0f VND", giaSauGiam));
        }else {
            // Hide discount view if no discount
            holder.txtPhanTramGiamGia.setVisibility(View.GONE);

            // Display only the original price
            holder.giagoc.setText(String.format("Giá: %,.0f VND", giaGoc));
            holder.giagoc.setPaintFlags(holder.giagoc.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvGia.setText(""); // Clear the discounted price field
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ChiTietSanPhamActivity.class);
            intent.putExtra("MA_SP", sanPham.getMaSP());
            intent.putExtra("TEN_SP", sanPham.getTenSP());
            intent.putExtra("GIA_SP", giaGoc);
            intent.putExtra("THONGTIN_SP", sanPham.getThongTin());
            intent.putExtra("ANH_SP", sanPham.getAnh());
            intent.putExtra("ANH_NHO_SP", sanPham.getAnhNho());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    static class SanPhamViewHolder extends RecyclerView.ViewHolder {
        ImageView anhsp;
        TextView tvTenSP;
        TextView tvGia;
        TextView tvThongTin;
        TextView txtPhanTramGiamGia,giagoc; // Add this line

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            anhsp = itemView.findViewById(R.id.anhsp);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvThongTin = itemView.findViewById(R.id.tvgiagoc);
            txtPhanTramGiamGia = itemView.findViewById(R.id.txtPhanTramGiamGia); // Initialize this view
            giagoc = itemView.findViewById(R.id.tvgiagoc);
        }
    }
}
