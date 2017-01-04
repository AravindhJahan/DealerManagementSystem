package com.falconnect.dealermanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

public class AuctionActivity extends AppCompatActivity {

    private boolean mVisible;

    ImageView auction_mnav;
    private SimpleSideDrawer mNav_auction;
    SessionManager session_auction;
    ImageView imageView_auction;
    TextView profile_name_auction;
    TextView profile_address_auction;
    String saved_name_auction, saved_address_auction;
    BuyPageNavigation auction_buypagenavigation;


    private static RecyclerView sellacution_footer;
    private static ArrayList<SellFooterDataModel> sellfooterdata;
    private static RecyclerView.Adapter selladapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_acution);

        mVisible = true;

        if (mVisible) {
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mVisible = false;
        } else {

        }

        sellacution_footer = (RecyclerView) findViewById(R.id.my_recycler_apply_loan);
        sellacution_footer.setHasFixedSize(true);
        sellacution_footer.setLayoutManager(new LinearLayoutManager(AuctionActivity.this, LinearLayoutManager.HORIZONTAL, false));
        sellfooterdata = new ArrayList<SellFooterDataModel>();
        for (int i = 0; i < MyFooterSellData.sellnameArray.length; i++) {

            sellfooterdata.add(new SellFooterDataModel(
                    MyFooterSellData.sellnameArray[i],
                    MyFooterSellData.selldrawableArrayWhite4[i],
                    MyFooterSellData.sellid_[i]
            ));
        }
        selladapter = new SellFooterCustomAdapter(AuctionActivity.this, sellfooterdata);
        sellacution_footer.setAdapter(selladapter);

        mNav_auction = new SimpleSideDrawer(this);
        mNav_auction.setLeftBehindContentView(R.layout.activity_behind_left_simple);

        imageView_auction = (ImageView) mNav_auction.findViewById(R.id.profile_avatar);
        profile_name_auction = (TextView) mNav_auction.findViewById(R.id.profile_name);
        profile_address_auction = (TextView) mNav_auction.findViewById(R.id.profile_address);

        session_auction = new SessionManager(AuctionActivity.this);
        HashMap<String, String> user = session_auction.getUserDetails();
        saved_name_auction = user.get("dealer_name");
        saved_address_auction = user.get("dealer_address");
        profile_name_auction.setText(saved_name_auction);
        if (user.get("dealer_img").isEmpty()) {
            Glide.with(getApplicationContext()).load(R.drawable.default_avatar).into(imageView_auction);
        } else {
            Glide.with(getApplicationContext()).load(user.get("dealer_img")).into(imageView_auction);
        }
        profile_address_auction.setText(saved_address_auction);
        auction_mnav  = (ImageView) findViewById(R.id.auction_mnav);
        auction_mnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav_auction.toggleLeftDrawer();
            }
        });

        //NAVIGATION DRAWER LIST VIEW
        auction_buypagenavigation = new BuyPageNavigation();
        CustomList adapter = new CustomList(AuctionActivity.this, auction_buypagenavigation.web, auction_buypagenavigation.imageId);
        ListView list = (ListView) findViewById(R.id.nav_list_view);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (auction_buypagenavigation.web[position] == "Buy") {
                    Intent intent = new Intent(AuctionActivity.this, DashBoard.class);
                    startActivity(intent);
                    mNav_auction.closeLeftSide();
                    AuctionActivity.this.finish();
                    Toast.makeText(AuctionActivity.this, auction_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (auction_buypagenavigation.web[position] == "Sell") {
                    mNav_auction.closeLeftSide();
                    Toast.makeText(AuctionActivity.this, auction_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (auction_buypagenavigation.web[position] == "Manage") {
                    mNav_auction.closeLeftSide();
                    Toast.makeText(AuctionActivity.this, auction_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (auction_buypagenavigation.web[position] == "Communication") {
                    mNav_auction.closeLeftSide();
                    Toast.makeText(AuctionActivity.this, auction_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (auction_buypagenavigation.web[position] == "Reports") {
                    mNav_auction.closeLeftSide();
                    Toast.makeText(AuctionActivity.this, auction_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (auction_buypagenavigation.web[position] == "Logout") {
                    session_auction.logoutUser();
                    mNav_auction.closeLeftSide();
                    AuctionActivity.this.finish();
                } else {
                    //Toast.makeText(DashBoard.this, web[position], Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
