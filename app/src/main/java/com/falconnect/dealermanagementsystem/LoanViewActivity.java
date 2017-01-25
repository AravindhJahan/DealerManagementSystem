package com.falconnect.dealermanagementsystem;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.falconnect.dealermanagementsystem.Adapter.SellFooterCustomAdapter;
import com.falconnect.dealermanagementsystem.Model.LoanModel;
import com.falconnect.dealermanagementsystem.Model.SellFooterDataModel;

import java.util.ArrayList;

public class LoanViewActivity extends AppCompatActivity {

    private boolean mVisible;

    private static RecyclerView loanrecycleview;
    private static ArrayList<SellFooterDataModel> sellfooterdata;
    private static RecyclerView.Adapter selladapter;

    ImageView loan_view_back_btn, loan_apply_profile;

    Button revoke;

    TextView custname, tokenid, amount, email, date, phone, loan_status;

    String cust_name, token_id, price, mail, time, number, status, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loan_view);

        mVisible = true;

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
        custname = (TextView) findViewById(R.id.loan_apply_owner_name);
        tokenid = (TextView) findViewById(R.id.loan_apply_token_num);
        amount = (TextView) findViewById(R.id.loan_apply_amount);
        email = (TextView) findViewById(R.id.loan_apply_email);
        date = (TextView) findViewById(R.id.loan_apply_date);
        phone = (TextView) findViewById(R.id.loan_apply_phone);
        loan_status = (TextView) findViewById(R.id.loan_status);
        loan_apply_profile = (ImageView) findViewById(R.id.loan_apply_profile);

        loan_view_back_btn = (ImageView) findViewById(R.id.loan_view_back_btn);
        loan_view_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanViewActivity.this.finish();
            }
        });

        loanrecycleview = (RecyclerView) findViewById(R.id.apply_loan_recycle_view);
        loanrecycleview.setHasFixedSize(true);
        loanrecycleview.setLayoutManager(new LinearLayoutManager(LoanViewActivity.this, LinearLayoutManager.HORIZONTAL, false));
        sellfooterdata = new ArrayList<SellFooterDataModel>();
        for (int i = 0; i < MyFooterSellData.sellnameArray.length; i++) {

            sellfooterdata.add(new SellFooterDataModel(
                    MyFooterSellData.sellnameArray[i],
                    MyFooterSellData.selldrawableArrayWhite4[i],
                    MyFooterSellData.sellid_[i]
            ));
        }
        selladapter = new SellFooterCustomAdapter(LoanViewActivity.this, sellfooterdata);
        loanrecycleview.setAdapter(selladapter);

        cust_name = getIntent().getStringExtra("customername");
        token_id = getIntent().getStringExtra("tokenid");
        price = getIntent().getStringExtra("amount");
        mail = getIntent().getStringExtra("customermailid");
        time = getIntent().getStringExtra("date");
        number = getIntent().getStringExtra("customermobileno");
        image = getIntent().getStringExtra("bankimage");
        status = getIntent().getStringExtra("status");


        custname.setText(cust_name);
        tokenid.setText(token_id);
        amount.setText(price);
        email.setText(mail);
        phone.setText(number);
        date.setText(time);

        revoke = (Button) findViewById(R.id.revoke_button);

        Glide.with(getApplicationContext())
                .load(image)
                .into(loan_apply_profile);

        if (status.equals("INPROGRESS")) {
            loan_status.setText(status);
            loan_status.setTextColor(Color.GRAY);
        } else if (status.equals("COMPLETED")) {
            loan_status.setText(status);
            loan_status.setTextColor(Color.GREEN);
            revoke.setVisibility(View.GONE);
        } else if (status.equals("PENDING")) {
            loan_status.setText(status);
            loan_status.setTextColor(Color.RED);
        } else {
            loan_status.setText(status);
            loan_status.setTextColor(Color.RED);
            revoke.setVisibility(View.GONE);
        }


    }


}
