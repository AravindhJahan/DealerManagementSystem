package com.falconnect.dealermanagementsystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.falconnect.dealermanagementsystem.Adapter.CustomList;
import com.falconnect.dealermanagementsystem.Adapter.SellFooterCustomAdapter;
import com.falconnect.dealermanagementsystem.FontAdapter.RoundImageTransform;
import com.falconnect.dealermanagementsystem.Model.SellFooterDataModel;
import com.falconnect.dealermanagementsystem.NavigationDrawer.BuyPageNavigation;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;
import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;
import java.util.HashMap;

public class MyPostingActivity extends AppCompatActivity {

    private boolean mVisible;

    ImageView myposting_mnav;
    private SimpleSideDrawer mNav_myposting;
    SessionManager session_myposting;
    ImageView imageView_myposting;
    TextView profile_name_myposting;
    TextView profile_address_myposting;
    String saved_name_myposting, saved_address_myposting;
    BuyPageNavigation myposting_buypagenavigation;

    private static RecyclerView sellmyposting_footer;
    private static ArrayList<SellFooterDataModel> sellfooterdata;
    private static RecyclerView.Adapter selladapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_posting);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mVisible = true;

        if (mVisible) {
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mVisible = false;
        } else {

        }

        sellmyposting_footer = (RecyclerView) findViewById(R.id.my_recycler_my_posting);
        sellmyposting_footer.setHasFixedSize(true);
        sellmyposting_footer.setLayoutManager(new LinearLayoutManager(MyPostingActivity.this, LinearLayoutManager.HORIZONTAL, false));
        sellfooterdata = new ArrayList<SellFooterDataModel>();
        for (int i = 0; i < MyFooterSellData.sellnameArray.length; i++) {

            sellfooterdata.add(new SellFooterDataModel(
                    MyFooterSellData.sellnameArray[i],
                    MyFooterSellData.selldrawableArrayWhite1[i],
                    MyFooterSellData.sellid_[i]
            ));
        }
        selladapter = new SellFooterCustomAdapter(MyPostingActivity.this, sellfooterdata);
        sellmyposting_footer.setAdapter(selladapter);

        mNav_myposting = new SimpleSideDrawer(this);
        mNav_myposting.setLeftBehindContentView(R.layout.activity_behind_left_simple);

        imageView_myposting = (ImageView) mNav_myposting.findViewById(R.id.profile_avatar);
        profile_name_myposting = (TextView) mNav_myposting.findViewById(R.id.profile_name);
        profile_address_myposting = (TextView) mNav_myposting.findViewById(R.id.profile_address);

        session_myposting = new SessionManager(MyPostingActivity.this);
        HashMap<String, String> user = session_myposting.getUserDetails();
        saved_name_myposting = user.get("dealer_name");
        saved_address_myposting = user.get("dealer_address");
        profile_name_myposting.setText(saved_name_myposting);
        if (user.get("dealer_img").isEmpty()) {
            Glide.with(getApplicationContext())
                    .load(R.drawable.default_avatar)
                    .transform(new RoundImageTransform(MyPostingActivity.this))
                    .into(imageView_myposting);
        } else {
            Glide.with(getApplicationContext())
                    .load(user.get("dealer_img"))
                    .transform(new RoundImageTransform(MyPostingActivity.this))
                    .into(imageView_myposting);
        }
        profile_address_myposting.setText(saved_address_myposting);
        myposting_mnav  = (ImageView) findViewById(R.id.myposting_mnav);
        myposting_mnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav_myposting.toggleLeftDrawer();
            }
        });

        //NAVIGATION DRAWER LIST VIEW
        myposting_buypagenavigation = new BuyPageNavigation();
        CustomList adapter = new CustomList(MyPostingActivity.this, myposting_buypagenavigation.web, myposting_buypagenavigation.imageId);
        ListView list = (ListView) findViewById(R.id.nav_list_view);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (myposting_buypagenavigation.web[position] == "Buy") {
                    Intent intent = new Intent(MyPostingActivity.this, DashBoard.class);
                    startActivity(intent);
                    mNav_myposting.closeLeftSide();
                    MyPostingActivity.this.finish();
                    Toast.makeText(MyPostingActivity.this, myposting_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (myposting_buypagenavigation.web[position] == "Sell") {
                    mNav_myposting.closeLeftSide();
                    Toast.makeText(MyPostingActivity.this, myposting_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (myposting_buypagenavigation.web[position] == "Manage") {
                    mNav_myposting.closeLeftSide();
                    Toast.makeText(MyPostingActivity.this, myposting_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (myposting_buypagenavigation.web[position] == "Communication") {
                    mNav_myposting.closeLeftSide();
                    Toast.makeText(MyPostingActivity.this, myposting_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (myposting_buypagenavigation.web[position] == "Reports") {
                    mNav_myposting.closeLeftSide();
                    Toast.makeText(MyPostingActivity.this, myposting_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (myposting_buypagenavigation.web[position] == "Logout") {
                    session_myposting.logoutUser();
                    mNav_myposting.closeLeftSide();
                    MyPostingActivity.this.finish();
                } else {
                    //Toast.makeText(DashBoard.this, web[position], Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
