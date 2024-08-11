package com.example.ung_dung_dat_hang.Model.ObjeactClass;

public class ThuongHieu {
    private int Mathuonghieu;           // Adjust the data type if necessary
    private String Tenthuonghieu;
    private String Hinhthuonghieu;

    // Constructor
    public ThuongHieu(int Mathuonghieu, String Tenthuonghieu, String Hinhthuonghieu) {
        this.Mathuonghieu = Mathuonghieu;
        this.Tenthuonghieu = Tenthuonghieu;
        this.Hinhthuonghieu = Hinhthuonghieu;
    }

    // Getters and Setters
    public int getMathuonghieu() {
        return Mathuonghieu;
    }

    public void setMathuonghieu(int Mathuonghieu) {
        this.Mathuonghieu = Mathuonghieu;
    }

    public String getTenthuonghieu() {
        return Tenthuonghieu;
    }

    public void setTenthuonghieu(String Tenthuonghieu) {
        this.Tenthuonghieu = Tenthuonghieu;
    }

    public String getHinhthuonghieu() {
        return Hinhthuonghieu;
    }

    public void setHinhthuonghieu(String Hinhthuonghieu) {
        this.Hinhthuonghieu = Hinhthuonghieu;
    }

    @Override
    public String toString() {
        return "ThuongHieu{" +
                "Mathuonghieu=" + Mathuonghieu +
                ", Tenthuonghieu='" + Tenthuonghieu + '\'' +
                ", Hinhthuonghieu='" + Hinhthuonghieu + '\'' +
                '}';
    }
}
