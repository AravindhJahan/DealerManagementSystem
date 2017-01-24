package com.falconnect.dealermanagementsystem;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.falconnect.dealermanagementsystem.Adapter.ApplyFundingListAdapter;
import com.falconnect.dealermanagementsystem.Adapter.CustomList;
import com.falconnect.dealermanagementsystem.Adapter.LoanListAdapter;
import com.falconnect.dealermanagementsystem.Adapter.SellFooterCustomAdapter;
import com.falconnect.dealermanagementsystem.FontAdapter.RoundImageTransform;
import com.falconnect.dealermanagementsystem.Fragment.MySellData;
import com.falconnect.dealermanagementsystem.Model.ApplyFundingListModel;
import com.falconnect.dealermanagementsystem.Model.LoanModel;
import com.falconnect.dealermanagementsystem.Model.SellFooterDataModel;
import com.falconnect.dealermanagementsystem.NavigationDrawer.BuyPageNavigation;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;
import com.navdrawer.SimpleSideDrawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    HashMap<String, String> user;

    private static RecyclerView sellloan_footer;
    private static ArrayList<SellFooterDataModel> sellfooterdata;
    private static RecyclerView.Adapter selladapter;

    ListView loan_listView;
    public ArrayList<HashMap<String, String>> loan_listview;
    HashMap<String, String> loanlistview;


   LoanListAdapter loanListAdapter;




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

        loan_listView = (ListView) findViewById(R.id.loan_list);

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
        user = session_loan.getUserDetails();
        saved_name_loan = user.get("dealer_name");
        saved_address_loan = user.get("dealer_address");
        profile_name_loan.setText(saved_name_loan);

        if (user.get("dealer_img").isEmpty()) {
            Glide.with(getApplicationContext())
                    .load(R.drawable.default_avatar)
                    .transform(new RoundImageTransform(LoanActivity.this))
                    .into(imageView_loan);
        } else {
            Glide.with(getApplicationContext())
                    .load(user.get("dealer_img"))
                    .transform(new RoundImageTransform(LoanActivity.this))
                    .into(imageView_loan);
        }
        profile_address_loan.setText(saved_address_loan);
        loan_mnav = (ImageView) findViewById(R.id.loan_mnav);
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

        new loan_list().execute();

    }

    private ArrayList<LoanModel> getLoanData() {
        final ArrayList<LoanModel> loandata = new ArrayList<>();
        for (int i = 0; i < loan_listview.size(); i++) {

            String customername = loan_listview.get(i).get("customername");
            String tokenid = loan_listview.get(i).get("tokenid");
            String amount = loan_listview.get(i).get("amount");
            String customermailid = loan_listview.get(i).get("customermailid");
            String status = loan_listview.get(i).get("status");
            String customermobileno = loan_listview.get(i).get("customermobileno");
            String date = loan_listview.get(i).get("date");
            String bankimage = loan_listview.get(i).get("bankimage");

            loandata.add(new LoanModel(customername,tokenid, customermobileno, amount, customermailid, status, date, bankimage));
        }
        return loandata;
    }

    private class loan_list extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

            String queriesurl = Constant.APPLY_LOAN + "session_user_id=" + user.get("user_id")
                    +"&page_name=viewloanpage";

            Log.e("queriesurl",queriesurl);

            String json = sh.makeServiceCall(queriesurl, ServiceHandler.POST);

            if (json != null) {

                loan_listview = new ArrayList<>();

                try {
                    JSONObject jsonObj = new JSONObject(json);

                    JSONArray loan = jsonObj.getJSONArray("loan_list");

                    for (int k = 0; k <= loan.length(); k++) {

                        String customername = loan.getJSONObject(k).getString("customername");
                        String tokenid = loan.getJSONObject(k).getString("tokenid");
                        String amount = loan.getJSONObject(k).getString("amount");
                        String date = loan.getJSONObject(k).getString("date");
                        String customermobileno = loan.getJSONObject(k).getString("customermobileno");
                        String customermailid = loan.getJSONObject(k).getString("customermailid");
                        String status = loan.getJSONObject(k).getString("status");
                        String bankimage = loan.getJSONObject(k).getString("bankimage");

                        loanlistview = new HashMap<>();

                        loanlistview.put("customername", customername);
                        loanlistview.put("tokenid", tokenid);
                        loanlistview.put("amount", amount);
                        loanlistview.put("status", status);
                        loanlistview.put("customermobileno", customermobileno);
                        loanlistview.put("date", date);
                        loanlistview.put("bankimage", bankimage);
                        loanlistview.put("customermailid", customermailid);

                        loan_listview.add(loanlistview);

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

            loanListAdapter = new LoanListAdapter(LoanActivity.this, getLoanData());
            loan_listView.setAdapter(loanListAdapter);

            loan_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    LoanModel loanModel = (LoanModel)parent.getItemAtPosition(position);

                    Intent intent = new Intent(LoanActivity.this, LoanViewActivity.class);
                    startActivity(intent);
                    Toast.makeText(LoanActivity.this, loanModel.getCustname(), Toast.LENGTH_SHORT).show();

                }
            });



        }

    }

}
