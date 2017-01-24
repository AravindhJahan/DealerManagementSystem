package com.falconnect.dealermanagementsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.falconnect.dealermanagementsystem.Adapter.ApplyFundingListAdapter;
import com.falconnect.dealermanagementsystem.Adapter.CustomAdapter;
import com.falconnect.dealermanagementsystem.Model.ApplyFundingListModel;
import com.falconnect.dealermanagementsystem.Model.DataModel;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ApplyFundingActivity extends AppCompatActivity {

    private boolean mVisible;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView applyfundingrecyclerView;
    private static ArrayList<DataModel> data;

    ImageView applyfundingback;

    Button submit_button;

    String result, message;

    SessionManager session;
    HashMap<String, String> user;

    EditText dealers_name, dealership_name, dealer_email, dealer_mobile, dealer_amount, dealer_area, dealer_city;

    public ArrayList<HashMap<String, String>> applyfundactivity_list;
    HashMap<String, String> applyfundactivitylist;

    ApplyFundingListAdapter applyFundingListAdapter;

    String s1,s2,s3,s4,s5,s6,s7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_apply_funding);

        //keyboard off
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //full screen
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

        //footer
        applyfundingrecyclerView = (RecyclerView) findViewById(R.id.my_recycler_apply_funding);
        applyfundingrecyclerView.setHasFixedSize(true);
        applyfundingrecyclerView.setLayoutManager(new LinearLayoutManager(ApplyFundingActivity.this, LinearLayoutManager.HORIZONTAL, false));

        //footer data
        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.id_[i],
                    MyData.drawableArrayWhite4[i]
            ));
        }
        //footer adapter
        adapter = new CustomAdapter(ApplyFundingActivity.this, data);
        applyfundingrecyclerView.setAdapter(adapter);
        //end footer

        dealers_name = (EditText) findViewById(R.id.dealername);
        dealership_name = (EditText) findViewById(R.id.dealershipname);
        dealer_amount = (EditText) findViewById(R.id.amount);
        dealer_mobile = (EditText) findViewById(R.id.phone_num);
        dealer_email = (EditText) findViewById(R.id.email);
        dealer_city = (EditText) findViewById(R.id.city);
        dealer_area = (EditText) findViewById(R.id.area);


        //back button
        applyfundingback = (ImageView) findViewById(R.id.apply_funding_back);
        submit_button = (Button) findViewById(R.id.submit_button);

        //back button click event
        applyfundingback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyFundingActivity.this.finish();
            }
        });

        session = new SessionManager(ApplyFundingActivity.this);
        user = session.getUserDetails();
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1 = dealers_name.getText().toString();
                s2 = dealership_name.getText().toString();
                s3 = dealer_email.getText().toString();
                s4 = dealer_amount.getText().toString();
                s5 = dealer_area.getText().toString();
                s6 = dealer_city.getText().toString();
                s7 = dealer_mobile.getText().toString();
                new Apply_fund_activity().execute();
            }
        });

    }

    private class Apply_fund_activity extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

             String newqueriesurl = Constant.ADD_APPLY_FUNDING
                     + "session_user_id=" + user.get("user_id")
                     +"&page_name=addfundingpage"
                     +"&dealername=" + s1
                     +"&dealershipname=" + s2
                     +"&dealermailid=" + s3
                     +"&requested_amount=" + s4
                     +"&dealerarea=" + s5
                     +"&dealercity=" + s6
                     +"&dealermobileno=" + s7;

            Log.e("Queriesurl" , newqueriesurl);

            String json = sh.makeServiceCall(newqueriesurl, ServiceHandler.POST);

            if (json != null) {

                applyfundactivity_list = new ArrayList<>();

                try {
                    JSONObject jsonObj = new JSONObject(json);

                    for (int k = 0; k <= jsonObj.length(); k++) {

                        result = jsonObj.getString("Result");
                        message = jsonObj.getString("message");

                        applyfundactivitylist = new HashMap<>();

                        applyfundactivitylist.put("Result", result);
                        applyfundactivitylist.put("message", message);

                        applyfundactivity_list.add(applyfundactivitylist);

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

            if (applyfundactivitylist.get("Result").equals("1")) {
                Toast.makeText(ApplyFundingActivity.this, applyfundactivitylist.get("message"), Toast.LENGTH_SHORT).show();
                ApplyFundingActivity.this.finish();

            } else {
                Toast.makeText(ApplyFundingActivity.this, applyfundactivitylist.get("message"), Toast.LENGTH_SHORT).show();

            }
        }
    }
}