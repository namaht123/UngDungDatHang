package com.example.ung_dung_dat_hang.Model.ObjeactClass;

public class SanPham {
    private int maSP;
    private String tenSP;
    private double gia;
    private String thongTin;
    private String anh;
    private int soLuong;
    private String anhNho;
    private int maLoai;
    private int maThuongHieu;

    public SanPham(int maSP, String tenSP, double gia, String thongTin, String anh, int soLuong, String anhNho, int maLoai, int maThuongHieu) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.gia = gia;
        this.thongTin = thongTin;
        this.anh = anh;
        this.soLuong = soLuong;
        this.anhNho = anhNho;
        this.maLoai = maLoai;
        this.maThuongHieu = maThuongHieu;
    }

    // Getters and setters
    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getThongTin() {
        return thongTin;
    }

    public void setThongTin(String thongTin) {
        this.thongTin = thongTin;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getAnhNho() {
        return anhNho;
    }

    public void setAnhNho(String anhNho) {
        this.anhNho = anhNho;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getMaThuongHieu() {
        return maThuongHieu;
    }

    public void setMaThuongHieu(int maThuongHieu) {
        this.maThuongHieu = maThuongHieu;
    }
}
