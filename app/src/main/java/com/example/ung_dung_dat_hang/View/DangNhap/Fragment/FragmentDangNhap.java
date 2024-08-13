package com.example.ung_dung_dat_hang.View.DangNhap.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.ung_dung_dat_hang.View.TrangChu.ManHinhTrangChu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FragmentDangNhap extends Fragment {
    private EditText edDiaChiEmailDN, edMatKhauDN;
    private Button btnDangNhap;
    private DatabaseConnection db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_dangnhap, container, false);

        // Initialize views
        edDiaChiEmailDN = view.findViewById(R.id.taikhoandangnhap);
        edMatKhauDN = view.findViewById(R.id.editmatkhaudangnhap);
        btnDangNhap = view.findViewById(R.id.btndangnhap);

        // Initialize Database Connection
        db = new DatabaseConnection(getContext());

        // Set onClickListener for login button
        btnDangNhap.setOnClickListener(v -> dangNhapTaiKhoan());

        return view;
    }

    private void dangNhapTaiKhoan() {
        String email = edDiaChiEmailDN.getText().toString();
        String matKhau = edMatKhauDN.getText().toString();

        // Validate input
        if (email.isEmpty() || matKhau.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check login credentials
        new Thread(() -> {
            boolean isAuthenticated = authenticateUser(email, matKhau);
            getActivity().runOnUiThread(() -> {
                if (isAuthenticated) {
                    Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    redirectToHomePage();  // Redirect to home page after successful login
                } else {
                    Toast.makeText(getContext(), "Thông tin đăng nhập không chính xác", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private boolean authenticateUser(String email, String matKhau) {
        boolean isAuthenticated = false;
        Connection connection = db.getCon();
        if (connection != null) {
            try {
                String sql = "SELECT * FROM TaiKhoan WHERE Tentaikhoan = ? AND Matkhau = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, email);
                stmt.setString(2, matKhau);

                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    isAuthenticated = true;
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error authenticating user: " + e.getMessage());
            }
        }
        return isAuthenticated;
    }
    private void redirectToHomePage() {
        // Save user session
        SharedPreferences preferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_email", edDiaChiEmailDN.getText().toString());
        editor.apply();

        Intent intent = new Intent(getActivity(), ManHinhTrangChu.class);
        startActivity(intent);
        getActivity().finish();  // Close the login activity so the user cannot navigate back
    }

}
