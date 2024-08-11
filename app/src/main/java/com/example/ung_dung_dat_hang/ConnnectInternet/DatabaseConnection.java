package com.example.ung_dung_dat_hang.ConnnectInternet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.ung_dung_dat_hang.Model.ObjeactClass.SanPham;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private Connection con;
    private String uname, pass, ip, port, database;
    private Context context;

    public DatabaseConnection(Context context) {
        this.context = context;
        new DatabaseConnectionTask().execute();
    }

    private class DatabaseConnectionTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            ip = "10.0.2.2"; // IP address for localhost in Android Emulator
            database = "data_Lazada";
            uname = "TQD";
            pass = "123";
            port = "1433"; // Default port for SQL Server
            String ConnectionURL;
            try {
                // Load the JDBC driver
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + database + ";user=" + uname + ";password=" + pass;
                con = DriverManager.getConnection(ConnectionURL);
                return con != null && !con.isClosed();
            } catch (ClassNotFoundException e) {
                Log.e("Database Error", "JDBC Driver not found: " + e.getMessage());
            } catch (SQLException e) {
                Log.e("Database Error", "SQL error: " + e.getMessage());
            } catch (Exception e) {
                Log.e("Database Error", "Unexpected error: " + e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean isConnected) {
            if (isConnected) {
                Toast.makeText(context, "Kết nối cơ sở dữ liệu thành công!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Không thể kết nối đến cơ sở dữ liệu!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public Connection getCon() {
        return con;
    }

    public boolean checkConnection() {
        boolean isConnected = false;
        try {
            if (con != null && !con.isClosed()) {
                isConnected = true;
            }
        } catch (SQLException e) {
            Log.e("Connection Error", "Connection check error: " + e.getMessage());
        }
        return isConnected;
    }

    // New method to fetch products
    public List<SanPham> getSanPhamList() {
        List<SanPham> list = new ArrayList<>();
        Connection connection = getCon(); // Use the correct reference
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM SanPham"; // Modify as needed
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maSP = rs.getInt("MaSP");
                    String tenSP = rs.getString("TenSP");
                    double gia = rs.getDouble("Gia");
                    String thongTin = rs.getString("Thong_tin");
                    String anh = rs.getString("Anh");
                    int soLuong = rs.getInt("SoLuong");
                    String anhNho = rs.getString("AnhNho");
                    int maLoai = rs.getInt("MaLoai");
                    int maThuongHieu = rs.getInt("Mathuonghieu");
                    list.add(new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu));
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error fetching products: " + e.getMessage());
            }
        }
        return list;
    }
    public List<SanPham> getSanPhamMevaBeList() {
        List<SanPham> list = new ArrayList<>();
        Connection connection = getCon(); // Use the correct reference
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM SanPham WHERE MaLoai = 3 "; // Modify as needed
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maSP = rs.getInt("MaSP");
                    String tenSP = rs.getString("TenSP");
                    double gia = rs.getDouble("Gia");
                    String thongTin = rs.getString("Thong_tin");
                    String anh = rs.getString("Anh");
                    int soLuong = rs.getInt("SoLuong");
                    String anhNho = rs.getString("AnhNho");
                    int maLoai = rs.getInt("MaLoai");
                    int maThuongHieu = rs.getInt("Mathuonghieu");
                    list.add(new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu));
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error fetching products: " + e.getMessage());
            }
        }
        return list;
    }
    public List<SanPham> getSanPhamnhavadoisongList() {
        List<SanPham> list = new ArrayList<>();
        Connection connection = getCon(); // Use the correct reference
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM SanPham WHERE MaLoai = 1 "; // Modify as needed
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maSP = rs.getInt("MaSP");
                    String tenSP = rs.getString("TenSP");
                    double gia = rs.getDouble("Gia");
                    String thongTin = rs.getString("Thong_tin");
                    String anh = rs.getString("Anh");
                    int soLuong = rs.getInt("SoLuong");
                    String anhNho = rs.getString("AnhNho");
                    int maLoai = rs.getInt("MaLoai");
                    int maThuongHieu = rs.getInt("Mathuonghieu");
                    list.add(new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu));
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error fetching products: " + e.getMessage());
            }
        }
        return list;
    }
    public List<SanPham> getSanPhamdientuList() {
        List<SanPham> list = new ArrayList<>();
        Connection connection = getCon(); // Use the correct reference
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM SanPham WHERE MaLoai = 2 "; // Modify as needed
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maSP = rs.getInt("MaSP");
                    String tenSP = rs.getString("TenSP");
                    double gia = rs.getDouble("Gia");
                    String thongTin = rs.getString("Thong_tin");
                    String anh = rs.getString("Anh");
                    int soLuong = rs.getInt("SoLuong");
                    String anhNho = rs.getString("AnhNho");
                    int maLoai = rs.getInt("MaLoai");
                    int maThuongHieu = rs.getInt("Mathuonghieu");
                    list.add(new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu));
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error fetching products: " + e.getMessage());
            }
        }
        return list;
    }
    public List<SanPham> getSanPhamlamdepList() {
        List<SanPham> list = new ArrayList<>();
        Connection connection = getCon(); // Use the correct reference
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM SanPham WHERE MaLoai = 4 "; // Modify as needed
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maSP = rs.getInt("MaSP");
                    String tenSP = rs.getString("TenSP");
                    double gia = rs.getDouble("Gia");
                    String thongTin = rs.getString("Thong_tin");
                    String anh = rs.getString("Anh");
                    int soLuong = rs.getInt("SoLuong");
                    String anhNho = rs.getString("AnhNho");
                    int maLoai = rs.getInt("MaLoai");
                    int maThuongHieu = rs.getInt("Mathuonghieu");
                    list.add(new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu));
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error fetching products: " + e.getMessage());
            }
        }
        return list;
    }
    public List<SanPham> getSanPhamthoitrangList() {
        List<SanPham> list = new ArrayList<>();
        Connection connection = getCon(); // Use the correct reference
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM SanPham WHERE MaLoai = 5 "; // Modify as needed
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maSP = rs.getInt("MaSP");
                    String tenSP = rs.getString("TenSP");
                    double gia = rs.getDouble("Gia");
                    String thongTin = rs.getString("Thong_tin");
                    String anh = rs.getString("Anh");
                    int soLuong = rs.getInt("SoLuong");
                    String anhNho = rs.getString("AnhNho");
                    int maLoai = rs.getInt("MaLoai");
                    int maThuongHieu = rs.getInt("Mathuonghieu");
                    list.add(new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu));
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error fetching products: " + e.getMessage());
            }
        }
        return list;
    }
    public List<SanPham> getSanPhamdulichList() {
        List<SanPham> list = new ArrayList<>();
        Connection connection = getCon(); // Use the correct reference
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM SanPham WHERE MaLoai = 5 "; // Modify as needed
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maSP = rs.getInt("MaSP");
                    String tenSP = rs.getString("TenSP");
                    double gia = rs.getDouble("Gia");
                    String thongTin = rs.getString("Thong_tin");
                    String anh = rs.getString("Anh");
                    int soLuong = rs.getInt("SoLuong");
                    String anhNho = rs.getString("AnhNho");
                    int maLoai = rs.getInt("MaLoai");
                    int maThuongHieu = rs.getInt("Mathuonghieu");
                    list.add(new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu));
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error fetching products: " + e.getMessage());
            }
        }
        return list;
    }
}
