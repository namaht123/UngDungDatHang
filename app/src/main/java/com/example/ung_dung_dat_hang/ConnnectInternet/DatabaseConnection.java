package com.example.ung_dung_dat_hang.ConnnectInternet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.ung_dung_dat_hang.Model.ObjeactClass.SanPham;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.SanPhamKhuyenMai;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.ThuongHieu;

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
            uname = "Sang";
            pass = "261102";
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
        Connection connection = getCon(); // Ensure this is correct
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT sp.MaSP AS maSP, " +
                        "       sp.TenSP AS tenSP, " +
                        "       sp.Gia AS gia, " +
                        "       sp.Thong_tin AS thongTin, " +
                        "       sp.Anh AS anh, " +
                        "       sp.SoLuong AS soLuong, " +
                        "       sp.AnhNho AS anhNho, " +
                        "       sp.MaLoai AS maLoai, " +
                        "       sp.MaThuongHieu AS maThuongHieu, " +
                        "       COALESCE(spkm.PhanTramKhuyenMai, 0) AS phanTramKhuyenMai " +
                        "FROM SanPham sp " +
                        "LEFT JOIN SanPhamKhuyenMai spkm ON sp.MaSP = spkm.MaSP AND " +
                        "                                    GETDATE() BETWEEN spkm.NgayBatDauKM AND spkm.NgayKetThucKM " +
                        "WHERE sp.MaLoai = 3"; // Modify as needed

                Log.d("DatabaseQuery", "Executing query: " + query);
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maSP = rs.getInt("maSP");
                    String tenSP = rs.getString("tenSP");
                    double gia = rs.getDouble("gia");
                    String thongTin = rs.getString("thongTin");
                    String anh = rs.getString("anh");
                    int soLuong = rs.getInt("soLuong");
                    String anhNho = rs.getString("anhNho");
                    int maLoai = rs.getInt("maLoai");
                    int maThuongHieu = rs.getInt("maThuongHieu");
                    double phanTramKhuyenMai = rs.getDouble("phanTramKhuyenMai");

                    // Store discount percentage in session
                    SessionManager sessionManager = new SessionManager(getContext()); // Replace with actual context
                    sessionManager.setDiscountPercentage(maSP, phanTramKhuyenMai);

                    // Create SanPham object
                    SanPham sanPham = new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu);
                    list.add(sanPham);
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error fetching products: " + e.getMessage());
            }
        } else {
            Log.e("Database Connection", "Connection is null.");
        }
        return list;
    }
    public List<SanPham> getSanPhamnhavadoisongList() {
        List<SanPham> list = new ArrayList<>();
        Connection connection = getCon(); // Ensure this is correct
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT sp.MaSP AS maSP, " +
                        "       sp.TenSP AS tenSP, " +
                        "       sp.Gia AS gia, " +
                        "       sp.Thong_tin AS thongTin, " +
                        "       sp.Anh AS anh, " +
                        "       sp.SoLuong AS soLuong, " +
                        "       sp.AnhNho AS anhNho, " +
                        "       sp.MaLoai AS maLoai, " +
                        "       sp.MaThuongHieu AS maThuongHieu, " +
                        "       COALESCE(spkm.PhanTramKhuyenMai, 0) AS phanTramKhuyenMai " +
                        "FROM SanPham sp " +
                        "LEFT JOIN SanPhamKhuyenMai spkm ON sp.MaSP = spkm.MaSP AND " +
                        "                                    GETDATE() BETWEEN spkm.NgayBatDauKM AND spkm.NgayKetThucKM " +
                        "WHERE sp.MaLoai = 1"; // Modify as needed

                Log.d("DatabaseQuery", "Executing query: " + query);
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maSP = rs.getInt("maSP");
                    String tenSP = rs.getString("tenSP");
                    double gia = rs.getDouble("gia");
                    String thongTin = rs.getString("thongTin");
                    String anh = rs.getString("anh");
                    int soLuong = rs.getInt("soLuong");
                    String anhNho = rs.getString("anhNho");
                    int maLoai = rs.getInt("maLoai");
                    int maThuongHieu = rs.getInt("maThuongHieu");
                    double phanTramKhuyenMai = rs.getDouble("phanTramKhuyenMai");

                    // Store discount percentage in session
                    SessionManager sessionManager = new SessionManager(getContext()); // Replace with actual context
                    sessionManager.setDiscountPercentage(maSP, phanTramKhuyenMai);

                    // Create SanPham object
                    SanPham sanPham = new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu);
                    list.add(sanPham);
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error fetching products: " + e.getMessage());
            }
        } else {
            Log.e("Database Connection", "Connection is null.");
        }
        return list;
    }
    public List<SanPham> getSanPhamdientuList() {
        List<SanPham> list = new ArrayList<>();
        Connection connection = getCon(); // Ensure this is correct
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT sp.MaSP AS maSP, " +
                        "       sp.TenSP AS tenSP, " +
                        "       sp.Gia AS gia, " +
                        "       sp.Thong_tin AS thongTin, " +
                        "       sp.Anh AS anh, " +
                        "       sp.SoLuong AS soLuong, " +
                        "       sp.AnhNho AS anhNho, " +
                        "       sp.MaLoai AS maLoai, " +
                        "       sp.MaThuongHieu AS maThuongHieu, " +
                        "       COALESCE(spkm.PhanTramKhuyenMai, 0) AS phanTramKhuyenMai " +
                        "FROM SanPham sp " +
                        "LEFT JOIN SanPhamKhuyenMai spkm ON sp.MaSP = spkm.MaSP AND " +
                        "                                    GETDATE() BETWEEN spkm.NgayBatDauKM AND spkm.NgayKetThucKM " +
                        "WHERE sp.MaLoai = 2"; // Modify as needed

                Log.d("DatabaseQuery", "Executing query: " + query);
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maSP = rs.getInt("maSP");
                    String tenSP = rs.getString("tenSP");
                    double gia = rs.getDouble("gia");
                    String thongTin = rs.getString("thongTin");
                    String anh = rs.getString("anh");
                    int soLuong = rs.getInt("soLuong");
                    String anhNho = rs.getString("anhNho");
                    int maLoai = rs.getInt("maLoai");
                    int maThuongHieu = rs.getInt("maThuongHieu");
                    double phanTramKhuyenMai = rs.getDouble("phanTramKhuyenMai");

                    // Store discount percentage in session
                    SessionManager sessionManager = new SessionManager(getContext()); // Replace with actual context
                    sessionManager.setDiscountPercentage(maSP, phanTramKhuyenMai);

                    // Create SanPham object
                    SanPham sanPham = new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu);
                    list.add(sanPham);
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error fetching products: " + e.getMessage());
            }
        } else {
            Log.e("Database Connection", "Connection is null.");
        }
        return list;
    }
    public List<SanPham> getSanPhamlamdepList() {
        List<SanPham> list = new ArrayList<>();
        Connection connection = getCon(); // Ensure this is correct
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT sp.MaSP AS maSP, " +
                        "       sp.TenSP AS tenSP, " +
                        "       sp.Gia AS gia, " +
                        "       sp.Thong_tin AS thongTin, " +
                        "       sp.Anh AS anh, " +
                        "       sp.SoLuong AS soLuong, " +
                        "       sp.AnhNho AS anhNho, " +
                        "       sp.MaLoai AS maLoai, " +
                        "       sp.MaThuongHieu AS maThuongHieu, " +
                        "       COALESCE(spkm.PhanTramKhuyenMai, 0) AS phanTramKhuyenMai " +
                        "FROM SanPham sp " +
                        "LEFT JOIN SanPhamKhuyenMai spkm ON sp.MaSP = spkm.MaSP AND " +
                        "                                    GETDATE() BETWEEN spkm.NgayBatDauKM AND spkm.NgayKetThucKM " +
                        "WHERE sp.MaLoai = 4"; // Modify as needed

                Log.d("DatabaseQuery", "Executing query: " + query);
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maSP = rs.getInt("maSP");
                    String tenSP = rs.getString("tenSP");
                    double gia = rs.getDouble("gia");
                    String thongTin = rs.getString("thongTin");
                    String anh = rs.getString("anh");
                    int soLuong = rs.getInt("soLuong");
                    String anhNho = rs.getString("anhNho");
                    int maLoai = rs.getInt("maLoai");
                    int maThuongHieu = rs.getInt("maThuongHieu");
                    double phanTramKhuyenMai = rs.getDouble("phanTramKhuyenMai");

                    // Store discount percentage in session
                    SessionManager sessionManager = new SessionManager(getContext()); // Replace with actual context
                    sessionManager.setDiscountPercentage(maSP, phanTramKhuyenMai);

                    // Create SanPham object
                    SanPham sanPham = new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu);
                    list.add(sanPham);
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error fetching products: " + e.getMessage());
            }
        } else {
            Log.e("Database Connection", "Connection is null.");
        }
        return list;
    }
    public List<SanPham> getSanPhamthoitrangList() {
        List<SanPham> list = new ArrayList<>();
        Connection connection = getCon(); // Ensure this is correct
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT sp.MaSP AS maSP, " +
                        "       sp.TenSP AS tenSP, " +
                        "       sp.Gia AS gia, " +
                        "       sp.Thong_tin AS thongTin, " +
                        "       sp.Anh AS anh, " +
                        "       sp.SoLuong AS soLuong, " +
                        "       sp.AnhNho AS anhNho, " +
                        "       sp.MaLoai AS maLoai, " +
                        "       sp.MaThuongHieu AS maThuongHieu, " +
                        "       COALESCE(spkm.PhanTramKhuyenMai, 0) AS phanTramKhuyenMai " +
                        "FROM SanPham sp " +
                        "LEFT JOIN SanPhamKhuyenMai spkm ON sp.MaSP = spkm.MaSP AND " +
                        "                                    GETDATE() BETWEEN spkm.NgayBatDauKM AND spkm.NgayKetThucKM " +
                        "WHERE sp.MaLoai = 5"; // Modify as needed

                Log.d("DatabaseQuery", "Executing query: " + query);
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maSP = rs.getInt("maSP");
                    String tenSP = rs.getString("tenSP");
                    double gia = rs.getDouble("gia");
                    String thongTin = rs.getString("thongTin");
                    String anh = rs.getString("anh");
                    int soLuong = rs.getInt("soLuong");
                    String anhNho = rs.getString("anhNho");
                    int maLoai = rs.getInt("maLoai");
                    int maThuongHieu = rs.getInt("maThuongHieu");
                    double phanTramKhuyenMai = rs.getDouble("phanTramKhuyenMai");

                    // Store discount percentage in session
                    SessionManager sessionManager = new SessionManager(getContext()); // Replace with actual context
                    sessionManager.setDiscountPercentage(maSP, phanTramKhuyenMai);

                    // Create SanPham object
                    SanPham sanPham = new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu);
                    list.add(sanPham);
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error fetching products: " + e.getMessage());
            }
        } else {
            Log.e("Database Connection", "Connection is null.");
        }
        return list;
    }
    public List<SanPham> getSanPhamdulichList() {
        List<SanPham> list = new ArrayList<>();
        Connection connection = getCon(); // Ensure this is correct
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT sp.MaSP AS maSP, " +
                        "       sp.TenSP AS tenSP, " +
                        "       sp.Gia AS gia, " +
                        "       sp.Thong_tin AS thongTin, " +
                        "       sp.Anh AS anh, " +
                        "       sp.SoLuong AS soLuong, " +
                        "       sp.AnhNho AS anhNho, " +
                        "       sp.MaLoai AS maLoai, " +
                        "       sp.MaThuongHieu AS maThuongHieu, " +
                        "       COALESCE(spkm.PhanTramKhuyenMai, 0) AS phanTramKhuyenMai " +
                        "FROM SanPham sp " +
                        "LEFT JOIN SanPhamKhuyenMai spkm ON sp.MaSP = spkm.MaSP AND " +
                        "                                    GETDATE() BETWEEN spkm.NgayBatDauKM AND spkm.NgayKetThucKM " +
                        "WHERE sp.MaLoai = 6"; // Modify as needed

                Log.d("DatabaseQuery", "Executing query: " + query);
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maSP = rs.getInt("maSP");
                    String tenSP = rs.getString("tenSP");
                    double gia = rs.getDouble("gia");
                    String thongTin = rs.getString("thongTin");
                    String anh = rs.getString("anh");
                    int soLuong = rs.getInt("soLuong");
                    String anhNho = rs.getString("anhNho");
                    int maLoai = rs.getInt("maLoai");
                    int maThuongHieu = rs.getInt("maThuongHieu");
                    double phanTramKhuyenMai = rs.getDouble("phanTramKhuyenMai");

                    // Store discount percentage in session
                    SessionManager sessionManager = new SessionManager(getContext()); // Replace with actual context
                    sessionManager.setDiscountPercentage(maSP, phanTramKhuyenMai);

                    // Create SanPham object
                    SanPham sanPham = new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu);
                    list.add(sanPham);
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error fetching products: " + e.getMessage());
            }
        } else {
            Log.e("Database Connection", "Connection is null.");
        }
        return list;
    }
    public List<SanPhamKhuyenMai> getAllSanPhamKhuyenMai() {
        List<SanPhamKhuyenMai> list = new ArrayList<>();
        Connection connection = getCon(); // Use the correct reference
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM SanPhamKhuyenMai"; // Ensure this query matches your database schema
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maSPKM = rs.getInt("MaSPKM");
                    String tenSPKM = rs.getString("TenSPKM");
                    String ngayBatDauKM = rs.getString("NgayBatDauKM");
                    String ngayKetThucKM = rs.getString("NgayKetThucKM");
                    double phanTramKhuyenMai = rs.getDouble("PhanTramKhuyenMai");
                    int maKhuyenMai = rs.getInt("MaKhuyenMai");
                    int maSP = rs.getInt("MaSP");
                    String hinhAnh = rs.getString("HinhAnh"); // Added field
                    String anhNho = rs.getString("AnhNho");   // Added field
                    double giaGoc = rs.getDouble("GiaGoc");    // Added field

                    list.add(new SanPhamKhuyenMai(maSPKM, tenSPKM, ngayBatDauKM, ngayKetThucKM,
                            phanTramKhuyenMai, maKhuyenMai, maSP, hinhAnh, anhNho, giaGoc));
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error fetching promotion products: " + e.getMessage());
            }
        }
        return list;
    }
    public List<ThuongHieu> getAllthuonghieu() {
        List<ThuongHieu> list = new ArrayList<>();
        Connection connection = getCon(); // Ensure getCon() is correctly implemented
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM thuonghieu"; // Ensure this matches your database schema
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int mathuonghieu = rs.getInt("Mathuonghieu");
                    String tenthuonghieu = rs.getString("Tenthuonghieu");
                    String hinhthuonghieu = rs.getString("Hinhthuonghieu");
                    list.add(new ThuongHieu(mathuonghieu, tenthuonghieu, hinhthuonghieu));
                }
            } catch (SQLException e) {
                Log.e("Database Error", "Error fetching data: " + e.getMessage());
            }
        } else {
            Log.e("Database Error", "Connection is null");
        }
        return list;
    }
