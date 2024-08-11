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

import com.example.ung_dung_dat_hang.Adapter.SPgiamgiaAdapter;
import com.example.ung_dung_dat_hang.ConnnectInternet.DatabaseConnection;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.SanPhamKhuyenMai;
import com.example.ung_dung_dat_hang.R;

import java.util.List;

public class FragmentChuongTrinhKhuyenMai extends Fragment {
    private DatabaseConnection databaseConnection;
    private RecyclerView recyclerView;
    private SPgiamgiaAdapter sPgiamgiaAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layput_chuongtrinh_khuyenmai, container, false);

        // Initialize the DatabaseConnection instance with context
        databaseConnection = new DatabaseConnection(requireContext());

        // Setup RecyclerView with GridLayoutManager
        recyclerView = view.findViewById(R.id.khuyenmai);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // 2 columns

        // Fetch and display products
        new FetchSanPhamTask().execute();
        return view;
    }

    private class FetchSanPhamTask extends AsyncTask<Void, Void, List<SanPhamKhuyenMai>> {

        @Override
        protected List<SanPhamKhuyenMai> doInBackground(Void... voids) {
            return databaseConnection.getAllSanPhamKhuyenMai(); // Use the updated method
        }

        @Override
        protected void onPostExecute(List<SanPhamKhuyenMai> sanPhamList) {
            if (sanPhamList != null && !sanPhamList.isEmpty()) {
                sPgiamgiaAdapter = new SPgiamgiaAdapter(sanPhamList);
                recyclerView.setAdapter(sPgiamgiaAdapter);
            } else {
                Toast.makeText(requireContext(), "Không thể tải sản phẩm!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
