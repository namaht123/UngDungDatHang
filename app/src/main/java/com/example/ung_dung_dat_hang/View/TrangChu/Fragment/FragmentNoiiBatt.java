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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ung_dung_dat_hang.Adapter.SanPhamAdapter;
import com.example.ung_dung_dat_hang.ConnnectInternet.DatabaseConnection;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.SanPham;
import com.example.ung_dung_dat_hang.R;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FragmentNoiiBatt extends Fragment {

    private DatabaseConnection databaseConnection;
    private RecyclerView recyclerView;
    private SanPhamAdapter sanPhamAdapter;
    private List<SanPham> sanPhamList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_noibat, container, false);

        // Initialize the DatabaseConnection instance with context
        databaseConnection = new DatabaseConnection(requireContext());

        // Setup RecyclerView with GridLayoutManager
        recyclerView = view.findViewById(R.id.spnoibat);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // 2 columns

        // Fetch and display products
        new FetchSanPhamTask().execute();

        return view;
    }

    private class FetchSanPhamTask extends AsyncTask<Void, Void, List<SanPham>> {

        @Override
        protected List<SanPham> doInBackground(Void... voids) {
            List<SanPham> list = new ArrayList<>();
            Connection con = databaseConnection.getCon();
            if (con != null) {
                try {
                    Statement stmt = con.createStatement();
                    String query = "SELECT * FROM SanPham"; // Thay điều kiện để chọn sản phẩm nổi bật
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        int maSP = rs.getInt("MaSP");
                        String tenSP = rs.getString("TenSP");
                        double gia = rs.getDouble("Gia");
                        String thongTin = rs.getString("Thong_tin");
                        String anh = rs.getString("Anh");
                        int soLuong = rs.getInt("SoLuong"); // Thêm thuộc tính SoLuong
                        String anhNho = rs.getString("AnhNho"); // Thêm thuộc tính AnhNho
                        int maLoai = rs.getInt("MaLoai"); // Thêm thuộc tính MaLoai
                        int maThuongHieu = rs.getInt("Mathuonghieu"); // Thêm thuộc tính MaThuongHieu

                        list.add(new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<SanPham> sanPhamList) {
            if (sanPhamList != null) {
                sanPhamAdapter = new SanPhamAdapter(sanPhamList);
                recyclerView.setAdapter(sanPhamAdapter);
            } else {
                Toast.makeText(requireContext(), "Không thể tải sản phẩm!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
