package com.example.ung_dung_dat_hang.View.ManHinhChao;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.ung_dung_dat_hang.R;
import com.example.ung_dung_dat_hang.View.TrangChu.ManHinhTrangChu;

public class ManHinhChaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manhinhchao_layout);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                }catch (Exception e){

                }finally  {
                    Intent intent = new Intent(ManHinhChaoActivity.this, ManHinhTrangChu.class);
                    startActivity(intent);
                }
            }
        });
        thread.start();
    }
}