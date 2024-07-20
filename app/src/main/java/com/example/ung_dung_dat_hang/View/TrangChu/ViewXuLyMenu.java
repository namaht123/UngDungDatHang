package com.example.ung_dung_dat_hang.View.TrangChu;

import com.example.ung_dung_dat_hang.Model.ObjeactClass.LoaiSanPham;

import java.util.List;

public interface ViewXuLyMenu {
    void HienThiDanhSachMenu(List<LoaiSanPham> loaiSanPhamList);
    void HienThiThongBaoLoi(String error);
}

