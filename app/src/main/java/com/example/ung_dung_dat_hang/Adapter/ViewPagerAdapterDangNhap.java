package com.example.ung_dung_dat_hang.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ung_dung_dat_hang.View.DangNhap.Fragment.FragmentDangKy;
import com.example.ung_dung_dat_hang.View.DangNhap.Fragment.FragmentDangNhap;

public class ViewPagerAdapterDangNhap extends FragmentPagerAdapter {
    public ViewPagerAdapterDangNhap(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                FragmentDangNhap fragmentDangNhap = new FragmentDangNhap();
                return fragmentDangNhap;
            case 1:
                FragmentDangKy fragmentDangKy = new FragmentDangKy();
                return fragmentDangKy;

            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :

                return "Đăng Nhập";
            case 1:

                return "Đăng Ký";

            default: return null;
        }

    }
}
