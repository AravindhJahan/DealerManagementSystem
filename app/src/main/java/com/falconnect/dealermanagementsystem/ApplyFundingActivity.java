package com.falconnect.dealermanagementsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.falconnect.dealermanagementsystem.Adapter.CustomAdapter;
import com.falconnect.dealermanagementsystem.Model.DataModel;

import java.util.ArrayList;

public class ApplyFundingActivity extends AppCompatActivity {

    private boolean mVisible;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView applyfundingrecyclerView;
    private static ArrayList<DataModel> data;

    ImageView applyfundingback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_apply_funding);

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

        applyfundingrecyclerView = (RecyclerView) findViewById(R.id.my_recycler_apply_funding);
        applyfundingrecyclerView.setHasFixedSize(true);
        applyfundingrecyclerView.setLayoutManager(new LinearLayoutManager(ApplyFundingActivity.this, LinearLayoutManager.HORIZONTAL, false));

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.id_[i],
                    MyData.drawableArrayWhite4[i]
            ));
        }

        adapter = new CustomAdapter(ApplyFundingActivity.this, data);
        applyfundingrecyclerView.setAdapter(adapter);

        applyfundingback = (ImageView) findViewById(R.id.apply_funding_back);

        applyfundingback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyFundingActivity.this.finish();
            }
        });

    }

}