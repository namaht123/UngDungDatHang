package com.example.ung_dung_dat_hang.ConnnectInternet;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadJSON extends AsyncTask<String, Void, String> {

    private String duongdan;
    private List<HashMap<String, String>> attrs;
    private boolean method = true;
    private DownloadJSONCallback callback;

    public DownloadJSON(String duongdan, List<HashMap<String, String>> attrs, DownloadJSONCallback callback) {
        this.duongdan = duongdan;
        this.attrs = attrs;
        this.callback = callback;
        method = false;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = "";
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(duongdan);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000); // 10 giây
            httpURLConnection.setReadTimeout(10000); // 10 giây

            if (method) {
                httpURLConnection.setRequestMethod("GET");
                data = methodGet(httpURLConnection);
            } else {
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                data = methodPost(httpURLConnection);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Lỗi URL: " + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "Lỗi kết nối: " + e.getMessage();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.startsWith("Lỗi")) {
            callback.onError(result);
        } else {
            callback.onSuccess(result);
        }
    }

    private String methodGet(HttpURLConnection httpURLConnection) {
        String data = "";
        try (InputStream inputStream = httpURLConnection.getInputStream();
             InputStreamReader reader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            httpURLConnection.connect();
            StringBuilder dulieu = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                dulieu.append(line);
            }
            data = dulieu.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return "Lỗi đọc dữ liệu: " + e.getMessage();
        }

        return data;
    }

    private String methodPost(HttpURLConnection httpURLConnection) {
        String data = "";
        try {
            Uri.Builder builder = new Uri.Builder();

            for (HashMap<String, String> map : attrs) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    builder.appendQueryParameter(entry.getKey(), entry.getValue());
                }
            }
            String query = builder.build().getEncodedQuery();

            try (OutputStream outputStream = httpURLConnection.getOutputStream();
                 OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
                 BufferedWriter writer = new BufferedWriter(streamWriter)) {

                writer.write(query);
                writer.flush();
            }

            data = methodGet(httpURLConnection);

        } catch (ProtocolException e) {
            e.printStackTrace();
            return "Lỗi giao thức: " + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "Lỗi ghi dữ liệu: " + e.getMessage();
        }

        return data;
    }
}

