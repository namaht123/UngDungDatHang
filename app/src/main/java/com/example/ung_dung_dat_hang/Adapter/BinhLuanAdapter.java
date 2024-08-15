package com.example.ung_dung_dat_hang.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.BinhLuan;
import com.example.ung_dung_dat_hang.R;

import java.util.List;

public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.BinhLuanViewHolder> {
    private List<BinhLuan> comments;

    public BinhLuanAdapter(List<BinhLuan> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public BinhLuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_binhluan, parent, false);
        return new BinhLuanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BinhLuanViewHolder holder, int position) {
        BinhLuan comment = comments.get(position);
        holder.noiDungTextView.setText(comment.getNoiDung());
        holder.ngayBinhLuanTextView.setText(comment.getNgayBinhLuan());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void updateComments(List<BinhLuan> newComments) {
        comments.clear();
        comments.addAll(newComments);
        notifyDataSetChanged();
    }

    static class BinhLuanViewHolder extends RecyclerView.ViewHolder {
        TextView noiDungTextView;
        TextView ngayBinhLuanTextView;

        BinhLuanViewHolder(@NonNull View itemView) {
            super(itemView);
            noiDungTextView = itemView.findViewById(R.id.textViewNoiDung);
            ngayBinhLuanTextView = itemView.findViewById(R.id.textViewNgayBinhLuan);
        }
    }
}
