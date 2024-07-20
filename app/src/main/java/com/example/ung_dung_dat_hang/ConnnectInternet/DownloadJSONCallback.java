package com.example.ung_dung_dat_hang.ConnnectInternet;

public interface DownloadJSONCallback {
    void onSuccess(String data);
    void onError(String error);
}
