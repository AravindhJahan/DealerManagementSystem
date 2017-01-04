package com.falconnect.dealermanagementsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import com.falconnect.dealermanagementsystem.Model.DataModel;
import com.falconnect.dealermanagementsystem.NavigationDrawer.BuyPageNavigation;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;
import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;
import java.util.HashMap;

public class MyQueriesActivity extends AppCompatActivity {

    private boolean mVisible;


    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView queriesrecyclerView;
    private static ArrayList<DataModel> data;

    ImageView my_queries_back;

    BuyPageNavigation queries_buypagenavigation;

    private SimpleSideDrawer mNav_queries;

    SessionManager session_queries;
    ImageView imageView_queries;
    TextView profile_name_queries;
    TextView profile_address_queries;
    String saved_name_queries, saved_address_queries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_queries);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mVisible = true;

        if (mVisible) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mVisible = false;
        } else {

        }


        queriesrecyclerView = (RecyclerView) findViewById(R.id.my_recycler_queries);
        queriesrecyclerView.setHasFixedSize(false);
        queriesrecyclerView.setLayoutManager(new LinearLayoutManager(MyQueriesActivity.this, LinearLayoutManager.HORIZONTAL, false));

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.id_[i],
                    MyData.drawableArrayWhite2[i]
            ));
        }

        adapter = new CustomAdapter(MyQueriesActivity.this, data);
        queriesrecyclerView.setAdapter(adapter);

        my_queries_back = (ImageView) findViewById(R.id.my_queries_back);
        mNav_queries = new SimpleSideDrawer(this);
        mNav_queries.setLeftBehindContentView(R.layout.activity_behind_left_simple);


        imageView_queries = (ImageView) mNav_queries.findViewById(R.id.profile_avatar);
        profile_name_queries = (TextView) mNav_queries.findViewById(R.id.profile_name);
        profile_address_queries = (TextView) mNav_queries.findViewById(R.id.profile_address);

        session_queries = new SessionManager(MyQueriesActivity.this);
        HashMap<String, String> user = session_queries.getUserDetails();
        saved_name_queries = user.get("dealer_name");
        saved_address_queries = user.get("dealer_address");
        profile_name_queries.setText(saved_name_queries);
        if (user.get("dealer_img").isEmpty()) {
            Glide.with(getApplicationContext()).load(R.drawable.default_avatar).into(imageView_queries);
        } else {
            Glide.with(getApplicationContext()).load(user.get("dealer_img")).into(imageView_queries);
        }
        profile_address_queries.setText(saved_address_queries);

        my_queries_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav_queries.toggleLeftDrawer();
            }
        });

        //NAVIGATION DRAWER LIST VIEW
        queries_buypagenavigation = new BuyPageNavigation();
        CustomList adapter = new CustomList(MyQueriesActivity.this, queries_buypagenavigation.web, queries_buypagenavigation.imageId);

        ListView list = (ListView) findViewById(R.id.nav_list_view);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (queries_buypagenavigation.web[position] == "Buy") {
                    mNav_queries.closeLeftSide();
                    Toast.makeText(MyQueriesActivity.this, queries_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (queries_buypagenavigation.web[position] == "Sell") {
                    Intent intent = new Intent(MyQueriesActivity.this, SellDashBoardActivity.class);
                    startActivity(intent);
                    mNav_queries.closeLeftSide();
                    Toast.makeText(MyQueriesActivity.this, queries_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (queries_buypagenavigation.web[position] == "Manage") {
                    mNav_queries.closeLeftSide();
                    Toast.makeText(MyQueriesActivity.this, queries_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (queries_buypagenavigation.web[position] == "Communication") {
                    mNav_queries.closeLeftSide();
                    Toast.makeText(MyQueriesActivity.this, queries_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();                } else if (queries_buypagenavigation.web[position] == "Reports") {
                } else if (queries_buypagenavigation.web[position] == "Logout") {
                    session_queries.logoutUser();
                    mNav_queries.closeLeftSide();
                    MyQueriesActivity.this.finish();
                } else {
                    //Toast.makeText(DashBoard.this, web[position], Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        MyQueriesActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }

}
