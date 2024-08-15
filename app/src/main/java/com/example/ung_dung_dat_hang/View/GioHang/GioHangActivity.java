package com.example.ung_dung_dat_hang.View.GioHang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ung_dung_dat_hang.Adapter.GioHangAdapter;
import com.example.ung_dung_dat_hang.ConnnectInternet.DatabaseConnection;
import com.example.ung_dung_dat_hang.R;
import com.example.ung_dung_dat_hang.View.DangNhap.DangNhapActivity;
import com.example.ung_dung_dat_hang.View.ThanhToan.ThanhToanActivity;
import com.example.ung_dung_dat_hang.View.TrangChu.ManHinhTrangChu;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GioHangAdapter adapter;
    private TextView txtTongTien, txtSoLuong;
    private DatabaseConnection databaseConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gio_hang);
        databaseConnection = new DatabaseConnection(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        recyclerView = findViewById(R.id.recycleGioHang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txtTongTien = findViewById(R.id.tvTongTien);
        txtSoLuong = findViewById(R.id.tvSoLuong);
        Button btnMuaNgay = findViewById(R.id.btnmuangay);
        // GioHangActivity.java
        btnMuaNgay.setOnClickListener(v -> {
            Intent intent = new Intent(GioHangActivity.this, ThanhToanActivity.class);

            // Pass the product details and total amount to ThanhToanActivity
            intent.putExtra("TOTAL_AMOUNT", txtTongTien.getText().toString());
            intent.putExtra("PRODUCTS", convertCartItemsToString(adapter.getCartItems()));

            startActivity(intent);
        });
        adapter = new GioHangAdapter(this, loadCartItems());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemRemoveListener(position -> {
            removeCartItem(position);
            recalculateTotal();
        });

        adapter.setOnQuantityChangeListener(() -> {
            recalculateTotal();
        });
    }
    private String convertCartItemsToString(List<CartItem> cartItems) {
        StringBuilder sb = new StringBuilder();
        for (CartItem item : cartItems) {
            sb.append(item.getTenSP()).append(";")
                    .append(item.getGiaSP()).append(";")
                    .append(item.getAnhSP()).append(";")
                    .append(item.getAnhNhoSP()).append(";")
                    .append(item.getSoLuong()).append("|");
        }
        return sb.toString();
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
        if (databaseConnection != null) {
            double totalPrice = databaseConnection.getCartTotal(); // Use the method from DatabaseConnection
            int totalQuantity = 0;

            for (CartItem item : adapter.getCartItems()) {
                totalQuantity += item.getSoLuong();
            }

            txtTongTien.setText(String.format("Tổng tiền: %,.0f VND", totalPrice));
            txtSoLuong.setText(String.format("Số lượng: %d", totalQuantity));
        } else {
            // Handle the case where databaseConnection is null
            txtTongTien.setText("Tổng tiền: 0 VND");
            txtSoLuong.setText("Số lượng: 0");
        }
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
