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

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder> {

    private List<SanPham> sanPhamList;
    private SessionManager sessionManager;

    public SanPhamAdapter(List<SanPham> sanPhamList, SessionManager sessionManager) {
        this.sanPhamList = sanPhamList;
        this.sessionManager = sessionManager;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham, parent, false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);

        holder.tenSPTextView.setText(sanPham.getTenSP());
        double giaGoc = sanPham.getGia();
        double phanTramKhuyenMai = sessionManager.getDiscountPercentage(sanPham.getMaSP());

        if (phanTramKhuyenMai > 0) {
            double giaSauGiam = giaGoc * (1 - phanTramKhuyenMai / 100);

            holder.txtPhanTramGiamGia.setText(String.format("%,.0f%%", phanTramKhuyenMai));
            holder.txtPhanTramGiamGia.setVisibility(View.VISIBLE);

            holder.giagoc.setText(String.format("Giá gốc: %,.0f VND", giaGoc));
            holder.giagoc.setPaintFlags(holder.giagoc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.giaTextView.setText(String.format("Giá sau giảm: %,.0f VND", giaSauGiam));
        } else {
            holder.txtPhanTramGiamGia.setVisibility(View.GONE);
            holder.giagoc.setText(String.format("Giá: %,.0f VND", giaGoc));
            holder.giagoc.setPaintFlags(holder.giagoc.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.giaTextView.setText("");
        }

        Glide.with(holder.itemView.getContext())
                .load(sanPham.getAnh())
                .into(holder.anhImageView);

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

    public void updateData(List<SanPham> newSanPhamList) {
        this.sanPhamList = newSanPhamList;
        notifyDataSetChanged();
    }

    public static class SanPhamViewHolder extends RecyclerView.ViewHolder {
        TextView tenSPTextView;
        TextView giaTextView;
        ImageView anhImageView;
        TextView txtPhanTramGiamGia;
        TextView giagoc;

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            tenSPTextView = itemView.findViewById(R.id.tvTenSP);
            giaTextView = itemView.findViewById(R.id.tvGia);
            anhImageView = itemView.findViewById(R.id.anhsp);
            txtPhanTramGiamGia = itemView.findViewById(R.id.txtPhanTramGiamGia);
            giagoc = itemView.findViewById(R.id.tvgiagoc);
        }
    }
}
