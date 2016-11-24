package com.falconnect.dealermanagementsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private boolean mVisible;

    Button enter, signupintro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_main);

        mVisible = true;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        if (mVisible) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mVisible = false;

        } else {

        }

        enter = (Button) findViewById(R.id.enter_btn);
        signupintro = (Button) findViewById(R.id.sigin_up_intro);

        //Enter button click event
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickevent();

            }
        });

        signupintro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

                MainActivity.this.finish();
            }
        });


    }

    public void clickevent() {
        //Go to Login screen
        Intent intent = new Intent(MainActivity.this, DashBoard.class);
        startActivity(intent);

        //finish this activity
        MainActivity.this.finish();

    }
}
