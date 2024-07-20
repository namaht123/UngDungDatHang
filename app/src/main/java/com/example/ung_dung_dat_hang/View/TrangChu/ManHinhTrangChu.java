package com.example.ung_dung_dat_hang.View.TrangChu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.ung_dung_dat_hang.Adapter.ViewPagerAdapter;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.LoaiSanPham;
import com.example.ung_dung_dat_hang.Presenter.Trangchu.XuLyMenu.PrensenterLogicXuLyMenu;
import com.example.ung_dung_dat_hang.R;
import com.example.ung_dung_dat_hang.View.ManHinhChao.ManHinhChaoActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class ManHinhTrangChu extends AppCompatActivity implements ViewXuLyMenu {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ExpandableListView expandableListView;
    DrawerLayout drawerLayout;

    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manhinhtrangchu_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        expandableListView = (ExpandableListView) findViewById(R.id.epMenu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        PrensenterLogicXuLyMenu logicXuLyMenu = new PrensenterLogicXuLyMenu(this);
        logicXuLyMenu.LayDanhSachMenu();
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void HienThiDanhSachMenu(List<LoaiSanPham> loaiSanPhamList) {
        if (loaiSanPhamList != null && !loaiSanPhamList.isEmpty()) {
            Log.d("kiemtra", loaiSanPhamList.get(0).getTENLOAISP());
        } else {
            Log.d("kiemtra", "Danh sách menu trống");
        }
    }

    @Override
    public void HienThiThongBaoLoi(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
