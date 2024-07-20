package com.example.ung_dung_dat_hang.Presenter.Trangchu.XuLyMenu;

import com.example.ung_dung_dat_hang.ConnnectInternet.DownloadJSON;
import com.example.ung_dung_dat_hang.ConnnectInternet.DownloadJSONCallback;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.LoaiSanPham;
import com.example.ung_dung_dat_hang.Model.TrangChu.XulyMenu.XuLyJSONMenu;
import com.example.ung_dung_dat_hang.View.TrangChu.ViewXuLyMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PrensenterLogicXuLyMenu implements IPresenterXuLYMenu {
    private ViewXuLyMenu viewXuLyMenu;

    public PrensenterLogicXuLyMenu(ViewXuLyMenu viewXuLyMenu) {
        this.viewXuLyMenu = viewXuLyMenu;
    }

    @Override
    public void LayDanhSachMenu() {
        String duongdan = "http://192.168.1.24/weblazada/loaisanpham.php";
        List<HashMap<String, String>> attrs = new ArrayList<>();
        HashMap<String, String> hsMaLoaiCha = new HashMap<>();
        hsMaLoaiCha.put("maloaicha", "0");
        attrs.add(hsMaLoaiCha);

        DownloadJSON downloadJSON = new DownloadJSON(duongdan, attrs, new DownloadJSONCallback() {
            @Override
            public void onSuccess(String data) {
                XuLyJSONMenu xuLyJSONMenu = new XuLyJSONMenu();
                List<LoaiSanPham> loaiSanPhamList = xuLyJSONMenu.ParserJSONMenu(data);
                viewXuLyMenu.HienThiDanhSachMenu(loaiSanPhamList);
            }

            @Override
            public void onError(String error) {
                viewXuLyMenu.HienThiThongBaoLoi(error);
            }
        });
        downloadJSON.execute();
    }
}

