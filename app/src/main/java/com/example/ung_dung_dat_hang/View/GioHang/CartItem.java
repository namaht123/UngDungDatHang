package com.example.ung_dung_dat_hang.View.GioHang;

import java.util.List;

public class CartItem {
    private int maSP;
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
    private String convertCartItemsToString(List<CartItem> cartItems) {
        StringBuilder sb = new StringBuilder();
        for (CartItem item : cartItems) {
            sb.append(item.getMaSP()).append(";")  // Product ID
                    .append(item.getGiaSP()).append(";")
                    .append(item.getAnhSP()).append(";")
                    .append(item.getAnhNhoSP()).append(";")
                    .append(item.getSoLuong()).append("|");
        }
        return sb.toString();
    }
    public int getMaSP() {
        return maSP;
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
