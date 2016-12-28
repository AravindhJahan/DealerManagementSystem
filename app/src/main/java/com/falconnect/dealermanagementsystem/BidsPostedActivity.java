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

import com.bumptech.glide.Glide;
import com.falconnect.dealermanagementsystem.Adapter.CustomAdapter;
import com.falconnect.dealermanagementsystem.Adapter.CustomList;
import com.falconnect.dealermanagementsystem.Model.DataModel;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;
import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;
import java.util.HashMap;

public class BidsPostedActivity extends AppCompatActivity {

    private boolean mVisible;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView bidsrecyclerView;
    private static ArrayList<DataModel> data;

    CardView card_new;

    ImageView bids_back;

    private SimpleSideDrawer mNav_bids;

    //side menu content
    String[] web = {
            "Buy",
            "Sell",
            "Manage",
            "Communication",
            "Reports",
            "Logout"
    };
    //side menu content icons
    Integer[] imageId = {
            R.drawable.buy_sidemenu,
            R.drawable.sell_sidemenu,
            R.drawable.manage_sidemenu,
            R.drawable.communication_sidemenu,
            R.drawable.report_sidemenu,
            R.drawable.logout_sidemenu
    };

    SessionManager session_bids;
    ImageView imageView_bids;
    TextView profile_name_bids;
    TextView profile_address_bids;
    String saved_name_bids, saved_address_bids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bids_posted);

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

        bidsrecyclerView = (RecyclerView) findViewById(R.id.my_recycler_bids);
        bidsrecyclerView.setHasFixedSize(true);
        bidsrecyclerView.setLayoutManager(new LinearLayoutManager(BidsPostedActivity.this, LinearLayoutManager.HORIZONTAL, false));

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.id_[i],
                    MyData.drawableArrayWhite3[i]
            ));
        }

        adapter = new CustomAdapter(BidsPostedActivity.this, data);
        bidsrecyclerView.setAdapter(adapter);

        card_new = (CardView) findViewById(R.id.card_new);

        card_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BidsPostedActivity.this, BidsPostingActivity.class);
                startActivity(intent);
            }
        });

        bids_back = (ImageView)findViewById(R.id.bids_back);

        mNav_bids = new SimpleSideDrawer(this);
        mNav_bids.setLeftBehindContentView(R.layout.activity_behind_left_simple);

        imageView_bids = (ImageView) mNav_bids.findViewById(R.id.profile_avatar);
        profile_name_bids= (TextView) mNav_bids.findViewById(R.id.profile_name);
        profile_address_bids = (TextView) mNav_bids.findViewById(R.id.profile_address);

        session_bids = new SessionManager(BidsPostedActivity.this);
        HashMap<String, String> user = session_bids.getUserDetails();
        saved_name_bids = user.get("dealer_name");
        saved_address_bids = user.get("dealer_address");
        profile_name_bids.setText(saved_name_bids);
        if (user.get("dealer_img").isEmpty()) {
            Glide.with(getApplicationContext()).load(R.drawable.default_avatar).into(imageView_bids);
        } else {
            Glide.with(getApplicationContext()).load(user.get("dealer_img")).into(imageView_bids);
        }
        profile_address_bids.setText(saved_address_bids);

        bids_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav_bids.toggleLeftDrawer();
            }
        });

        //NAVIGATION DRAWER LIST VIEW
        CustomList adapter = new CustomList(BidsPostedActivity.this, web, imageId);
        ListView list = (ListView) findViewById(R.id.nav_list_view);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

    }

    //Back pressed alert box appear
    @Override
    public void onBackPressed() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        BidsPostedActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }

}
