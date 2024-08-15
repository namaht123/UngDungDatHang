package com.example.ung_dung_dat_hang.Model.ObjeactClass;

import java.math.BigDecimal;

public class ChiTietDonDatHang {
    private int maSP;
    private String hinh;
    private String tenSP;
    private int soluong;
    private BigDecimal gia; // Add this field
    private String anh; // Add this field

    // Constructors
    public ChiTietDonDatHang() {}

    public ChiTietDonDatHang(int maSP, String hinh, String tenSP, int soluong, BigDecimal gia, String anh) {
        this.maSP = maSP;
        this.hinh = hinh;
        this.tenSP = tenSP;
        this.soluong = soluong;
        this.gia = gia;
        this.anh = anh;
    }

    // Getters and Setters
    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public BigDecimal getGia() {
        return gia;
    }

    public void setGia(BigDecimal gia) {
        this.gia = gia;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}

