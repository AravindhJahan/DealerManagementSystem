package com.falconnect.dealermanagementsystem;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.falconnect.dealermanagementsystem.Adapter.CustomAdapter;
import com.falconnect.dealermanagementsystem.FontAdapter.RoundImageTransform;
import com.falconnect.dealermanagementsystem.Model.ApplyFundingListModel;
import com.falconnect.dealermanagementsystem.Model.DataModel;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FundingViewActivity extends AppCompatActivity {


    private boolean mVisible;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView fundingviewrecyclerView;
    private static ArrayList<DataModel> data;

    String token_id, contact_id, email_id, amount_id, date_id, status_id;

    TextView token, contact, email, amount, status, date;

    TextView header;

    Button revoke_fund;

    ImageView fund_image;

    SessionManager session;
    HashMap<String, String> user;

    public ArrayList<HashMap<String, String>> revoke_fund_list;
    HashMap<String, String> revokefundlist;


    String result, message;

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

        session = new SessionManager(FundingViewActivity.this);
        user = session.getUserDetails();

        token_id = getIntent().getStringExtra("token_id");
        contact_id = getIntent().getStringExtra("contact_id");
        amount_id = getIntent().getStringExtra("amount_id");
        email_id = getIntent().getStringExtra("email_id");
        status_id = getIntent().getStringExtra("status_id");
        date_id = getIntent().getStringExtra("date_id");

        revoke_fund = (Button) findViewById(R.id.revoke_funing);

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


        ImageView funding_view_back = (ImageView) findViewById(R.id.funding_view_back);

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
        header = (TextView) findViewById(R.id.fund_token_id);
        fund_image = (ImageView) findViewById(R.id.cholo_images);

        token.setText(token_id.toString());

        contact.setText(contact_id.toString());

        amount.setText(amount_id.toString());

        email.setText(email_id.toString());

        date.setText(date_id.toString());

        header.setText(token_id.toString());

        Glide.with(getApplicationContext())
                .load(R.drawable.chola)
                .transform(new RoundImageTransform(FundingViewActivity.this))
                .into(fund_image);

        if (status_id.equals("INPROGRESS")) {
            status.setText(status_id);
            status.setTextColor(Color.GRAY);
        } else if (status_id.equals("COMPLETED")) {
            status.setText(status_id);
            status.setTextColor(Color.GREEN);
            revoke_fund.setVisibility(View.GONE);
        } else if (status_id.equals("PENDING")) {
            status.setText(status_id);
            status.setTextColor(Color.RED);
        } else {
            status.setText(status_id);
            status.setTextColor(Color.RED);
            revoke_fund.setVisibility(View.GONE);
        }


        revoke_fund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new revoke_funding_buy().execute();
            }
        });

    }


    private class revoke_funding_buy extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

            String revoke_buy_funding = Constant.REVOKE_FUNDING_BUY
                    + "session_user_id=" + user.get("user_id")
                    + "&ticketid=" + token_id;

            Log.e("revoke_buy_funding",revoke_buy_funding);

            String json = sh.makeServiceCall(revoke_buy_funding, ServiceHandler.POST);

            if (json != null) {

                revoke_fund_list = new ArrayList<>();

                try {
                    JSONObject jsonObj = new JSONObject(json);

                    for (int k = 0; k <= jsonObj.length(); k++) {

                        result = jsonObj.getString("Result");
                        message = jsonObj.getString("message");

                        revokefundlist = new HashMap<>();

                        revokefundlist.put("Result", result);
                        revokefundlist.put("message", message);

                        revoke_fund_list.add(revokefundlist);

                    }

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }


            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (revokefundlist.get("Result").equals("1")) {
                Toast.makeText(FundingViewActivity.this, revokefundlist.get("message"), Toast.LENGTH_SHORT).show();
                FundingViewActivity.this.finish();

            } else {
                Toast.makeText(FundingViewActivity.this, revokefundlist.get("message"), Toast.LENGTH_SHORT).show();

            }
        }
    }
}
