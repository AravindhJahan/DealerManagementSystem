package com.falconnect.dealermanagementsystem;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.falconnect.dealermanagementsystem.Adapter.CustomListViewAdapter;
import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;

public class DashBoard extends AppCompatActivity {


    private boolean mVisible;
    Context context;

    ImageView nav;

    private SimpleSideDrawer mNav;


    public static int [] prgmImages={
            R.drawable.search_blue,
            R.drawable.savecar_blue,
            R.drawable.queries_blue,
            R.drawable.bids_blue,
            R.drawable.funding_blue};

    public static String [] prgmNameList = {
            "Search",
            "Saved Cars",
            "My Queries",
            "Bids Posted",
            "Funding"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dash_board);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mVisible = true;
        context = this;


        if (mVisible) {
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mVisible = false;

        } else {
        }

       /* lv =(GridView) findViewById(R.id.list);

        lv.setAdapter(new CustomListViewAdapter(DashBoard.this, prgmNameList, prgmImages));*/


        nav = (ImageView) findViewById(R.id.nav_icon_drawer);
        mNav = new SimpleSideDrawer(this);
        mNav.setLeftBehindContentView(R.layout.activity_behind_left_simple);

        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav.toggleLeftDrawer();
            }
        });



    }

}