package com.example.ung_dung_dat_hang.Model.TrangChu.XulyMenu;

import com.example.ung_dung_dat_hang.ConnnectInternet.DownloadJSON;
import com.example.ung_dung_dat_hang.ConnnectInternet.DownloadJSONCallback;
import com.example.ung_dung_dat_hang.Model.ObjeactClass.LoaiSanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XuLyJSONMenu {

    public List<LoaiSanPham> ParserJSONMenu(String dulieuJSON) {
        List<LoaiSanPham> loaiSanPhamsList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(dulieuJSON);
            JSONArray loaisanpham = jsonObject.getJSONArray("LOAISANPHAM");
            int count = loaisanpham.length();
            for (int i = 0; i < count; i++) {
                JSONObject valus = loaisanpham.getJSONObject(i);

                LoaiSanPham dataloaiSanPham = new LoaiSanPham();
                dataloaiSanPham.setMALOAISP(Integer.parseInt(valus.getString("MALOAISP")));
                dataloaiSanPham.setMALOAICHA(Integer.parseInt(valus.getString("MALOAI_CHA")));
                dataloaiSanPham.setTENLOAISP(valus.getString("TENLOAISP"));

                loaiSanPhamsList.add(dataloaiSanPham);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return loaiSanPhamsList;
    }

    public void LayLoaiSanPhamThaoMaLoai(int maloaisp, DownloadJSONCallback callback) {
        List<HashMap<String, String>> attrs = new ArrayList<>();

        String duongdan = "http://192.168.1.24/weblazada/loaisanpham.php";
        HashMap<String, String> hsMaLoaiCha = new HashMap<>();
        hsMaLoaiCha.put("maloaicha", String.valueOf(maloaisp));

        attrs.add(hsMaLoaiCha);

        DownloadJSON downloadJSON = new DownloadJSON(duongdan, attrs, new DownloadJSONCallback() {
            @Override
            public void onSuccess(String data) {
                List<LoaiSanPham> loaiSanPhamList = ParserJSONMenu(data);
                callback.onSuccess(data);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
        downloadJSON.execute();
    }
}
