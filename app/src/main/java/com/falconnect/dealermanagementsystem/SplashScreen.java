package com.falconnect.dealermanagementsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;

import java.util.HashMap;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();

        if (user.get("user_id") == null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    //open activity
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    // activity finish
                    SplashScreen.this.finish();
                }
            }, SPLASH_TIME_OUT);
        }
        else {
            //open activity
            Intent i = new Intent(SplashScreen.this, DashBoard.class);
            startActivity(i);
            SplashScreen.this.finish();
        }
    }
}
