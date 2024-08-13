package com.example.ung_dung_dat_hang.View;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ung_dung_dat_hang.Adapter.SanPhamAdapter;
import com.example.ung_dung_dat_hang.ConnnectInternet.DatabaseConnection;
import com.example.ung_dung_dat_hang.ConnnectInternet.SessionManager;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.SanPham;
import com.example.ung_dung_dat_hang.R;
import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    private RecyclerView resultsRecyclerView;
    private SanPhamAdapter productAdapter;
    private List<SanPham> productList;
    private DatabaseConnection databaseHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        resultsRecyclerView = findViewById(R.id.resultsRecyclerView);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize productList and sessionManager
        productList = new ArrayList<>();
        sessionManager = new SessionManager(this); // Initialize SessionManager here

        // Initialize the adapter with both productList and sessionManager
        productAdapter = new SanPhamAdapter(productList, sessionManager);
        resultsRecyclerView.setAdapter(productAdapter);

        // Initialize DatabaseConnection with context
        databaseHelper = new DatabaseConnection(this);

        // Get search query from intent
        String query = getIntent().getStringExtra("SEARCH_QUERY");

        // Perform the search if query is not null
        if (query != null && !query.isEmpty()) {
            performSearch(query);
        }
    }

    private void performSearch(String query) {
        // Perform search with filters if needed
        productList.clear();  // Clear the existing list
//        List<SanPham> results = databaseHelper.searchProducts(query);
//        productList.addAll(results);

        // Update the RecyclerView with search results
        if (productAdapter != null) {
            productAdapter.notifyDataSetChanged();
        }
    }

}
