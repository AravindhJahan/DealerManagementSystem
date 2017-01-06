package com.falconnect.dealermanagementsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.falconnect.dealermanagementsystem.Adapter.CustomAdapter;
import com.falconnect.dealermanagementsystem.FontAdapter.RoundImageTransform;
import com.falconnect.dealermanagementsystem.Model.ApplyFundingListModel;
import com.falconnect.dealermanagementsystem.Model.DataModel;

import java.util.ArrayList;

public class FundingViewActivity extends AppCompatActivity {


    private boolean mVisible;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView fundingviewrecyclerView;
    private static ArrayList<DataModel> data;

    String token_id, contact_id, email_id, amount_id, date_id, status_id;

    TextView token, contact, email, amount, status, date;

    TextView header;

    ImageView fund_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_funding_view);

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

        token_id = getIntent().getStringExtra("token_id");
        contact_id = getIntent().getStringExtra("contact_id");
        amount_id = getIntent().getStringExtra("amount_id");
        email_id = getIntent().getStringExtra("email_id");
        status_id = getIntent().getStringExtra("status_id");
        date_id = getIntent().getStringExtra("date_id");

        fundingviewrecyclerView = (RecyclerView) findViewById(R.id.funding_view_my_recycler);
        fundingviewrecyclerView.setHasFixedSize(true);
        fundingviewrecyclerView.setLayoutManager(new LinearLayoutManager(FundingViewActivity.this, LinearLayoutManager.HORIZONTAL, false));

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.id_[i],
                    MyData.drawableArrayWhite4[i]
            ));
        }

        adapter = new CustomAdapter(FundingViewActivity.this, data);
        fundingviewrecyclerView.setAdapter(adapter);


        ImageView funding_view_back =(ImageView)findViewById(R.id.funding_view_back);

        funding_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FundingViewActivity.this.finish();
            }
        });

        token = (TextView) findViewById(R.id.token_id);
        contact = (TextView) findViewById(R.id.contact_id);
        amount = (TextView) findViewById(R.id.amount_id);
        email = (TextView) findViewById(R.id.email_id);
        status = (TextView) findViewById(R.id.status);
        date = (TextView) findViewById(R.id.date_id);
        header = (TextView) findViewById(R.id.fund_token_id) ;
        fund_image = (ImageView) findViewById(R.id.cholo_images);

        token.setText(token_id.toString());

        contact.setText(contact_id.toString());

        amount.setText(amount_id.toString());

        email.setText(email_id.toString());

        status.setText(status_id.toString());

        date.setText(date_id.toString());

        header.setText(token_id.toString());

        Glide.with(getApplicationContext())
                .load(R.drawable.chola)
                .transform(new RoundImageTransform(FundingViewActivity.this))
                .into(fund_image);

    }

}
