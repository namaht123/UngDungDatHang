package com.example.ung_dung_dat_hang.Model.ObjeactClass;

public class SanPhamKhuyenMai {
    private int maSPKM;
    private String tenSPKM;
    private String ngayBatDauKM;
    private String ngayKetThucKM;
    private double phanTramKhuyenMai;
    private int maKhuyenMai;
    private int maSP;

    // Constructor
    public SanPhamKhuyenMai(int maSPKM, String tenSPKM, String ngayBatDauKM, String ngayKetThucKM,
                            double phanTramKhuyenMai, int maKhuyenMai, int maSP) {
        this.maSPKM = maSPKM;
        this.tenSPKM = tenSPKM;
        this.ngayBatDauKM = ngayBatDauKM;
        this.ngayKetThucKM = ngayKetThucKM;
        this.phanTramKhuyenMai = phanTramKhuyenMai;
        this.maKhuyenMai = maKhuyenMai;
        this.maSP = maSP;
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
                '}';
    }
}
