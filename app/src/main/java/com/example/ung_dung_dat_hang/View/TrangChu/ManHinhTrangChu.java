package com.example.ung_dung_dat_hang.View.TrangChu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.ung_dung_dat_hang.Adapter.ViewPagerAdapter;
import com.example.ung_dung_dat_hang.R;
import com.example.ung_dung_dat_hang.View.ManHinhChao.ManHinhChaoActivity;
import com.google.android.material.tabs.TabLayout;

public class ManHinhTrangChu extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manhinhtrangchu_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trangchu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}