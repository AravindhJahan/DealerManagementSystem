package com.falconnect.dealermanagementsystem;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.falconnect.dealermanagementsystem.Adapter.ApplyFundingListAdapter;
import com.falconnect.dealermanagementsystem.Adapter.BidsPostedListAdapter;
import com.falconnect.dealermanagementsystem.Adapter.CustomAdapter;
import com.falconnect.dealermanagementsystem.Adapter.CustomList;
import com.falconnect.dealermanagementsystem.FontAdapter.RoundImageTransform;
import com.falconnect.dealermanagementsystem.Model.ApplyFundingListModel;
import com.falconnect.dealermanagementsystem.Model.BidsPostedListModel;
import com.falconnect.dealermanagementsystem.Model.DataModel;
import com.falconnect.dealermanagementsystem.NavigationDrawer.BuyPageNavigation;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;
import com.navdrawer.SimpleSideDrawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class FundingActivity extends AppCompatActivity {

    private boolean mVisible;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView fundingrecyclerView;
    private static ArrayList<DataModel> data;

    TextView plus;

    BuyPageNavigation funding_buypagenavigation;

    private SimpleSideDrawer mNav_funding;

    SessionManager session_fund;
    ImageView imageView_fund;
    TextView profile_name_fund;
    TextView profile_address_fund;
    String saved_name_fund, saved_address_fund;

    SessionManager session;
    HashMap<String, String> user;

    ListView applyfunding_listview;
    public ArrayList<HashMap<String, String>> applyfund_list;
    HashMap<String, String> applyfundlist;
    ArrayList<String> datas = new ArrayList<String>();

    ApplyFundingListAdapter applyFundingListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_funding);

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

        fundingrecyclerView = (RecyclerView) findViewById(R.id.my_recycler_funding);
        fundingrecyclerView.setHasFixedSize(true);
        fundingrecyclerView.setLayoutManager(new LinearLayoutManager(FundingActivity.this, LinearLayoutManager.HORIZONTAL, false));

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.id_[i],
                    MyData.drawableArrayWhite4[i]
            ));
        }

        adapter = new CustomAdapter(FundingActivity.this, data);
        fundingrecyclerView.setAdapter(adapter);

        ImageView nav_funding = (ImageView)findViewById(R.id.nav_funding);
        mNav_funding = new SimpleSideDrawer(this);
        mNav_funding.setLeftBehindContentView(R.layout.activity_behind_left_simple);

        imageView_fund = (ImageView) mNav_funding.findViewById(R.id.profile_avatar);
        profile_name_fund = (TextView) mNav_funding.findViewById(R.id.profile_name);
        profile_address_fund = (TextView) mNav_funding.findViewById(R.id.profile_address);

        session_fund = new SessionManager(FundingActivity.this);
        user = session_fund.getUserDetails();
        saved_name_fund = user.get("dealer_name");
        saved_address_fund = user.get("dealer_address");
        profile_name_fund.setText(saved_name_fund);
        if (user.get("dealer_img").isEmpty()) {
            Glide.with(getApplicationContext())
                    .load(R.drawable.default_avatar)
                    .transform(new RoundImageTransform(FundingActivity.this))
                    .into(imageView_fund);
        } else {
            Glide.with(getApplicationContext())
                    .load(user.get("dealer_img"))
                    .transform(new RoundImageTransform(FundingActivity.this))
                    .into(imageView_fund);
        }
        profile_address_fund.setText(saved_address_fund);

        nav_funding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav_funding.toggleLeftDrawer();
            }
        });

        plus = (TextView) findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundingActivity.this, ApplyFundingActivity.class);
                startActivity(intent);
            }
        });

        session = new SessionManager(FundingActivity.this);

        //NAVIGATION DRAWER LIST VIEW
        funding_buypagenavigation = new BuyPageNavigation();
        CustomList adapter = new CustomList(FundingActivity.this, funding_buypagenavigation.web, funding_buypagenavigation.imageId);
        ListView list = (ListView) findViewById(R.id.nav_list_view);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (funding_buypagenavigation.web[position] == "Buy") {
                    mNav_funding.closeLeftSide();
                    Toast.makeText(FundingActivity.this, funding_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (funding_buypagenavigation.web[position] == "Sell") {
                    Intent intent = new Intent(FundingActivity.this, SellDashBoardActivity.class);
                    startActivity(intent);
                    FundingActivity.this.finish();
                    mNav_funding.closeLeftSide();
                    Toast.makeText(FundingActivity.this, funding_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (funding_buypagenavigation.web[position] == "Manage") {
                    mNav_funding.closeLeftSide();
                    Toast.makeText(FundingActivity.this, funding_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();

                } else if (funding_buypagenavigation.web[position] == "Communication") {
                    mNav_funding.closeLeftSide();
                    Toast.makeText(FundingActivity.this, funding_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (funding_buypagenavigation.web[position] == "Reports") {
                    mNav_funding.closeLeftSide();
                    Toast.makeText(FundingActivity.this, funding_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (funding_buypagenavigation.web[position] == "Logout") {
                    session.logoutUser();
                    mNav_funding.closeLeftSide();
                    FundingActivity.this.finish();
                } else {
                    //Toast.makeText(DashBoard.this, web[position], Toast.LENGTH_SHORT).show();
                }
            }
        });

        new Apply_fund().execute();

    }

    @Override
    public void onBackPressed() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        FundingActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }


    private ArrayList<ApplyFundingListModel> getFundData() {
        final ArrayList<ApplyFundingListModel> funddata = new ArrayList<>();
        for (int i = 0; i < applyfund_list.size(); i++) {
            String token = applyfund_list.get(i).get("Token");
            String contact = applyfund_list.get(i).get("Contact");
            String amount = applyfund_list.get(i).get("Amount");
            String email = applyfund_list.get(i).get("Email");
            String status = applyfund_list.get(i).get("Status");
            String date = applyfund_list.get(i).get("Date");

            funddata.add(new ApplyFundingListModel(token, contact, amount, email, status, date));
        }
        return funddata;
    }

    private class Apply_fund extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

            String queriesurl = Constant.APPLY_FUNDING_APT + "session_user_id=" + user.get("user_id");

            String json = sh.makeServiceCall(queriesurl, ServiceHandler.POST);

            if (json != null) {

                applyfund_list = new ArrayList<>();

                try {
                    JSONObject jsonObj = new JSONObject(json);

                    JSONArray apply_fund = jsonObj.getJSONArray("apply_inventory_fund_list");

                    for (int k = 0; k <= apply_fund.length(); k++) {

                        String token = apply_fund.getJSONObject(k).getString("Token");
                        String contact = apply_fund.getJSONObject(k).getString("Contact");
                        String amount = apply_fund.getJSONObject(k).getString("Amount");
                        String status = apply_fund.getJSONObject(k).getString("Status");
                        String email = apply_fund.getJSONObject(k).getString("Email");
                        String date = apply_fund.getJSONObject(k).getString("Date");

                        applyfundlist = new HashMap<>();

                        applyfundlist.put("Token", token);
                        applyfundlist.put("Contact", contact);
                        applyfundlist.put("Amount", amount);
                        applyfundlist.put("Status", status);
                        applyfundlist.put("Email", email);
                        applyfundlist.put("Date", date);

                        applyfund_list.add(applyfundlist);

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

            applyfunding_listview = (ListView) findViewById(R.id.funding_listview);

            applyFundingListAdapter = new ApplyFundingListAdapter(FundingActivity.this, getFundData());
            applyfunding_listview.setAdapter(applyFundingListAdapter);

            applyfunding_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ApplyFundingListModel applyFundingListModel = (ApplyFundingListModel)parent.getItemAtPosition(position);
                    Intent intent = new Intent(FundingActivity.this, FundingViewActivity.class);
                    intent.putExtra("token_id", applyFundingListModel.getToken());
                    intent.putExtra("contact_id", applyFundingListModel.getContact());
                    intent.putExtra("email_id", applyFundingListModel.getEmail());
                    intent.putExtra("amount_id", applyFundingListModel.getAmount());
                    intent.putExtra("status_id", applyFundingListModel.getStatus());
                    intent.putExtra("date_id", applyFundingListModel.getDate());
                    startActivity(intent);
                    Toast.makeText(FundingActivity.this, applyFundingListModel.getToken(), Toast.LENGTH_SHORT).show();

                }
            });


        }

    }
}
