package com.example.ung_dung_dat_hang.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ung_dung_dat_hang.R;
import com.example.ung_dung_dat_hang.View.GioHang.CartItem;

import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    private List<CartItem> cartItems;
    private Context context;
    private OnItemRemoveListener onItemRemoveListener;
    private OnQuantityChangeListener onQuantityChangeListener;

    public GioHangAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
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
        holder.soluongsanphamgiohang.setText(String.valueOf(item.getSoLuong()));

        // Use Glide to load image
        Glide.with(holder.itemView.getContext())
                .load(item.getAnhSP())
                .error(R.drawable.icon_block)
                .into(holder.hinhsanphamgiohang);

        holder.btnTang.setOnClickListener(v -> {
            incrementQuantity(holder.getAdapterPosition());
        });

        holder.btnGiam.setOnClickListener(v -> {
            decrementQuantity(holder.getAdapterPosition());
        });

        holder.btnXoa.setOnClickListener(v -> {
            if (onItemRemoveListener != null) {
                onItemRemoveListener.onItemRemove(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void removeItem(int position) {
        cartItems.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemRemoveListener(OnItemRemoveListener listener) {
        this.onItemRemoveListener = listener;
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        this.onQuantityChangeListener = listener;
    }

    private void incrementQuantity(int position) {
        CartItem item = cartItems.get(position);
        item.setSoLuong(item.getSoLuong() + 1);
        notifyItemChanged(position);

        updateCartInPreferences();

        if (onQuantityChangeListener != null) {
            onQuantityChangeListener.onQuantityChange();
        }
    }

    private void decrementQuantity(int position) {
        CartItem item = cartItems.get(position);
        if (item.getSoLuong() > 1) {  // Prevent quantity from going below 1
            item.setSoLuong(item.getSoLuong() - 1);
            notifyItemChanged(position);

            updateCartInPreferences();

            if (onQuantityChangeListener != null) {
                onQuantityChangeListener.onQuantityChange();
            }
        }
    }

    private void updateCartInPreferences() {
        SharedPreferences cartPreferences = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        SharedPreferences userPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String userEmail = userPreferences.getString("user_email", null);

        if (userEmail != null) {
            SharedPreferences.Editor editor = cartPreferences.edit();

            int itemCount = cartItems.size();
            editor.putInt(userEmail + "_item_count", itemCount);

            for (int i = 0; i < itemCount; i++) {
                CartItem item = cartItems.get(i);
                String itemData = item.getTenSP() + ";" + item.getGiaSP() + ";" + item.getAnhSP() + ";" +
                        (item.getAnhNhoSP() != null ? item.getAnhNhoSP() : "") + ";" + item.getSoLuong();
                editor.putString(userEmail + "_product_" + (i + 1), itemData);
            }

            editor.apply();
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tieudesanphamgiohang, giasanphamgiohang, soluongsanphamgiohang;
        ImageView hinhsanphamgiohang, btnXoa;
        Button btnTang, btnGiam;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tieudesanphamgiohang = itemView.findViewById(R.id.tieudesanphamgiohang);
            giasanphamgiohang = itemView.findViewById(R.id.giasanphamgiohang);
            soluongsanphamgiohang = itemView.findViewById(R.id.soluongsanphamgiohang);
            hinhsanphamgiohang = itemView.findViewById(R.id.hinhsanphamgiohang);
            btnTang = itemView.findViewById(R.id.btnTang);
            btnGiam = itemView.findViewById(R.id.btnGiam);
            btnXoa = itemView.findViewById(R.id.xoasanpham);
        }
    }

    public interface OnItemRemoveListener {
        void onItemRemove(int position);
    }

    public interface OnQuantityChangeListener {
        void onQuantityChange();
    }
}
