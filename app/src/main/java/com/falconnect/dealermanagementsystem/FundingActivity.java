package com.falconnect.dealermanagementsystem;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.falconnect.dealermanagementsystem.Adapter.CustomAdapter;
import com.falconnect.dealermanagementsystem.Adapter.CustomList;
import com.falconnect.dealermanagementsystem.FontAdapter.RoundImageTransform;
import com.falconnect.dealermanagementsystem.Model.DataModel;
import com.falconnect.dealermanagementsystem.NavigationDrawer.BuyPageNavigation;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;
import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;
import java.util.HashMap;

public class FundingActivity extends AppCompatActivity {

    private boolean mVisible;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView fundingrecyclerView;
    private static ArrayList<DataModel> data;

    TextView plus;

    BuyPageNavigation funding_buypagenavigation;

    private SimpleSideDrawer mNav_funding;

    SessionManager session_fund;
    ImageView imageView_fund;
    TextView profile_name_fund;
    TextView profile_address_fund;
    String saved_name_fund, saved_address_fund;

    SessionManager session;
    CardView new_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_funding);

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

        fundingrecyclerView = (RecyclerView) findViewById(R.id.my_recycler_funding);
        fundingrecyclerView.setHasFixedSize(true);
        fundingrecyclerView.setLayoutManager(new LinearLayoutManager(FundingActivity.this, LinearLayoutManager.HORIZONTAL, false));

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.id_[i],
                    MyData.drawableArrayWhite4[i]
            ));
        }

        adapter = new CustomAdapter(FundingActivity.this, data);
        fundingrecyclerView.setAdapter(adapter);

        ImageView nav_funding = (ImageView)findViewById(R.id.nav_funding);
        mNav_funding = new SimpleSideDrawer(this);
        mNav_funding.setLeftBehindContentView(R.layout.activity_behind_left_simple);

        imageView_fund = (ImageView) mNav_funding.findViewById(R.id.profile_avatar);
        profile_name_fund = (TextView) mNav_funding.findViewById(R.id.profile_name);
        profile_address_fund = (TextView) mNav_funding.findViewById(R.id.profile_address);

        session_fund = new SessionManager(FundingActivity.this);
        HashMap<String, String> user = session_fund.getUserDetails();
        saved_name_fund = user.get("dealer_name");
        saved_address_fund = user.get("dealer_address");
        profile_name_fund.setText(saved_name_fund);
        if (user.get("dealer_img").isEmpty()) {
            Glide.with(getApplicationContext())
                    .load(R.drawable.default_avatar)
                    .transform(new RoundImageTransform(FundingActivity.this))
                    .into(imageView_fund);
        } else {
            Glide.with(getApplicationContext())
                    .load(user.get("dealer_img"))
                    .transform(new RoundImageTransform(FundingActivity.this))
                    .into(imageView_fund);
        }
        profile_address_fund.setText(saved_address_fund);

        nav_funding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav_funding.toggleLeftDrawer();
            }
        });

        plus = (TextView) findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundingActivity.this, ApplyFundingActivity.class);
                startActivity(intent);
            }
        });

        session = new SessionManager(FundingActivity.this);

        //NAVIGATION DRAWER LIST VIEW
        funding_buypagenavigation = new BuyPageNavigation();
        CustomList adapter = new CustomList(FundingActivity.this, funding_buypagenavigation.web, funding_buypagenavigation.imageId);
        ListView list = (ListView) findViewById(R.id.nav_list_view);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (funding_buypagenavigation.web[position] == "Buy") {
                    mNav_funding.closeLeftSide();
                    Toast.makeText(FundingActivity.this, funding_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (funding_buypagenavigation.web[position] == "Sell") {
                    Intent intent = new Intent(FundingActivity.this, SellDashBoardActivity.class);
                    startActivity(intent);
                    mNav_funding.closeLeftSide();
                    Toast.makeText(FundingActivity.this, funding_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (funding_buypagenavigation.web[position] == "Manage") {
                    mNav_funding.closeLeftSide();
                    Toast.makeText(FundingActivity.this, funding_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();

                } else if (funding_buypagenavigation.web[position] == "Communication") {
                    mNav_funding.closeLeftSide();
                    Toast.makeText(FundingActivity.this, funding_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (funding_buypagenavigation.web[position] == "Reports") {
                    mNav_funding.closeLeftSide();
                    Toast.makeText(FundingActivity.this, funding_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (funding_buypagenavigation.web[position] == "Logout") {
                    session.logoutUser();
                    mNav_funding.closeLeftSide();
                    FundingActivity.this.finish();
                } else {
                    //Toast.makeText(DashBoard.this, web[position], Toast.LENGTH_SHORT).show();
                }
            }
        });

        new_card = (CardView)findViewById(R.id.new_card);
        new_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (FundingActivity.this, FundingViewActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        FundingActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }
}
