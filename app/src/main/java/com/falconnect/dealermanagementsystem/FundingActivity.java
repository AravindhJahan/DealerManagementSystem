package com.falconnect.dealermanagementsystem;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FundingActivity extends AppCompatActivity {

    private boolean mVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_funding);

        mVisible = true;

        if (mVisible) {
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mVisible = false;
        } else {

        }

    }


}
