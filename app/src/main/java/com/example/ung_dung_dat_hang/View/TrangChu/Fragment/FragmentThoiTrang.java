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

import com.example.ung_dung_dat_hang.Adapter.SPthoitrangAdapter;
import com.example.ung_dung_dat_hang.Adapter.SanPhamAdapter;
import com.example.ung_dung_dat_hang.ConnnectInternet.DatabaseConnection;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.SanPham;
import com.example.ung_dung_dat_hang.R;

import java.util.List;

public class FragmentThoiTrang extends Fragment {
    private DatabaseConnection databaseConnection;
    private RecyclerView recyclerView;
    private SPthoitrangAdapter spthoitrangAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_thoitrang,container,false);
        // Initialize the DatabaseConnection instance with context
        databaseConnection = new DatabaseConnection(requireContext());

        // Setup RecyclerView with GridLayoutManager
        recyclerView = view.findViewById(R.id.thoitrang);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // 2 columns

        // Fetch and display products
        new FragmentThoiTrang.FetchSanPhamTask().execute();
        return view;
    }
    private class FetchSanPhamTask extends AsyncTask<Void, Void, List<SanPham>> {

        @Override
        protected List<SanPham> doInBackground(Void... voids) {
            return databaseConnection.getSanPhamthoitrangList(); // Use the new method
        }

        @Override
        protected void onPostExecute(List<SanPham> sanPhamList) {
            if (sanPhamList != null) {
                spthoitrangAdapter = new SPthoitrangAdapter(sanPhamList);
                recyclerView.setAdapter(spthoitrangAdapter);
            } else {
                Toast.makeText(requireContext(), "Không thể tải sản phẩm!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
