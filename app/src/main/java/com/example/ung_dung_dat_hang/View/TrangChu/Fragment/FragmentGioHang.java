package com.example.ung_dung_dat_hang.View.TrangChu.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ung_dung_dat_hang.R;

public class FragmentGioHang extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("FragmentGioHang", "onCreateView called");
        return inflater.inflate(R.layout.fragment_gio_hang, container, false);
    }
}

