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
    private ArrayList<SanPham> productList; // Use ArrayList
    private DatabaseConnection databaseConnection;
    private TextView searchTitle;
    private SearchView searchView;

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
                // Trigger search when user submits a query
                new FetchSanPhamTask().execute(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Trigger search as user types
                new FetchSanPhamTask().execute(newText);
                return true;
            }
        });

        // Get search query from intent
        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra("SEARCH_QUERY");

        // Fetch and display products
        new FetchSanPhamTask().execute(searchQuery);
    }

    private class FetchSanPhamTask extends AsyncTask<String, Void, List<SanPham>> {

        @Override
        protected List<SanPham> doInBackground(String... params) {
            String searchQuery = params[0];
            if (databaseConnection.checkConnection()) {
                if (searchQuery == null || searchQuery.trim().isEmpty()) {
                    return databaseConnection.getSanPhamList(); // Method to get all products
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
                Intent intent = getIntent();
                String searchQuery = intent.getStringExtra("SEARCH_QUERY");
                if (searchQuery == null || searchQuery.trim().isEmpty()) {
                    searchTitle.setText("All Products");
                } else {
                    searchTitle.setText("Search results for: " + searchQuery);
                }
            } else {
                // Handle the case where data retrieval fails
                searchTitle.setText("No products found.");
            }
        }
    }
}
