package com.example.ung_dung_dat_hang.View.TrangChu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ung_dung_dat_hang.Adapter.SanPhamAdapter;
import com.example.ung_dung_dat_hang.ConnnectInternet.DatabaseConnection;
import com.example.ung_dung_dat_hang.ConnnectInternet.SessionManager;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.SanPham;
import com.example.ung_dung_dat_hang.R;

import java.util.ArrayList;
import java.util.List;

public class allsanphamActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SanPhamAdapter adapter;
    private ArrayList<SanPham> productList;
    private DatabaseConnection databaseConnection;
    private TextView searchTitle;
    private SearchView searchView;
    private String currentSearchQuery = "";
    private int currentMaThuongHieu = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allsanpham);

        // Initialize UI elements
        recyclerView = findViewById(R.id.rvAllSanPham);
        searchTitle = findViewById(R.id.searchTitle);
        searchView = findViewById(R.id.searchproduct); // Initialize SearchView

        // Initialize DatabaseConnection
        databaseConnection = new DatabaseConnection(this);

        // Set up SearchView query listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Update search query and fetch products
                currentSearchQuery = query;
                fetchProducts();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Update search query and fetch products
                currentSearchQuery = newText;
                fetchProducts();
                return true;
            }
        });

        // Get search query and brand ID from intent
        Intent intent = getIntent();
        currentSearchQuery = intent.getStringExtra("SEARCH_QUERY") != null ? intent.getStringExtra("SEARCH_QUERY") : "";
        currentMaThuongHieu = intent.getIntExtra("MA_THUONGHIEU", -1);

        // Fetch and display products
        fetchProducts();
    }

    private void fetchProducts() {
        new FetchSanPhamTask().execute(currentSearchQuery, currentMaThuongHieu);
    }

    private class FetchSanPhamTask extends AsyncTask<Object, Void, List<SanPham>> {

        @Override
        protected List<SanPham> doInBackground(Object... params) {
            String searchQuery = (String) params[0];
            int maThuongHieu = (int) params[1];

            if (databaseConnection.checkConnection()) {
                if (searchQuery == null || searchQuery.trim().isEmpty()) {
                    if (maThuongHieu == -1) {
                        return databaseConnection.getSanPhamList(); // Method to get all products
                    } else {
                        return databaseConnection.getSanPhamByBrand(maThuongHieu); // Method to get products by brand
                    }
                } else {
                    return databaseConnection.searchProductsByName(searchQuery);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<SanPham> sanPhamList) {
            if (sanPhamList != null) {
                productList = new ArrayList<>(sanPhamList); // Convert to ArrayList
                SessionManager sessionManager = new SessionManager(allsanphamActivity.this); // Initialize SessionManager

                // Initialize adapter
                adapter = new SanPhamAdapter(productList, sessionManager);

                // Set up RecyclerView with GridLayoutManager for 2 columns
                recyclerView.setLayoutManager(new GridLayoutManager(allsanphamActivity.this, 2)); // 2 columns
                recyclerView.setAdapter(adapter);

                // Update the search title
                if (currentSearchQuery == null || currentSearchQuery.trim().isEmpty()) {
                    if (currentMaThuongHieu != -1) {
                        searchTitle.setText("Sản phẩm từ thương hiệu ID: " + currentMaThuongHieu);
                    } else {
                        searchTitle.setText("Tất cả sản phẩm");
                    }
                } else {
                    searchTitle.setText("Kết quả tìm kiếm cho: " + currentSearchQuery);
                }
            } else {
                // Handle the case where data retrieval fails
                searchTitle.setText("Không tìm thấy sản phẩm.");
            }
        }
    }
}
