package com.example.ung_dung_dat_hang.View.GioHang;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ung_dung_dat_hang.R;
import com.example.ung_dung_dat_hang.View.TrangChu.Fragment.FragmentGioHang;

public class GioHangActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new FragmentGioHang())
                    .commit();
        }
    }
}
