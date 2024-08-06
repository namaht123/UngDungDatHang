package com.example.ung_dung_dat_hang.View.DangNhap;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.ung_dung_dat_hang.Adapter.ViewPagerAdapterDangNhap;
import com.example.ung_dung_dat_hang.R;
import com.google.android.material.tabs.TabLayout;

public class DangNhapActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        tabLayout = (TabLayout) findViewById(R.id.tabDangNhap);
        viewPager = (ViewPager) findViewById(R.id.viewpagerDangNhap);
        toolbar = (Toolbar) findViewById(R.id.toolbarDangNhap);

        setSupportActionBar(toolbar);

        ViewPagerAdapterDangNhap viewPagerAdapterDangNhap = new ViewPagerAdapterDangNhap(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapterDangNhap);
        viewPagerAdapterDangNhap.notifyDataSetChanged();;

        tabLayout.setupWithViewPager(viewPager);


    }
}