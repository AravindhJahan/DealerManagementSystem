package com.falconnect.dealermanagementsystem.SharedPreference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.falconnect.dealermanagementsystem.LoginActivity;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    HashMap<String, String> user;
    private static final String PREF_NAME = "AndroidHivePref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_DEALER_NAME = "dealer_name";
    public static final String KEY_ID = "user_id";
    public static final String KEY_IMAGE = "dealer_img";
    public static final String KEY_CITY = "dealer_address";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String name, String id, String image, String city) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_DEALER_NAME, name);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_IMAGE, image);
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

    public HashMap<String, String> getUserDetails() {

        user = new HashMap<String, String>();
        user.put(KEY_DEALER_NAME, pref.getString(KEY_DEALER_NAME, null));
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_IMAGE, pref.getString(KEY_IMAGE, null));
        user.put(KEY_CITY, pref.getString(KEY_CITY, null));

        Log.e("dealer_name", KEY_DEALER_NAME);
        Log.e("user_id", KEY_ID);
        Log.e("dealer_imag", KEY_IMAGE);
        Log.e("dealer_address", KEY_CITY);

        return user;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public boolean isLoggedIn() {

        return pref.getBoolean(IS_LOGIN, false);

    }

}
