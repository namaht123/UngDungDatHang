package com.example.ung_dung_dat_hang.View.GioHang;

public class CartItem {
    private String tenSP;
    private double giaSP;
    private String anhSP;
    private String anhNhoSP;
    private int soLuong; // Add quantity field

    public CartItem(String tenSP, double giaSP, String anhSP, String anhNhoSP, int soLuong) {
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.anhSP = anhSP;
        this.anhNhoSP = anhNhoSP;
        this.soLuong = soLuong;
    }

    public String getTenSP() {
        return tenSP;
    }

    public double getGiaSP() {
        return giaSP;
    }

    public String getAnhSP() {
        return anhSP;
    }

    public String getAnhNhoSP() {
        return anhNhoSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