//
//    public List<SanPham> getAllBanChay() {
//        List<SanPham> list = new ArrayList<>();
//        Connection connection = getCon(); // Ensure this is correct
//        if (connection != null) {
//            try {
//                Statement stmt = connection.createStatement();
//                String query = "SELECT \n" +
//                        "    sp.MaSP AS maSP,\n" +
//                        "    sp.TenSP AS tenSP,\n" +
//                        "    sp.Gia AS gia,\n" +
//                        "    sp.Thong_tin AS thongTin,\n" +
//                        "    sp.Anh AS anh,\n" +
//                        "    sp.SoLuong AS soLuong,\n" +
//                        "    sp.AnhNho AS anhNho,\n" +
//                        "    sp.MaLoai AS maLoai,\n" +
//                        "    sp.MaThuongHieu AS maThuongHieu,\n" +
//                        "    COALESCE(spkm.MaSPKM, 0) AS maSPKM,\n" +
//                        "    COALESCE(spkm.TenSPKM, '') AS tenSPKM,\n" +
//                        "    COALESCE(spkm.NgayBatDauKM, '') AS ngayBatDauKM,\n" +
//                        "    COALESCE(spkm.NgayKetThucKM, '') AS ngayKetThucKM,\n" +
//                        "    COALESCE(spkm.PhanTramKhuyenMai, 0) AS phanTramKhuyenMai,\n" +
//                        "    COALESCE(spkm.MaKhuyenMai, 0) AS maKhuyenMai,\n" +
//                        "    COALESCE(spkm.HinhAnh, '') AS hinhAnh,\n" +
//                        "    COALESCE(spkm.AnhNho, '') AS anhNho,\n" +
//                        "    COALESCE(spkm.GiaGoc, 0) AS giaGoc,\n" +
//                        "    SUM(ctdh.Soluong) AS TongSoLuong\n" +
//                        "FROM \n" +
//                        "    dbo.ChiTietDonDatHang ctdh\n" +
//                        "JOIN \n" +
//                        "    dbo.SanPham sp ON ctdh.MaSP = sp.MaSP\n" +
//                        "LEFT JOIN \n" +
//                        "    dbo.SanPhamKhuyenMai spkm ON sp.MaSP = spkm.MaSP AND \n" +
//                        "                                GETDATE() BETWEEN spkm.NgayBatDauKM AND spkm.NgayKetThucKM\n" +
//                        "GROUP BY \n" +
//                        "    sp.MaSP, sp.TenSP, sp.Gia, sp.Thong_tin, sp.Anh, sp.SoLuong, sp.AnhNho, sp.MaLoai, sp.MaThuongHieu,\n" +
//                        "    spkm.MaSPKM, spkm.TenSPKM, spkm.NgayBatDauKM, spkm.NgayKetThucKM, spkm.PhanTramKhuyenMai, spkm.MaKhuyenMai,\n" +
//                        "    spkm.HinhAnh, spkm.AnhNho, spkm.GiaGoc\n" +
//                        "ORDER BY    \n" +
//                        "    TongSoLuong DESC\n" +
//                        "OFFSET 0 ROWS FETCH NEXT 4 ROWS ONLY;\n";
//                Log.d("DatabaseQuery", "Executing query: " + query);
//                ResultSet rs = stmt.executeQuery(query);
//                while (rs.next()) {
//                    int maSP = rs.getInt("maSP");
//                    String tenSP = rs.getString("tenSP");
//                    double gia = rs.getDouble("gia");
//                    String thongTin = rs.getString("thongTin");
//                    String anh = rs.getString("anh");
//                    int soLuong = rs.getInt("soLuong");
//                    String anhNho = rs.getString("anhNho");
//                    int maLoai = rs.getInt("maLoai");
//                    int maThuongHieu = rs.getInt("maThuongHieu");
//
//                    // Create SanPham object
//                    SanPham sanPham = new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu);
//                    list.add(sanPham);
//
//                    // Optionally, if you need to use SanPhamKhuyenMai details, create and add that as well
//                    int maSPKM = rs.getInt("maSPKM");
//                    String tenSPKM = rs.getString("tenSPKM");
//                    String ngayBatDauKM = rs.getString("ngayBatDauKM");
//                    String ngayKetThucKM = rs.getString("ngayKetThucKM");
//                    double phanTramKhuyenMai = rs.getDouble("phanTramKhuyenMai");
//                    int maKhuyenMai = rs.getInt("maKhuyenMai");
//                    String hinhAnh = rs.getString("hinhAnh");
//                    String anhNhoKM = rs.getString("anhNho");
//                    double giaGoc = rs.getDouble("giaGoc");
//
//                    SanPhamKhuyenMai khuyenMai = new SanPhamKhuyenMai(maSPKM, tenSPKM, ngayBatDauKM, ngayKetThucKM,
//                            phanTramKhuyenMai, maKhuyenMai, maSP, hinhAnh,
//                            anhNhoKM, giaGoc);
//                }
//            } catch (SQLException e) {
//                Log.e("DatabaseError", "Error fetching products: " + e.getMessage());
//            }
//        } else {
//            Log.e("DatabaseConnection", "Connection is null.");
//        }
//        Log.d("DataFetch", "List size: " + list.size());
//        return list;
//    }


    // Existing methods...

    // Add a method to get the context if needed
    public Context getContext() {
        return context;
    }
    public List<SanPham> getAllBanChay() {
        List<SanPham> list = new ArrayList<>();
        Connection connection = getCon(); // Ensure this is correct
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "SELECT \n" +
                        "    sp.MaSP AS maSP,\n" +
                        "    sp.TenSP AS tenSP,\n" +
                        "    sp.Gia AS gia,\n" +
                        "    sp.Thong_tin AS thongTin,\n" +
                        "    sp.Anh AS anh,\n" +
                        "    sp.SoLuong AS soLuong,\n" +
                        "    sp.AnhNho AS anhNho,\n" +
                        "    sp.MaLoai AS maLoai,\n" +
                        "    sp.MaThuongHieu AS maThuongHieu,\n" +
                        "    COALESCE(spkm.PhanTramKhuyenMai, 0) AS phanTramKhuyenMai,\n" +
                        "    SUM(ctdh.Soluong) AS TongSoLuong\n" +
                        "FROM \n" +
                        "    dbo.ChiTietDonDatHang ctdh\n" +
                        "JOIN \n" +
                        "    dbo.SanPham sp ON ctdh.MaSP = sp.MaSP\n" +
                        "LEFT JOIN \n" +
                        "    dbo.SanPhamKhuyenMai spkm ON sp.MaSP = spkm.MaSP AND \n" +
                        "                                GETDATE() BETWEEN spkm.NgayBatDauKM AND spkm.NgayKetThucKM\n" +
                        "GROUP BY \n" +
                        "    sp.MaSP, sp.TenSP, sp.Gia, sp.Thong_tin, sp.Anh, sp.SoLuong, sp.AnhNho, sp.MaLoai, sp.MaThuongHieu, spkm.PhanTramKhuyenMai\n" +
                        "ORDER BY    \n" +
                        "    TongSoLuong DESC\n" +
                        "OFFSET 0 ROWS FETCH NEXT 5 ROWS ONLY;\n";
                Log.d("DatabaseQuery", "Executing query: " + query);
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int maSP = rs.getInt("maSP");
                    String tenSP = rs.getString("tenSP");
                    double gia = rs.getDouble("gia");
                    String thongTin = rs.getString("thongTin");
                    String anh = rs.getString("anh");
                    int soLuong = rs.getInt("soLuong");
                    String anhNho = rs.getString("anhNho");
                    int maLoai = rs.getInt("maLoai");
                    int maThuongHieu = rs.getInt("maThuongHieu");
                    double phanTramKhuyenMai = rs.getDouble("phanTramKhuyenMai");

                    // Store discount percentage in session
                    SessionManager sessionManager = new SessionManager(getContext()); // Replace with actual context
                    sessionManager.setDiscountPercentage(maSP, phanTramKhuyenMai);

                    // Create SanPham object
                    SanPham sanPham = new SanPham(maSP, tenSP, gia, thongTin, anh, soLuong, anhNho, maLoai, maThuongHieu);
                    list.add(sanPham);
                }
            } catch (SQLException e) {
                Log.e("DatabaseError", "Error fetching products: " + e.getMessage());
            }
        } else {
            Log.e("DatabaseConnection", "Connection is null.");
        }
        Log.d("DataFetch", "List size: " + list.size());
        return list;
    }


}
