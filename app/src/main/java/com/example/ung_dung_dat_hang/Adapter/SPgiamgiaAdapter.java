package com.example.ung_dung_dat_hang.Adapter;

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

        // Assuming getGiaGoc() returns the original price and getPhanTramKhuyenMai() returns the discount percentage
        double giaGoc = sanPhamKhuyenMai.getGiaGoc();
        double phanTramKhuyenMai = sanPhamKhuyenMai.getPhanTramKhuyenMai();

        // Calculate the discounted price
        double giaKhuyenMai = giaGoc * (1 - (phanTramKhuyenMai / 100));

        holder.tvGiaGoc.setText("Giá gốc: " + String.format("%,.0f", giaGoc));
        holder.tvGiaKhuyenMai.setText("Giá khuyến mãi: " + String.format("%,.0f", giaKhuyenMai));
        holder.txtPhanTramGiamGia.setText(String.format("%,.0f%%", phanTramKhuyenMai));

        // Load image with Glide
        String imageUrl = sanPhamKhuyenMai.getHinhAnh(); // Make sure getHinhanh() method exists in SanPhamKhuyenMai
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(holder.anhsp);
        } else {
            holder.anhsp.setImageResource(R.drawable.error);
        }
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
