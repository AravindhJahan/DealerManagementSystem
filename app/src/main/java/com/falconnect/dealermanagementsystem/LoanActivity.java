package com.falconnect.dealermanagementsystem;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
import com.falconnect.dealermanagementsystem.Fragment.MySellData;
import com.falconnect.dealermanagementsystem.Model.SellFooterDataModel;
import com.falconnect.dealermanagementsystem.NavigationDrawer.BuyPageNavigation;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;
import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;
import java.util.HashMap;

public class LoanActivity extends AppCompatActivity {

    private boolean mVisible;

    ImageView loan_mnav;
    private SimpleSideDrawer mNav_loan;
    SessionManager session_loan;
    ImageView imageView_loan;
    TextView profile_name_loan;
    TextView profile_address_loan;
    String saved_name_loan, saved_address_loan;
    BuyPageNavigation loan_buypagenavigation;


    private static RecyclerView sellloan_footer;
    private static ArrayList<SellFooterDataModel> sellfooterdata;
    private static RecyclerView.Adapter selladapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loan);

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

        sellloan_footer = (RecyclerView) findViewById(R.id.my_recycler_apply_loan);
        sellloan_footer.setHasFixedSize(true);
        sellloan_footer.setLayoutManager(new LinearLayoutManager(LoanActivity.this, LinearLayoutManager.HORIZONTAL, false));
        sellfooterdata = new ArrayList<SellFooterDataModel>();
        for (int i = 0; i < MyFooterSellData.sellnameArray.length; i++) {

            sellfooterdata.add(new SellFooterDataModel(
                    MyFooterSellData.sellnameArray[i],
                    MyFooterSellData.selldrawableArrayWhite4[i],
                    MyFooterSellData.sellid_[i]
            ));
        }
        selladapter = new SellFooterCustomAdapter(LoanActivity.this, sellfooterdata);
        sellloan_footer.setAdapter(selladapter);

        mNav_loan = new SimpleSideDrawer(this);
        mNav_loan.setLeftBehindContentView(R.layout.activity_behind_left_simple);

        imageView_loan = (ImageView) mNav_loan.findViewById(R.id.profile_avatar);
        profile_name_loan = (TextView) mNav_loan.findViewById(R.id.profile_name);
        profile_address_loan = (TextView) mNav_loan.findViewById(R.id.profile_address);

        session_loan = new SessionManager(LoanActivity.this);
        HashMap<String, String> user = session_loan.getUserDetails();
        saved_name_loan = user.get("dealer_name");
        saved_address_loan = user.get("dealer_address");
        profile_name_loan.setText(saved_name_loan);
        if (user.get("dealer_img").isEmpty()) {
            Glide.with(getApplicationContext()).load(R.drawable.default_avatar).into(imageView_loan);
        } else {
            Glide.with(getApplicationContext()).load(user.get("dealer_img")).into(imageView_loan);
        }
        profile_address_loan.setText(saved_address_loan);
        loan_mnav  = (ImageView) findViewById(R.id.loan_mnav);
        loan_mnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav_loan.toggleLeftDrawer();
            }
        });

        //NAVIGATION DRAWER LIST VIEW
        loan_buypagenavigation = new BuyPageNavigation();
        CustomList adapter = new CustomList(LoanActivity.this, loan_buypagenavigation.web, loan_buypagenavigation.imageId);
        ListView list = (ListView) findViewById(R.id.nav_list_view);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (loan_buypagenavigation.web[position] == "Buy") {
                    Intent intent = new Intent(LoanActivity.this, DashBoard.class);
                    startActivity(intent);
                    mNav_loan.closeLeftSide();
                    LoanActivity.this.finish();
                    Toast.makeText(LoanActivity.this, loan_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (loan_buypagenavigation.web[position] == "Sell") {
                    mNav_loan.closeLeftSide();
                    Toast.makeText(LoanActivity.this, loan_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (loan_buypagenavigation.web[position] == "Manage") {
                    mNav_loan.closeLeftSide();
                    Toast.makeText(LoanActivity.this, loan_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (loan_buypagenavigation.web[position] == "Communication") {
                    mNav_loan.closeLeftSide();
                    Toast.makeText(LoanActivity.this, loan_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (loan_buypagenavigation.web[position] == "Reports") {
                    mNav_loan.closeLeftSide();
                    Toast.makeText(LoanActivity.this, loan_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (loan_buypagenavigation.web[position] == "Logout") {
                    session_loan.logoutUser();
                    mNav_loan.closeLeftSide();
                    LoanActivity.this.finish();
                } else {
                    //Toast.makeText(DashBoard.this, web[position], Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
