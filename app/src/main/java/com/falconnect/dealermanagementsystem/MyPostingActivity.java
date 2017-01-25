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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.falconnect.dealermanagementsystem.Adapter.BidsPostingListAdapter;
import com.falconnect.dealermanagementsystem.Adapter.CustomList;
import com.falconnect.dealermanagementsystem.Adapter.LoanListAdapter;
import com.falconnect.dealermanagementsystem.Adapter.MyPostingListAdapter;
import com.falconnect.dealermanagementsystem.Adapter.SellFooterCustomAdapter;
import com.falconnect.dealermanagementsystem.FontAdapter.RoundImageTransform;
import com.falconnect.dealermanagementsystem.Model.BidsPostingListModel;
import com.falconnect.dealermanagementsystem.Model.LoanModel;
import com.falconnect.dealermanagementsystem.Model.MyPostingListModel;
import com.falconnect.dealermanagementsystem.Model.SellFooterDataModel;
import com.falconnect.dealermanagementsystem.NavigationDrawer.BuyPageNavigation;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;
import com.navdrawer.SimpleSideDrawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyPostingActivity extends AppCompatActivity {

    private boolean mVisible;

    ImageView myposting_mnav;
    private SimpleSideDrawer mNav_myposting;
    SessionManager session_myposting;
    ImageView imageView_myposting;
    TextView profile_name_myposting;
    TextView profile_address_myposting;
    String saved_name_myposting, saved_address_myposting;
    BuyPageNavigation myposting_buypagenavigation;

    private static RecyclerView sellmyposting_footer;
    private static ArrayList<SellFooterDataModel> sellfooterdata;
    private static RecyclerView.Adapter selladapter;
    HashMap<String, String> user;
    public ArrayList<HashMap<String, String>> my_posting_list;
    HashMap<String, String> mypositinglist;

    MyPostingListAdapter myPostingListAdapter;

    ListView myposting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_posting);

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


        sellmyposting_footer = (RecyclerView) findViewById(R.id.my_recycler_my_posting);
        sellmyposting_footer.setHasFixedSize(true);
        sellmyposting_footer.setLayoutManager(new LinearLayoutManager(MyPostingActivity.this, LinearLayoutManager.HORIZONTAL, false));
        sellfooterdata = new ArrayList<SellFooterDataModel>();
        for (int i = 0; i < MyFooterSellData.sellnameArray.length; i++) {

            sellfooterdata.add(new SellFooterDataModel(
                    MyFooterSellData.sellnameArray[i],
                    MyFooterSellData.selldrawableArrayWhite1[i],
                    MyFooterSellData.sellid_[i]
            ));
        }
        selladapter = new SellFooterCustomAdapter(MyPostingActivity.this, sellfooterdata);
        sellmyposting_footer.setAdapter(selladapter);

        mNav_myposting = new SimpleSideDrawer(this);
        mNav_myposting.setLeftBehindContentView(R.layout.activity_behind_left_simple);

        imageView_myposting = (ImageView) mNav_myposting.findViewById(R.id.profile_avatar);
        profile_name_myposting = (TextView) mNav_myposting.findViewById(R.id.profile_name);
        profile_address_myposting = (TextView) mNav_myposting.findViewById(R.id.profile_address);

        session_myposting = new SessionManager(MyPostingActivity.this);
        user = session_myposting.getUserDetails();
        saved_name_myposting = user.get("dealer_name");
        saved_address_myposting = user.get("dealer_address");
        profile_name_myposting.setText(saved_name_myposting);
        if (user.get("dealer_img").isEmpty()) {
            Glide.with(getApplicationContext())
                    .load(R.drawable.default_avatar)
                    .transform(new RoundImageTransform(MyPostingActivity.this))
                    .into(imageView_myposting);
        } else {
            Glide.with(getApplicationContext())
                    .load(user.get("dealer_img"))
                    .transform(new RoundImageTransform(MyPostingActivity.this))
                    .into(imageView_myposting);
        }
        profile_address_myposting.setText(saved_address_myposting);
        myposting_mnav  = (ImageView) findViewById(R.id.myposting_mnav);
        myposting_mnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav_myposting.toggleLeftDrawer();
            }
        });

        //NAVIGATION DRAWER LIST VIEW
        myposting_buypagenavigation = new BuyPageNavigation();
        CustomList adapter = new CustomList(MyPostingActivity.this, myposting_buypagenavigation.web, myposting_buypagenavigation.imageId);
        ListView list = (ListView) findViewById(R.id.nav_list_view);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (myposting_buypagenavigation.web[position] == "Buy") {
                    Intent intent = new Intent(MyPostingActivity.this, DashBoard.class);
                    startActivity(intent);
                    mNav_myposting.closeLeftSide();
                    MyPostingActivity.this.finish();
                    Toast.makeText(MyPostingActivity.this, myposting_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (myposting_buypagenavigation.web[position] == "Sell") {
                    mNav_myposting.closeLeftSide();
                    Toast.makeText(MyPostingActivity.this, myposting_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (myposting_buypagenavigation.web[position] == "Manage") {
                    mNav_myposting.closeLeftSide();
                    Toast.makeText(MyPostingActivity.this, myposting_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (myposting_buypagenavigation.web[position] == "Communication") {
                    mNav_myposting.closeLeftSide();
                    Toast.makeText(MyPostingActivity.this, myposting_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (myposting_buypagenavigation.web[position] == "Reports") {
                    mNav_myposting.closeLeftSide();
                    Toast.makeText(MyPostingActivity.this, myposting_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (myposting_buypagenavigation.web[position] == "Logout") {
                    session_myposting.logoutUser();
                    mNav_myposting.closeLeftSide();
                    MyPostingActivity.this.finish();
                } else {
                    //Toast.makeText(DashBoard.this, web[position], Toast.LENGTH_SHORT).show();
                }
            }
        });

        new my_posting().execute();

    }

    private ArrayList<MyPostingListModel> getmypostingData() {
        final ArrayList<MyPostingListModel> mypostdata = new ArrayList<>();
        for (int i = 0; i < my_posting_list.size(); i++) {

            String imageurl = my_posting_list.get(i).get("imageurl");
            String year = my_posting_list.get(i).get("year");
            String price = my_posting_list.get(i).get("price");
            String kms = my_posting_list.get(i).get("kms");
            String owner = my_posting_list.get(i).get("owner");
            String fuel_type = my_posting_list.get(i).get("fuel_type");
            String make = my_posting_list.get(i).get("make");
            String model = my_posting_list.get(i).get("model");
            String variant = my_posting_list.get(i).get("variant");
            String imagecount = my_posting_list.get(i).get("imagecount");
            String videoscount = my_posting_list.get(i).get("videoscount");
            String documentcount = my_posting_list.get(i).get("documentcount");
            String viewscount = my_posting_list.get(i).get("viewscount");
            String mongopushdate = my_posting_list.get(i).get("mongopushdate");

            mypostdata.add(new MyPostingListModel(imageurl, year, price, kms, owner,
                    fuel_type, make, model, variant, imagecount, videoscount, documentcount,
                    viewscount, mongopushdate));
        }
        return mypostdata;
    }

    private class my_posting extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

            String queriesurl = Constant.MY_POSTING
                    + "session_user_id=" + user.get("user_id")
                    +"&page_name=viewmypost";

            Log.e("queriesurl",queriesurl);

            String json = sh.makeServiceCall(queriesurl, ServiceHandler.POST);

            if (json != null) {

                my_posting_list = new ArrayList<>();

                try {
                    JSONObject jsonObj = new JSONObject(json);

                    JSONArray loan = jsonObj.getJSONArray("myposting_list");

                    for (int k = 0; k <= loan.length(); k++) {

                        String imageurl = loan.getJSONObject(k).getString("imageurl");
                        String year = loan.getJSONObject(k).getString("year");
                        String price = loan.getJSONObject(k).getString("price");
                        String kms = loan.getJSONObject(k).getString("kms");
                        String owner = loan.getJSONObject(k).getString("owner");
                        String fuel_type = loan.getJSONObject(k).getString("fuel_type");
                        String make = loan.getJSONObject(k).getString("make");
                        String model = loan.getJSONObject(k).getString("model");
                        String variant = loan.getJSONObject(k).getString("variant");
                        String imagecount = loan.getJSONObject(k).getString("imagecount");
                        String videoscount = loan.getJSONObject(k).getString("videoscount");
                        String documentcount = loan.getJSONObject(k).getString("documentcount");
                        String viewscount = loan.getJSONObject(k).getString("viewscount");
                        String mongopushdate = loan.getJSONObject(k).getString("mongopushdate");

                        mypositinglist = new HashMap<>();

                        mypositinglist.put("imageurl", imageurl);
                        mypositinglist.put("year", year);
                        mypositinglist.put("price", price);
                        mypositinglist.put("kms", kms);
                        mypositinglist.put("owner", owner);
                        mypositinglist.put("fuel_type", fuel_type);
                        mypositinglist.put("make", make);
                        mypositinglist.put("model", model);
                        mypositinglist.put("variant", variant);
                        mypositinglist.put("imagecount", imagecount);
                        mypositinglist.put("videoscount", videoscount);
                        mypositinglist.put("documentcount", documentcount);
                        mypositinglist.put("viewscount", viewscount);
                        mypositinglist.put("mongopushdate", mongopushdate);

                        my_posting_list.add(mypositinglist);

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

            myposting = (ListView) findViewById(R.id.my_posting_listview);
            myPostingListAdapter = new MyPostingListAdapter(MyPostingActivity.this, getmypostingData());
            myposting.setAdapter(myPostingListAdapter);

        }

    }

}
