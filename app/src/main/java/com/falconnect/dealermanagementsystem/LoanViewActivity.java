package com.falconnect.dealermanagementsystem;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.falconnect.dealermanagementsystem.Adapter.SellFooterCustomAdapter;
import com.falconnect.dealermanagementsystem.Model.SellFooterDataModel;

import java.util.ArrayList;

public class LoanViewActivity extends AppCompatActivity {

    private boolean mVisible;

    private static RecyclerView loanrecycleview;
    private static ArrayList<SellFooterDataModel> sellfooterdata;
    private static RecyclerView.Adapter selladapter;

    ImageView loan_view_back_btn;

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

    }


}
