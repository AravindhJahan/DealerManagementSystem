package com.falconnect.dealermanagementsystem;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
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
import com.falconnect.dealermanagementsystem.Model.SellFooterDataModel;
import com.falconnect.dealermanagementsystem.NavigationDrawer.BuyPageNavigation;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;
import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;
import java.util.HashMap;

public class QueriesRecievedActivity extends AppCompatActivity {

    private boolean mVisible;

    private static RecyclerView sellqueries_footer;
    private static ArrayList<SellFooterDataModel> sellfooterdata;
    private static RecyclerView.Adapter selladapter;


    ImageView query_recieve_mNav;
    private SimpleSideDrawer mNav_query_receive;
    SessionManager session_query_receive;
    ImageView imageView_query_receive;
    TextView profile_name_query_receive;
    TextView profile_address_query_receive;
    String saved_name_query_receive, saved_address_query_receive;
    BuyPageNavigation query_receive_buypagenavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_queries_recieved);
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
        sellqueries_footer = (RecyclerView) findViewById(R.id.my_recycler_queries_recieved);
        sellqueries_footer.setHasFixedSize(true);
        sellqueries_footer.setLayoutManager(new LinearLayoutManager(QueriesRecievedActivity.this, LinearLayoutManager.HORIZONTAL, false));
        sellfooterdata = new ArrayList<SellFooterDataModel>();
        for (int i = 0; i < MyFooterSellData.sellnameArray.length; i++) {

            sellfooterdata.add(new SellFooterDataModel(
                    MyFooterSellData.sellnameArray[i],
                    MyFooterSellData.selldrawableArrayWhite3[i],
                    MyFooterSellData.sellid_[i]
            ));
        }
        selladapter = new SellFooterCustomAdapter(QueriesRecievedActivity.this, sellfooterdata);
        sellqueries_footer.setAdapter(selladapter);


        mNav_query_receive = new SimpleSideDrawer(this);
        mNav_query_receive.setLeftBehindContentView(R.layout.activity_behind_left_simple);

        imageView_query_receive = (ImageView) mNav_query_receive.findViewById(R.id.profile_avatar);
        profile_name_query_receive = (TextView) mNav_query_receive.findViewById(R.id.profile_name);
        profile_address_query_receive = (TextView) mNav_query_receive.findViewById(R.id.profile_address);

        session_query_receive = new SessionManager(QueriesRecievedActivity.this);
        HashMap<String, String> user = session_query_receive.getUserDetails();
        saved_name_query_receive = user.get("dealer_name");
        saved_address_query_receive = user.get("dealer_address");
        profile_name_query_receive.setText(saved_name_query_receive);
        if (user.get("dealer_img").isEmpty()) {
            Glide.with(getApplicationContext()).load(R.drawable.default_avatar).into(imageView_query_receive);
        } else {
            Glide.with(getApplicationContext()).load(user.get("dealer_img")).into(imageView_query_receive);
        }
        profile_address_query_receive.setText(saved_address_query_receive);
        query_recieve_mNav  = (ImageView) findViewById(R.id.query_recived_mnav);
        query_recieve_mNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav_query_receive.toggleLeftDrawer();
            }
        });

        //NAVIGATION DRAWER LIST VIEW
        query_receive_buypagenavigation = new BuyPageNavigation();
        CustomList adapter = new CustomList(QueriesRecievedActivity.this, query_receive_buypagenavigation.web, query_receive_buypagenavigation.imageId);
        ListView list = (ListView) findViewById(R.id.nav_list_view);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (query_receive_buypagenavigation.web[position] == "Buy") {
                    Intent intent = new Intent(QueriesRecievedActivity.this, DashBoard.class);
                    startActivity(intent);
                    mNav_query_receive.closeLeftSide();
                    QueriesRecievedActivity.this.finish();
                    Toast.makeText(QueriesRecievedActivity.this, query_receive_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (query_receive_buypagenavigation.web[position] == "Sell") {
                    mNav_query_receive.closeLeftSide();
                    Toast.makeText(QueriesRecievedActivity.this, query_receive_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (query_receive_buypagenavigation.web[position] == "Manage") {
                    mNav_query_receive.closeLeftSide();
                    Toast.makeText(QueriesRecievedActivity.this, query_receive_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (query_receive_buypagenavigation.web[position] == "Communication") {
                    mNav_query_receive.closeLeftSide();
                    Toast.makeText(QueriesRecievedActivity.this, query_receive_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (query_receive_buypagenavigation.web[position] == "Reports") {
                    mNav_query_receive.closeLeftSide();
                    Toast.makeText(QueriesRecievedActivity.this, query_receive_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (query_receive_buypagenavigation.web[position] == "Logout") {
                    session_query_receive.logoutUser();
                    mNav_query_receive.closeLeftSide();
                    QueriesRecievedActivity.this.finish();
                } else {
                    //Toast.makeText(DashBoard.this, web[position], Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
