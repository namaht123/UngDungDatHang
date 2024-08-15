package com.example.ung_dung_dat_hang.View.ThanhToan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ung_dung_dat_hang.ConnnectInternet.DatabaseConnection;
import com.example.ung_dung_dat_hang.R;
import com.example.ung_dung_dat_hang.View.DangNhap.DangNhapActivity;
import com.example.ung_dung_dat_hang.View.GioHang.GioHangActivity;
import com.example.ung_dung_dat_hang.View.TrangChu.ManHinhTrangChu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ThanhToanActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edtTenNguoiNhan, edtDiaChiGiao, edtSoDienThoai;
    private Button btnThanhToan;
    private DatabaseConnection databaseConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        edtTenNguoiNhan = findViewById(R.id.edtTenNguoiNhan);
        edtDiaChiGiao = findViewById(R.id.edtDiaChiGiao);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        btnThanhToan = findViewById(R.id.btnThanhToan);

        btnThanhToan.setOnClickListener(v -> new ProcessPaymentTask().execute());

        // Initialize DatabaseConnection
        databaseConnection = new DatabaseConnection(this);
    }

    private class ProcessPaymentTask extends AsyncTask<Void, Void, Boolean> {
        private String errorMessage;

        @Override
        protected Boolean doInBackground(Void... voids) {
            String tenNguoiNhan = edtTenNguoiNhan.getText().toString().trim();
            String diaChiGiao = edtDiaChiGiao.getText().toString().trim();
            String soDienThoai = edtSoDienThoai.getText().toString().trim();

            if (tenNguoiNhan.isEmpty() || diaChiGiao.isEmpty() || soDienThoai.isEmpty()) {
                errorMessage = "Vui lòng điền đầy đủ thông tin";
                return false;
            }

            SharedPreferences cartPreferences = getSharedPreferences("cart_prefs", MODE_PRIVATE);
            SharedPreferences userPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String userEmail = userPreferences.getString("user_email", null);

            if (userEmail == null) {
                errorMessage = "Bạn cần đăng nhập để thanh toán";
                return false;
            }

            try {
                // Insert the order and get the generated order ID
                int orderId = databaseConnection.executeInsertOrder(userEmail, tenNguoiNhan, diaChiGiao, soDienThoai);
                if (orderId > 0) {
                    insertOrderDetails(orderId);
                    return true;
                } else {
                    errorMessage = "Đã xảy ra lỗi trong quá trình đặt hàng";
                    return false;
                }
            } catch (SQLException e) {
                errorMessage = "Lỗi cơ sở dữ liệu: " + e.getMessage();
                return false;
            }
        }

        private void insertOrderDetails(int orderId) throws SQLException {
            SharedPreferences cartPreferences = getSharedPreferences("cart_prefs", MODE_PRIVATE);
            SharedPreferences userPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String userEmail = userPreferences.getString("user_email", null);

            if (userEmail != null) {
                int itemCount = cartPreferences.getInt(userEmail + "_item_count", 0);
                for (int i = 1; i <= itemCount; i++) {
                    String itemData = cartPreferences.getString(userEmail + "_product_" + i, null);
                    if (itemData != null) {
                        String[] data = itemData.split(";");
                        if (data.length >= 5) {
                            int maSP = getProductID(data[0]); // Implement getProductID() to get MaSP from SanPham
                            double giaSP = Double.parseDouble(data[1]);
                            int soluong = Integer.parseInt(data[4]);
                            double tongtienSP = giaSP * soluong;

                            databaseConnection.executeInsertOrderDetails(maSP, orderId, soluong, tongtienSP);
                        }
                    }
                }
            }
        }

        private int getProductID(String productName) throws SQLException {
            String query = "SELECT MaSP FROM SanPham WHERE TenSP = ?";
            try (PreparedStatement stmt = databaseConnection.getCon().prepareStatement(query)) {
                stmt.setString(1, productName);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("MaSP");
                    }
                }
            }
            throw new SQLException("Product not found");
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(ThanhToanActivity.this, "Đặt hàng thành công!", Toast.LENGTH_LONG).show();
                // Optionally clear the cart after successful order placement
                clearCart();
                // Navigate back to the home screen
                Intent intent = new Intent(ThanhToanActivity.this, ManHinhTrangChu.class);
                startActivity(intent);
                finish(); // Close current activity
            } else {
                Toast.makeText(ThanhToanActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        }

        private void clearCart() {
            SharedPreferences cartPreferences = getSharedPreferences("cart_prefs", MODE_PRIVATE);
            SharedPreferences userPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String userEmail = userPreferences.getString("user_email", null);

            if (userEmail != null) {
                SharedPreferences.Editor editor = cartPreferences.edit();
                editor.remove(userEmail + "_item_count");
                for (int i = 1; i <= 100; i++) { // Assuming max 100 items in the cart
                    editor.remove(userEmail + "_product_" + i);
                }
                editor.apply();
            }
        }
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
