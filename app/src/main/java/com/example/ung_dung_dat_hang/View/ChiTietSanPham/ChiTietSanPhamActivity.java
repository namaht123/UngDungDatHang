package com.example.ung_dung_dat_hang.View.ChiTietSanPham;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.ung_dung_dat_hang.Adapter.SanPhamAdapter;
import com.example.ung_dung_dat_hang.ConnnectInternet.DatabaseConnection;
import com.example.ung_dung_dat_hang.ConnnectInternet.SessionManager;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.SanPham;
import com.example.ung_dung_dat_hang.R;
import com.example.ung_dung_dat_hang.View.DangNhap.DangNhapActivity;
import com.example.ung_dung_dat_hang.View.GioHang.GioHangActivity;
import com.example.ung_dung_dat_hang.View.TrangChu.ManHinhTrangChu;

import java.util.ArrayList;
import java.util.List;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    private TextView tenSPTextView, giaTextView, thongTinTextView;
    private ViewPager viewPager;
    private RatingBar ratingBar;
    private Toolbar toolbar;
    private ImageButton addToCartButton;
    private DatabaseConnection databaseConnection;
    private RecyclerView recyclerView;
    private SanPhamAdapter sanPhamAdapter;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chitietsanpham);

        // Initialize views
        tenSPTextView = findViewById(R.id.tenSPTextView);
        giaTextView = findViewById(R.id.giaTextView);
        thongTinTextView = findViewById(R.id.thongTinTextView);
        viewPager = findViewById(R.id.viewpager_liveshow);
        ratingBar = findViewById(R.id.ratingBar);
        toolbar = findViewById(R.id.toolbar);
        addToCartButton = findViewById(R.id.add_to_cart_button);

        // Set up the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Initialize DatabaseConnection and SessionManager
        databaseConnection = new DatabaseConnection(this);
        sessionManager = new SessionManager(this);

        // Get the data from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String tenSP = intent.getStringExtra("TEN_SP");
            double giaSP = intent.getDoubleExtra("GIA_SP", 0);
            String thongTinSP = intent.getStringExtra("THONGTIN_SP");
            String anhSP = intent.getStringExtra("ANH_SP");
            String anhNhoSP = intent.getStringExtra("ANH_NHO_SP");

            // Set the data to the views
            tenSPTextView.setText(tenSP);
            giaTextView.setText(String.format("%,.0f VND", giaSP));
            thongTinTextView.setText(thongTinSP);

            // Load images into ViewPager
            ArrayList<String> images = new ArrayList<>();
            images.add(anhSP); // Large image
            if (anhNhoSP != null && !anhNhoSP.isEmpty()) {
                images.add(anhNhoSP);
            }
            viewPager.setAdapter(new AnhPagerAdapter(this, images));

            // Set rating bar value if needed
            ratingBar.setRating(4.5f); // Example rating value, set as per your data
        }

        // Add click listener for the "Add to Cart" button
        addToCartButton.setOnClickListener(v -> {
            addToCart();
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });

        // Setup RecyclerView
        recyclerView = findViewById(R.id.spgoiy);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Fetch and display products
        new FetchSanPhamTask().execute();
    }

    private class FetchSanPhamTask extends AsyncTask<Void, Void, List<SanPham>> {

        @Override
        protected List<SanPham> doInBackground(Void... voids) {
            return databaseConnection.getAllBanChay(); // Use the new method
        }

        @Override
        protected void onPostExecute(List<SanPham> sanPhamList) {
            if (sanPhamList != null) {
                sanPhamAdapter = new SanPhamAdapter(sanPhamList, sessionManager);
                recyclerView.setAdapter(sanPhamAdapter);
            } else {
                Toast.makeText(ChiTietSanPhamActivity.this, "Không thể tải sản phẩm!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void addToCart() {
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userEmail = preferences.getString("user_email", null);

        if (userEmail != null) {
            SharedPreferences cartPreferences = getSharedPreferences("cart_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = cartPreferences.edit();

            // Retrieve the product details from the intent
            Intent intent = getIntent();
            String tenSP = intent.getStringExtra("TEN_SP");
            double giaSP = intent.getDoubleExtra("GIA_SP", 0);
            String anhSP = intent.getStringExtra("ANH_SP");
            String anhNhoSP = intent.getStringExtra("ANH_NHO_SP");

            // Check if the product already exists in the cart
            int itemCount = cartPreferences.getInt(userEmail + "_item_count", 0);
            boolean itemExists = false;

            for (int i = 1; i <= itemCount; i++) {
                String itemData = cartPreferences.getString(userEmail + "_product_" + i, null);
                if (itemData != null) {
                    String[] data = itemData.split(";");
                    if (data.length >= 5 && data[0].equals(tenSP)) {
                        // Product already exists, update quantity
                        int existingQuantity = Integer.parseInt(data[4]);
                        existingQuantity++;
                        editor.putString(userEmail + "_product_" + i, tenSP + ";" + giaSP + ";" + anhSP + ";" + anhNhoSP + ";" + existingQuantity);
                        itemExists = true;
                        break;
                    }
                }
            }

            if (!itemExists) {
                // Product does not exist, add new entry
                itemCount++;
                editor.putString(userEmail + "_product_" + itemCount, tenSP + ";" + giaSP + ";" + anhSP + ";" + anhNhoSP + ";1");
                editor.putInt(userEmail + "_item_count", itemCount);
            }

            editor.apply();
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        }

        // Refresh cart menu item
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trangchu, menu);
        updateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itGioHang) {
            // Open GioHangActivity
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
                itDangXuat.setVisible(true);  // Show "Đăng xuất"
            } else {
                itDangNhap.setTitle("Đăng nhập");
                itDangXuat.setVisible(false); // Hide "Đăng xuất"
            }
        }
    }
}
