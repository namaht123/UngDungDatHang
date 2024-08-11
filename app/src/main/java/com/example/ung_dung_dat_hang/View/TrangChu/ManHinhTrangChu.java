package com.example.ung_dung_dat_hang.View.TrangChu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.ung_dung_dat_hang.View.TrangChu.Fragment.FragmentGioHang;
import com.google.android.material.tabs.TabLayout;

public class ManHinhTrangChu extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manhinhtrangchu_layout);

        // Initialize UI elements
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewpager);
        drawerLayout = findViewById(R.id.drawerLayout);

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

        // Hiển thị FragmentGioHang nếu được chọn từ menu
        if (savedInstanceState == null && getIntent().getBooleanExtra("SHOW_CART_FRAGMENT", false)) {
            showFragmentGioHang();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trangchu, menu);
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
}
