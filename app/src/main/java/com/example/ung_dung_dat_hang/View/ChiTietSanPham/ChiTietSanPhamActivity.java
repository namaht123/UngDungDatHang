package com.example.ung_dung_dat_hang.View.ChiTietSanPham;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.ung_dung_dat_hang.R;
import java.util.ArrayList;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    private TextView tenSPTextView, giaTextView, thongTinTextView;
    private ViewPager viewPager;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chitietsanpham);

        tenSPTextView = findViewById(R.id.tenSPTextView);
        giaTextView = findViewById(R.id.giaTextView);
        thongTinTextView = findViewById(R.id.thongTinTextView);
        viewPager = findViewById(R.id.viewpager_liveshow);
        ratingBar = findViewById(R.id.ratingBar);

        // Get the data from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String tenSP = intent.getStringExtra("TEN_SP");
            double giaSP = intent.getDoubleExtra("GIA_SP", 0);
            String thongTinSP = intent.getStringExtra("THONGTIN_SP");
            String anhSP = intent.getStringExtra("ANH_SP");
            String anhNhoSP = intent.getStringExtra("ANH_NHO_SP");

            // Set the data to the views
            tenSPTextView.setText(tenSP);
            giaTextView.setText(String.format("%,.0f VND", giaSP));
            thongTinTextView.setText(thongTinSP);

            // Load images into ViewPager
            ArrayList<String> images = new ArrayList<>();
            images.add(anhSP); // Large image
            if (anhNhoSP != null && !anhNhoSP.isEmpty()) {
                images.add(anhNhoSP);
            }

            viewPager.setAdapter(new AnhPagerAdapter(this, images));


            // Set rating bar value if needed
            ratingBar.setRating(4.5f); // Example rating value, set as per your data
        }
    }
}
