package com.example.ung_dung_dat_hang.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.ThuongHieu;  // Use the correct package path
import com.example.ung_dung_dat_hang.R;

import java.util.List;

public class ThuongHieuAdapter extends RecyclerView.Adapter<ThuongHieuAdapter.ThuonghieuViewHolder> {

    private List<ThuongHieu> thuongHieuList;

    public ThuongHieuAdapter(List<ThuongHieu> thuongHieuList) {
        this.thuongHieuList = thuongHieuList;
    }

    @NonNull
    @Override
    public ThuonghieuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_thuonghieu, parent, false);
        return new ThuonghieuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThuonghieuViewHolder holder, int position) {
        ThuongHieu thuongHieu = thuongHieuList.get(position);
        holder.textViewTenThuongHieu.setText(thuongHieu.getTenthuonghieu());
        Log.d("Adapter", "Binding data: " + thuongHieu.toString());
        Glide.with(holder.itemView.getContext())
                .load(thuongHieu.getHinhthuonghieu())
                .placeholder(R.drawable.placeholder) // Optional placeholder
                .into(holder.imageViewHinhThuongHieu);
    }


    @Override
    public int getItemCount() {
        return thuongHieuList.size();
    }

    public static class ThuonghieuViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTenThuongHieu;
        ImageView imageViewHinhThuongHieu;

        public ThuonghieuViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTenThuongHieu = itemView.findViewById(R.id.textViewTenThuongHieu);
            imageViewHinhThuongHieu = itemView.findViewById(R.id.imageViewHinhThuongHieu);
        }
    }
}
