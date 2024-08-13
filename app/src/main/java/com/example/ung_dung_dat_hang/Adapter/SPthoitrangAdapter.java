package com.example.ung_dung_dat_hang.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.SanPham;
import com.example.ung_dung_dat_hang.R;

import java.util.List;

public class SPthoitrangAdapter extends RecyclerView.Adapter<SPthoitrangAdapter.SanPhamViewHolder>{
    private List<SanPham> sanPhamList;

    public SPthoitrangAdapter(List<SanPham> sanPhamList) {
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public SPthoitrangAdapter.SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sanpham, parent, false);
        return new SPthoitrangAdapter.SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SPthoitrangAdapter.SanPhamViewHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        holder.tvTenSP.setText(sanPham.getTenSP());
        holder.tvGia.setText(String.valueOf(sanPham.getGia()));
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
            // If URL is invalid, set default image or hide ImageView
            holder.anhsp.setImageResource(R.drawable.error); // Or hide ImageView
        }
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

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            anhsp = itemView.findViewById(R.id.anhsp);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvThongTin = itemView.findViewById(R.id.tvgiagoc);
        }
    }

}
