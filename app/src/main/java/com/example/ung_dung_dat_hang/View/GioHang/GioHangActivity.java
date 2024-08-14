package com.example.ung_dung_dat_hang.View.GioHang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ung_dung_dat_hang.Adapter.GioHangAdapter;
import com.example.ung_dung_dat_hang.R;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ung_dung_dat_hang.Adapter.GioHangAdapter;
import com.example.ung_dung_dat_hang.R;
import com.example.ung_dung_dat_hang.View.DangNhap.DangNhapActivity;
import com.example.ung_dung_dat_hang.View.TrangChu.ManHinhTrangChu;

import java.util.ArrayList;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GioHangAdapter adapter;
    private TextView txtTongTien, txtSoLuong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gio_hang);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.recycleGioHang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txtTongTien = findViewById(R.id.tvTongTien);
        txtSoLuong = findViewById(R.id.tvSoLuong);

        adapter = new GioHangAdapter(this, loadCartItems());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemRemoveListener(position -> {
            removeCartItem(position);
            recalculateTotal();
        });
    }

    private void removeCartItem(int position) {
        SharedPreferences cartPreferences = getSharedPreferences("cart_prefs", MODE_PRIVATE);
        SharedPreferences userPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userEmail = userPreferences.getString("user_email", null);

        if (userEmail != null) {
            SharedPreferences.Editor editor = cartPreferences.edit();

            // Remove the item from SharedPreferences
            int itemCount = cartPreferences.getInt(userEmail + "_item_count", 0);
            editor.remove(userEmail + "_product_" + (position + 1));
            editor.putInt(userEmail + "_item_count", itemCount - 1);

            // Shift remaining items up to fill the gap
            for (int i = position + 1; i < itemCount; i++) {
                String itemData = cartPreferences.getString(userEmail + "_product_" + (i + 1), null);
                if (itemData != null) {
                    editor.putString(userEmail + "_product_" + i, itemData);
                    editor.remove(userEmail + "_product_" + (i + 1));
                }
            }

            editor.apply();

            // Remove the item from the adapter
            adapter.removeItem(position);
        }
    }

    private void recalculateTotal() {
        double totalPrice = 0;
        int totalQuantity = 0;

        for (CartItem item : adapter.getCartItems()) {
            totalPrice += item.getGiaSP() * item.getSoLuong();
            totalQuantity += item.getSoLuong();
        }

        txtTongTien.setText(String.format("Tổng tiền: %,.0f VND", totalPrice));
        txtSoLuong.setText(String.format("Số lượng: %d", totalQuantity));
    }

    private List<CartItem> loadCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        SharedPreferences cartPreferences = getSharedPreferences("cart_prefs", MODE_PRIVATE);
        SharedPreferences userPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userEmail = userPreferences.getString("user_email", null);

        double totalPrice = 0;
        int totalQuantity = 0;

        if (userEmail != null) {
            int itemCount = cartPreferences.getInt(userEmail + "_item_count", 0);
            for (int i = 1; i <= itemCount; i++) {
                String itemData = cartPreferences.getString(userEmail + "_product_" + i, null);
                if (itemData != null) {
                    String[] data = itemData.split(";");
                    if (data.length >= 5) {
                        String tenSP = data[0];
                        double giaSP = Double.parseDouble(data[1]);
                        String anhSP = data[2];
                        String anhNhoSP = data.length > 3 ? data[3] : null;
                        int quantity = Integer.parseInt(data[4]);

                        cartItems.add(new CartItem(tenSP, giaSP, anhSP, anhNhoSP, quantity));

                        totalPrice += giaSP * quantity;
                        totalQuantity += quantity;
                    }
                }
            }
        }

        // Update the TextViews
        txtTongTien.setText(String.format("Tổng tiền: %,.0f VND", totalPrice));
        txtSoLuong.setText(String.format("Số lượng: %d", totalQuantity));

        return cartItems;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trangchu, menu);
        updateMenu(menu);  // Ensure this method is implemented or use similar logic if needed
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itGioHang) {
            // Open GioHangActivity (current activity)
            Intent intent = new Intent(this, GioHangActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.itDangNhap) {
            Intent iDangNhap = new Intent(this, DangNhapActivity.class);
            startActivity(iDangNhap);
            return true;
        } else if (id == R.id.itdangxuat) {
            // Clear login info
            SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("user_email");  // Clear email or needed info
            editor.apply();

            // Reload TrangChu
            Intent intent = new Intent(this, ManHinhTrangChu.class);
            startActivity(intent);
            finish();  // Finish current activity to prevent back navigation
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void updateMenu(Menu menu) {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userEmail = preferences.getString("user_email", null);

        MenuItem itDangNhap = menu.findItem(R.id.itDangNhap);
        MenuItem itDangXuat = menu.findItem(R.id.itdangxuat);

        if (itDangNhap != null) {
            if (userEmail != null) {
                itDangNhap.setTitle(userEmail);
                itDangNhap.setIcon(null);  // Optionally remove icon
                itDangXuat.setVisible(true);  // Show "Đăng xuất" menu item
            } else {
                itDangNhap.setTitle("Đăng Nhập");
                itDangNhap.setIcon(R.drawable.icon_mcuoi);
                itDangXuat.setVisible(false);  // Hide "Đăng xuất" menu item
            }
        }
    }
}
