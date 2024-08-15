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
import com.example.ung_dung_dat_hang.Model.ObjeactClass.SanPhamKhuyenMai;
import com.example.ung_dung_dat_hang.R;
import com.example.ung_dung_dat_hang.View.ChiTietSanPham.ChiTietSanPhamActivity;

import java.util.List;

public class SPgiamgiaAdapter extends RecyclerView.Adapter<SPgiamgiaAdapter.SanPhamViewHolder> {
    private List<SanPhamKhuyenMai> sanPhamList;

    public SPgiamgiaAdapter(List<SanPhamKhuyenMai> sanPhamList) {
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sanpham_khuyenmai, parent, false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPhamKhuyenMai sanPhamKhuyenMai = sanPhamList.get(position);

        holder.tvTenSP.setText(sanPhamKhuyenMai.getTenSPKM());

        double giaGoc = sanPhamKhuyenMai.getGiaGoc();
        double phanTramKhuyenMai = sanPhamKhuyenMai.getPhanTramKhuyenMai();
        double giaKhuyenMai = giaGoc * (1 - (phanTramKhuyenMai / 100));

        if (phanTramKhuyenMai > 0) {
            // Show discounted price and hide original price
            holder.tvGiaKhuyenMai.setText("Giá khuyến mãi: " + String.format("%,.0f", giaKhuyenMai));
            holder.tvGiaKhuyenMai.setVisibility(View.VISIBLE);

            holder.tvGiaGoc.setText("Giá gốc: " + String.format("%,.0f", giaGoc));
            holder.tvGiaGoc.setPaintFlags(holder.tvGiaGoc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG); // Strikethrough original price
            holder.tvGiaGoc.setVisibility(View.VISIBLE);
        } else {
            // Show original price only
            holder.tvGiaGoc.setText("Giá gốc: " + String.format("%,.0f", giaGoc));
            holder.tvGiaGoc.setPaintFlags(0); // Remove strikethrough if any
            holder.tvGiaGoc.setVisibility(View.VISIBLE);

            holder.tvGiaKhuyenMai.setVisibility(View.GONE);
        }

        String imageUrl = sanPhamKhuyenMai.getHinhAnh();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(holder.anhsp);
        } else {
            holder.anhsp.setImageResource(R.drawable.error);
        }

        // Set click listener for the item view
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ChiTietSanPhamActivity.class);
            intent.putExtra("MA_SP", sanPhamKhuyenMai.getMaSP());
            intent.putExtra("TEN_SP", sanPhamKhuyenMai.getTenSPKM());

            // Pass discounted price if applicable, otherwise pass original price
            double priceToPass = (phanTramKhuyenMai > 0) ? giaKhuyenMai : giaGoc;
            intent.putExtra("GIA_SP", priceToPass);

            intent.putExtra("THONGTIN_SP", sanPhamKhuyenMai.getThongTin());
            intent.putExtra("ANH_SP", sanPhamKhuyenMai.getHinhAnh());
            holder.itemView.getContext().startActivity(intent);
        });

        // Optionally set click listener for the image view
        holder.anhsp.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ChiTietSanPhamActivity.class);
            intent.putExtra("MA_SP", sanPhamKhuyenMai.getMaSP());
            intent.putExtra("TEN_SP", sanPhamKhuyenMai.getTenSPKM());

            // Pass discounted price if applicable, otherwise pass original price
            double priceToPass = (phanTramKhuyenMai > 0) ? giaKhuyenMai : giaGoc;
            intent.putExtra("GIA_SP", priceToPass);
            intent.putExtra("THONGTIN_SP", sanPhamKhuyenMai.getThongTin());
            intent.putExtra("ANH_SP", sanPhamKhuyenMai.getHinhAnh());
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
        TextView tvGiaGoc;
        TextView tvGiaKhuyenMai;
        TextView txtPhanTramGiamGia;

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            anhsp = itemView.findViewById(R.id.anhsp);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGiaGoc = itemView.findViewById(R.id.tvGiaGoc);
            tvGiaKhuyenMai = itemView.findViewById(R.id.tvGiaKhuyenMai);
            txtPhanTramGiamGia = itemView.findViewById(R.id.txtPhanTramGiamGia);
        }
    }
}
