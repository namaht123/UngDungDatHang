package com.example.ung_dung_dat_hang.View.TrangChu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.ung_dung_dat_hang.Adapter.ViewPagerAdapter;
import com.example.ung_dung_dat_hang.R;
import com.example.ung_dung_dat_hang.View.DangNhap.DangNhapActivity;
import com.example.ung_dung_dat_hang.View.GioHang.GioHangActivity;
import com.example.ung_dung_dat_hang.View.SearchResultsActivity;
import com.example.ung_dung_dat_hang.View.TrangChu.Fragment.FragmentGioHang;
import com.google.android.material.tabs.TabLayout;

public class ManHinhTrangChu extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manhinhtrangchu_layout);

        // Initialize UI elements
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewpager);
        drawerLayout = findViewById(R.id.drawerLayout);
        searchEditText = findViewById(R.id.timkiem);

        // Set up the toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // Set up the drawer toggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Set up ViewPager with TabLayout
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // Set up the search functionality
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String query = searchEditText.getText().toString().trim();
                performSearch(query);
                return true;
            }
            return false;
        });

        // Hiển thị FragmentGioHang nếu được chọn từ menu
        if (savedInstanceState == null && getIntent().getBooleanExtra("SHOW_CART_FRAGMENT", false)) {
            showFragmentGioHang();
        }
    }

    private void performSearch(String query) {
        // Create an instance of the search results activity
        Intent searchIntent = new Intent(this, SearchResultsActivity.class);
        searchIntent.putExtra("SEARCH_QUERY", query);
        startActivity(searchIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trangchu, menu);
        updateMenu(menu);  // Pass the menu object to updateMenu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        if (id == R.id.itGioHang) {
            // Mở GioHangActivity
            Intent intent = new Intent(this, GioHangActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.itDangNhap) {
            Intent iDangNhap = new Intent(this, DangNhapActivity.class);
            startActivity(iDangNhap);
            return true;
        } else if (id == R.id.itThongBao) {
            // Add action for notifications if needed
            return true;
        } else if (id == R.id.itdangxuat) {
            // Xóa thông tin đăng nhập
            SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("user_email");  // Xóa email hoặc thông tin cần thiết
            editor.apply();

            // Tải lại trang chủ
            Intent intent = new Intent(this, ManHinhTrangChu.class);
            startActivity(intent);
            finish();  // Kết thúc Activity hiện tại để không quay lại trang đã đăng nhập

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showFragmentGioHang() {
        // Tạo instance của FragmentGioHang
        Fragment fragment = new FragmentGioHang();

        // Lấy FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Tạo một giao dịch Fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Thay thế nội dung của container bằng FragmentGioHang
        fragmentTransaction.replace(R.id.container, fragment);

        // Thêm giao dịch vào back stack
        fragmentTransaction.addToBackStack(null);

        // Xác nhận giao dịch
        fragmentTransaction.commit();
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

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the menu
        invalidateOptionsMenu();
    }
}
