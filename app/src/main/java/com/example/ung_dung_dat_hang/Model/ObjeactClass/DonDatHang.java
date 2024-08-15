package com.example.ung_dung_dat_hang.Model.ObjeactClass;

public class DonDatHang {
    private int madondathang;
    private double tongtien;

    // Constructors
    public DonDatHang() {}

    public DonDatHang(int madondathang, double tongtien) {
        this.madondathang = madondathang;
        this.tongtien = tongtien;
    }

    // Getters and Setters
    public int getMadondathang() {
        return madondathang;
    }

    public void setMadondathang(int madondathang) {
        this.madondathang = madondathang;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }
}
