package com.example.ung_dung_dat_hang.View.LichSuDonHang;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ung_dung_dat_hang.ConnnectInternet.DatabaseConnection;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.DonDatHang;
import com.example.ung_dung_dat_hang.R;

import java.sql.SQLException;
import java.util.List;

public class DonHangCuaToiActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<DonDatHang> orderList;
    private DatabaseConnection dbConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donhangcuatoi);

        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbConnection = new DatabaseConnection(this);

        SharedPreferences userPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userEmail = userPreferences.getString("user_email", null);

        new LoadOrdersTask().execute(userEmail);
    }

    private class LoadOrdersTask extends AsyncTask<String, Void, List<DonDatHang>> {
        @Override
        protected List<DonDatHang> doInBackground(String... params) {
            try {
                return dbConnection.getOrdersByUserEmail(params[0]);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<DonDatHang> orders) {
            if (orders != null) {
                orderList = orders;
                orderAdapter = new OrderAdapter(orderList);
                recyclerView.setAdapter(orderAdapter);
            } else {
                Toast.makeText(DonHangCuaToiActivity.this, "Không thể tải đơn hàng.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
