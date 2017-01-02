package com.falconnect.dealermanagementsystem.SharedPreference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;

import com.falconnect.dealermanagementsystem.LoginActivity;

import java.util.HashMap;

public class SiteCitySavedPreferences {
    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    HashMap<String, String> datass;
    private static final String PREF_NAME = "AndroidHivePref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_SITES = "sites";
    public static final String KEY_CITY = "city";

    public SiteCitySavedPreferences(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createGetValueSession(String sites, String city) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_SITES, sites);
        editor.putString(KEY_CITY, city);
        editor.commit();
    }

    public void save_Data(String sites) {

        editor.putString(KEY_SITES, sites);
        editor.commit();
    }

    public void save_Datas(String city) {

        editor.putString(KEY_CITY, city);
        editor.commit();
    }


    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public HashMap<String, String> getDetails() {

        datass = new HashMap<String, String>();
        datass.put(KEY_SITES, pref.getString(KEY_SITES, null));
        datass.put(KEY_CITY, pref.getString(KEY_CITY, null));

        Log.e("sites", KEY_SITES);
        Log.e("city", KEY_CITY);

        return datass;
    }

    public boolean isLoggedIn() {

        return pref.getBoolean(IS_LOGIN, false);

    }

}
