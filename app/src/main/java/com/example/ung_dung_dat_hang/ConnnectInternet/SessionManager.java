package com.example.ung_dung_dat_hang.ConnnectInternet;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "app_prefs";
    private static final String KEY_DISCOUNT = "discount_";

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Save discount percentage for a product
    public void setDiscountPercentage(int productId, double discountPercentage) {
        editor.putFloat(KEY_DISCOUNT + productId, (float) discountPercentage);
        editor.apply();
    }

    // Get discount percentage for a product
    public double getDiscountPercentage(int productId) {
        return sharedPreferences.getFloat(KEY_DISCOUNT + productId, 0);
    }
}

