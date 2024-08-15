package com.example.ung_dung_dat_hang.View.TrangChu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

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
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manhinhtrangchu_layout);

        // Initialize UI elements
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewpager);
        drawerLayout = findViewById(R.id.drawerLayout);
        searchView = findViewById(R.id.timkiem); // Ensure this ID is correct

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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Start allsanphamActivity with search query
                startAllProductActivity(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Optionally handle text changes if needed
                return false;
            }
        });

        // Show FragmentGioHang if selected from menu
        if (savedInstanceState == null && getIntent().getBooleanExtra("SHOW_CART_FRAGMENT", false)) {
            showFragmentGioHang();
        }
    }

    private void startAllProductActivity(String searchQuery) {
        Intent intent = new Intent(ManHinhTrangChu.this, allsanphamActivity.class);
        if (!searchQuery.trim().isEmpty()) {
            intent.putExtra("SEARCH_QUERY", searchQuery);
        }
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trangchu, menu);
        updateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        if (id == R.id.itGioHang) {
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
            // Clear login information
            SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("user_email");
            editor.apply();

            // Reload the main screen
            Intent intent = new Intent(this, ManHinhTrangChu.class);
            startActivity(intent);
            finish();

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showFragmentGioHang() {
        Fragment fragment = new FragmentGioHang();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
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
                itDangXuat.setVisible(true);
            } else {
                itDangNhap.setTitle("Đăng Nhập");
                itDangNhap.setIcon(R.drawable.icon_mcuoi);
                itDangXuat.setVisible(false);
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
