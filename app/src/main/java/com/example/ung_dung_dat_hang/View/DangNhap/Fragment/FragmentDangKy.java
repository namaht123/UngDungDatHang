package com.example.ung_dung_dat_hang.View.DangNhap.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ung_dung_dat_hang.ConnnectInternet.DatabaseConnection;
import com.example.ung_dung_dat_hang.R;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FragmentDangKy extends Fragment {
    EditText edHoTenDK, edDiaChiEmailDK, edMatKhauDK, edNhapLaiMatKhauDK;
    Button btnDangKy;
    DatabaseConnection db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_dangky, container, false);

        // Initialize views
        edHoTenDK = view.findViewById(R.id.edHoTenDK);
        edDiaChiEmailDK = view.findViewById(R.id.edDiaChiEmailDK);
        edMatKhauDK = view.findViewById(R.id.edMatKhauDK);
        edNhapLaiMatKhauDK = view.findViewById(R.id.ednhaplaiMatKhauDK);
        btnDangKy = view.findViewById(R.id.btndangky);

        // Initialize Database Connection
        db = new DatabaseConnection(getContext());

        // Set onClickListener for registration button
        btnDangKy.setOnClickListener(v -> dangKyTaiKhoan());

        return view;
    }

    private void dangKyTaiKhoan() {
        String tenDangNhap = edHoTenDK.getText().toString();
        String tentaikhoan = edDiaChiEmailDK.getText().toString();
        String matKhau = edMatKhauDK.getText().toString();
        String nhapLaiMatKhau = edNhapLaiMatKhauDK.getText().toString();

        // Validate input
        if (tenDangNhap.isEmpty() || tentaikhoan.isEmpty() || matKhau.isEmpty() || nhapLaiMatKhau.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!matKhau.equals(nhapLaiMatKhau)) {
            Toast.makeText(getContext(), "Mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert new account into the database
        new Thread(() -> {
            boolean isInserted = insertTaiKhoan(tenDangNhap, tentaikhoan, matKhau);
            getActivity().runOnUiThread(() -> {
                if (isInserted) {
                    Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    // Redirect to login or another action
                } else {
                    Toast.makeText(getContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private boolean insertTaiKhoan(String tenDangNhap, String tentaikhoan, String matKhau) {
        boolean success = false;
        Connection connection = db.getCon();
        if (connection != null) {
            try {
                String sql = "INSERT INTO TaiKhoan (Tentaikhoan, Tendangnhap, Matkhau, MaloaiTK, Diachi, Ngaysinh, Sdt, Gioitinh) " +
                        "VALUES (?, ?, ?, 2, NULL, NULL, NULL, NULL)";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, tentaikhoan); // Tentaikhoan
                stmt.setString(2, tenDangNhap); // Tendangnhap
                stmt.setString(3, matKhau); // Matkhau

                int rowsInserted = stmt.executeUpdate();
                success = rowsInserted > 0;

            } catch (SQLException e) {
                Log.e("Database Error", "Error inserting TaiKhoan: " + e.getMessage());
            }
        }
        return success;
    }

}
