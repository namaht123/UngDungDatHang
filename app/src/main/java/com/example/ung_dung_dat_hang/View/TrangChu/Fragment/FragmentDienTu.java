package com.example.ung_dung_dat_hang.View.TrangChu.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ung_dung_dat_hang.Adapter.SanPhamdientuAdapter;
import com.example.ung_dung_dat_hang.ConnnectInternet.DatabaseConnection;
import com.example.ung_dung_dat_hang.ConnnectInternet.SessionManager;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.SanPham;
import com.example.ung_dung_dat_hang.R;

import java.util.List;

public class FragmentDienTu extends Fragment {

    private DatabaseConnection databaseConnection;
    private SessionManager sessionManager;
    private RecyclerView recyclerView;
    private SanPhamdientuAdapter sanPhamdientuAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_mevabe, container, false);

        // Initialize the DatabaseConnection and SessionManager instances
        databaseConnection = new DatabaseConnection(requireContext());
        sessionManager = new SessionManager(requireContext());

        // Setup RecyclerView with GridLayoutManager
        recyclerView = view.findViewById(R.id.mevabe);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // 2 columns

        // Fetch and display products
        new FetchSanPhamTask().execute();

        return view;
    }

    private class FetchSanPhamTask extends AsyncTask<Void, Void, List<SanPham>> {

        @Override
        protected List<SanPham> doInBackground(Void... voids) {
            if (databaseConnection.checkConnection()) {
                return databaseConnection.getSanPhamMevaBeList(); // Fetch data from database
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<SanPham> sanPhamList) {
            if (sanPhamList != null) {
                // Pass the SessionManager instance to the adapter
                sanPhamdientuAdapter = new SanPhamdientuAdapter(sanPhamList, sessionManager);
                recyclerView.setAdapter(sanPhamdientuAdapter);
            } else {
                Toast.makeText(requireContext(), "Không thể tải sản phẩm!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
