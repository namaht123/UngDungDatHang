package com.example.ung_dung_dat_hang.ConnnectInternet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection con;
    private String uname, pass, ip, port, database;
    private Context context;

    public DatabaseConnection(Context context) {
        this.context = context;
        new DatabaseConnectionTask().execute();
    }

    private class DatabaseConnectionTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            ip = "10.0.2.2"; // IP address for localhost in Android Emulator
            database = "data_Lazada";
            uname = "Sang";
            pass = "261102";
            port = "1433"; // Default port for SQL Server
            String ConnectionURL;
            try {
                // Load the JDBC driver
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";databasename=" + database + ";user=" + uname + ";password=" + pass;
                con = DriverManager.getConnection(ConnectionURL);
                return con != null && !con.isClosed();
            } catch (ClassNotFoundException e) {
                Log.e("Database Error", "JDBC Driver not found: " + e.getMessage());
            } catch (SQLException e) {
                Log.e("Database Error", "SQL error: " + e.getMessage());
            } catch (Exception e) {
                Log.e("Database Error", "Unexpected error: " + e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean isConnected) {
            if (isConnected) {
                Toast.makeText(context, "Kết nối cơ sở dữ liệu thành công!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Không thể kết nối đến cơ sở dữ liệu!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public Connection getCon() {
        return con;
    }

    public boolean checkConnection() {
        boolean isConnected = false;
        try {
            if (con != null && !con.isClosed()) {
                isConnected = true;
            }
        } catch (SQLException e) {
            Log.e("Connection Error", "Connection check error: " + e.getMessage());
        }
        return isConnected;
    }
}
