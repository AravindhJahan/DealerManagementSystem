package com.falconnect.dealermanagementsystem;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

public class AuctionActivity extends Activity {

    private boolean mVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auction);

        mVisible = true;
    }


}
