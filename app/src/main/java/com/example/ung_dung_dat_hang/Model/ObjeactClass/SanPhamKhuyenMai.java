package com.example.ung_dung_dat_hang.Model.ObjeactClass;

public class SanPhamKhuyenMai extends SanPham {
    private int maSPKM;
    private String tenSPKM;
    private String ngayBatDauKM;
    private String ngayKetThucKM;
    private double phanTramKhuyenMai;
    private int maKhuyenMai;
    private int maSP;
    private String hinhAnh;  // URL or path to the large image
    private String anhNho;   // URL or path to the thumbnail image
    private double giaGoc;

    public SanPhamKhuyenMai() {
        // No-argument constructor
    }
    // Constructor
    public SanPhamKhuyenMai(int maSPKM, String tenSPKM, String ngayBatDauKM, String ngayKetThucKM,
                            double phanTramKhuyenMai, int maKhuyenMai, int maSP, String hinhAnh,
                            String anhNho, double giaGoc) {
        this.maSPKM = maSPKM;
        this.tenSPKM = tenSPKM;
        this.ngayBatDauKM = ngayBatDauKM;
        this.ngayKetThucKM = ngayKetThucKM;
        this.phanTramKhuyenMai = phanTramKhuyenMai;
        this.maKhuyenMai = maKhuyenMai;
        this.maSP = maSP;
        this.hinhAnh = hinhAnh;
        this.anhNho = anhNho;
        this.giaGoc = giaGoc;
    }

    // Getters and Setters
    public int getMaSPKM() {
        return maSPKM;
    }

    public void setMaSPKM(int maSPKM) {
        this.maSPKM = maSPKM;
    }

    public String getTenSPKM() {
        return tenSPKM;
    }

    public void setTenSPKM(String tenSPKM) {
        this.tenSPKM = tenSPKM;
    }

    public String getNgayBatDauKM() {
        return ngayBatDauKM;
    }

    public void setNgayBatDauKM(String ngayBatDauKM) {
        this.ngayBatDauKM = ngayBatDauKM;
    }

    public String getNgayKetThucKM() {
        return ngayKetThucKM;
    }

    public void setNgayKetThucKM(String ngayKetThucKM) {
        this.ngayKetThucKM = ngayKetThucKM;
    }

    public double getPhanTramKhuyenMai() {
        return phanTramKhuyenMai;
    }

    public void setPhanTramKhuyenMai(double phanTramKhuyenMai) {
        this.phanTramKhuyenMai = phanTramKhuyenMai;
    }

    public int getMaKhuyenMai() {
        return maKhuyenMai;
    }

    public void setMaKhuyenMai(int maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getAnhNho() {
        return anhNho;
    }

    public void setAnhNho(String anhNho) {
        this.anhNho = anhNho;
    }

    public double getGiaGoc() {
        return giaGoc;
    }

    public void setGiaGoc(double giaGoc) {
        this.giaGoc = giaGoc;
    }

    @Override
    public String toString() {
        return "SanPhamKhuyenMai{" +
                "maSPKM=" + maSPKM +
                ", tenSPKM='" + tenSPKM + '\'' +
                ", ngayBatDauKM='" + ngayBatDauKM + '\'' +
                ", ngayKetThucKM='" + ngayKetThucKM + '\'' +
                ", phanTramKhuyenMai=" + phanTramKhuyenMai +
                ", maKhuyenMai=" + maKhuyenMai +
                ", maSP=" + maSP +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", anhNho='" + anhNho + '\'' +
                ", giaGoc=" + giaGoc +
                '}';
    }

}
