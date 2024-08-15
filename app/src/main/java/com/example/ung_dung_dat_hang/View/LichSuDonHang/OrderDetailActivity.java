package com.example.ung_dung_dat_hang.View.LichSuDonHang;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ung_dung_dat_hang.ConnnectInternet.DatabaseConnection;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.ChiTietDonDatHang;
import com.example.ung_dung_dat_hang.R;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderDetailAdapter orderDetailAdapter;
    private List<ChiTietDonDatHang> orderDetailList;
    private DatabaseConnection dbConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        recyclerView = findViewById(R.id.recyclerViewOrderDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbConnection = new DatabaseConnection(this);

        int madondathang = getIntent().getIntExtra("Madondathang", -1);
        if (madondathang != -1) {
            new LoadOrderDetailsTask().execute(madondathang);
        }
    }

    private class LoadOrderDetailsTask extends AsyncTask<Integer, Void, List<ChiTietDonDatHang>> {
        @Override
        protected List<ChiTietDonDatHang> doInBackground(Integer... params) {
            try {
                return dbConnection.getProductsByOrderId(params[0]);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ChiTietDonDatHang> orderDetails) {
            if (orderDetails != null && !orderDetails.isEmpty()) {
                orderDetailList = orderDetails;
                orderDetailAdapter = new OrderDetailAdapter(orderDetailList);
                recyclerView.setAdapter(orderDetailAdapter);
            } else {
                Toast.makeText(OrderDetailActivity.this, "Không thể tải chi tiết đơn hàng.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
