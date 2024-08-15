package com.example.ung_dung_dat_hang.Model.ObjeactClass;

public class BinhLuan {
    private int maSP;              // Mã sản phẩm
    private int maTaiKhoan;       // Mã tài khoản người bình luận
    private int maBinhLuan;       // Mã bình luận
    private String noiDung;       // Nội dung bình luận
    private String ngayBinhLuan;  // Ngày bình luận

    // Constructor
    public BinhLuan(int maSP, int maTaiKhoan, int maBinhLuan, String noiDung, String ngayBinhLuan) {
        this.maSP = maSP;
        this.maTaiKhoan = maTaiKhoan;
        this.maBinhLuan = maBinhLuan;
        this.noiDung = noiDung;
        this.ngayBinhLuan = ngayBinhLuan;
    }

    // Getters and Setters
    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(int maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public int getMaBinhLuan() {
        return maBinhLuan;
    }

    public void setMaBinhLuan(int maBinhLuan) {
        this.maBinhLuan = maBinhLuan;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayBinhLuan() {
        return ngayBinhLuan;
    }

    public void setNgayBinhLuan(String ngayBinhLuan) {
        this.ngayBinhLuan = ngayBinhLuan;
    }

    @Override
    public String toString() {
        return "BinhLuan{" +
                "maSP=" + maSP +
                ", maTaiKhoan=" + maTaiKhoan +
                ", maBinhLuan=" + maBinhLuan +
                ", noiDung='" + noiDung + '\'' +
                ", ngayBinhLuan='" + ngayBinhLuan + '\'' +
                '}';
    }
}
