package com.example.ung_dung_dat_hang.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ung_dung_dat_hang.View.TrangChu.Fragment.FragmentChuongTrinhKhuyenMai;
import com.example.ung_dung_dat_hang.View.TrangChu.Fragment.FragmentDienTu;
import com.example.ung_dung_dat_hang.View.TrangChu.Fragment.FragmentGioHang;
import com.example.ung_dung_dat_hang.View.TrangChu.Fragment.FragmentLamDep;
import com.example.ung_dung_dat_hang.View.TrangChu.Fragment.FragmentMeVaBe;
import com.example.ung_dung_dat_hang.View.TrangChu.Fragment.FragmentNhaCuaVaDoiSong;
import com.example.ung_dung_dat_hang.View.TrangChu.Fragment.FragmentNoiiBatt;
import com.example.ung_dung_dat_hang.View.TrangChu.Fragment.FragmentTheThaoVaDuLich;
import com.example.ung_dung_dat_hang.View.TrangChu.Fragment.FragmentThoiTrang;
import com.example.ung_dung_dat_hang.View.TrangChu.Fragment.FragmentThuongHieu;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> listFragment = new ArrayList<Fragment>();
    List<String> titleFragment = new ArrayList<String>();
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        listFragment.add(new FragmentNoiiBatt());
        listFragment.add(new FragmentChuongTrinhKhuyenMai());
        listFragment.add(new FragmentNhaCuaVaDoiSong());
        listFragment.add(new FragmentDienTu());
        listFragment.add(new FragmentMeVaBe());
        listFragment.add(new FragmentLamDep());
        listFragment.add(new FragmentThoiTrang());
        listFragment.add(new FragmentTheThaoVaDuLich());
        listFragment.add(new FragmentThuongHieu());

        titleFragment.add("Nổi bật");
        titleFragment.add("Chương trình khuyến mãi");
        titleFragment.add("Nhà của & đời sống");
        titleFragment.add("Điện tử");
        titleFragment.add("Mẹ & bé");
        titleFragment.add("Làm đẹp");
        titleFragment.add("Thời trang");
        titleFragment.add("Thể thao & du lịch");
        titleFragment.add("Thương hiệu");



    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleFragment.get(position);
    }
}
