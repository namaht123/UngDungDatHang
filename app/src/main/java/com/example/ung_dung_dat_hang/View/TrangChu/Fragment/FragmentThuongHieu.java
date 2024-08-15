package com.example.ung_dung_dat_hang.View.TrangChu.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ung_dung_dat_hang.Adapter.ThuongHieuAdapter;
import com.example.ung_dung_dat_hang.ConnnectInternet.DatabaseConnection;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.SanPham;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.ThuongHieu;
import com.example.ung_dung_dat_hang.R;
import com.example.ung_dung_dat_hang.View.TrangChu.allsanphamActivity;

import java.util.List;

public class FragmentThuongHieu extends Fragment {

    private DatabaseConnection databaseConnection;
    private RecyclerView recyclerView;
    private ThuongHieuAdapter thuongHieuAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_thuonghieu, container, false);

        // Initialize the DatabaseConnection instance with context
        databaseConnection = new DatabaseConnection(requireContext());

        // Setup RecyclerView with GridLayoutManager
        recyclerView = view.findViewById(R.id.thuonghieu);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // 2 columns

        // Fetch and display brands
        new FetchThuongHieuTask().execute();
        return view;
    }

    private class FetchThuongHieuTask extends AsyncTask<Void, Void, List<ThuongHieu>> {

        @Override
        protected List<ThuongHieu> doInBackground(Void... voids) {
            return databaseConnection.getAllthuonghieu();
        }

        @Override
        protected void onPostExecute(List<ThuongHieu> thuongHieulist) {
            if (thuongHieulist != null) {
                thuongHieuAdapter = new ThuongHieuAdapter(thuongHieulist, thuongHieu -> {
                    // Handle brand click
                    Intent intent = new Intent(getActivity(), allsanphamActivity.class);
                    intent.putExtra("MA_THUONGHIEU", thuongHieu.getMathuonghieu()); // Pass the brand ID
                    startActivity(intent);
                });
                recyclerView.setAdapter(thuongHieuAdapter);
            } else {
                Toast.makeText(requireContext(), "Không thể tải thương hiệu!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
