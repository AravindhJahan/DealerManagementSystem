package com.falconnect.dealermanagementsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import com.falconnect.dealermanagementsystem.Adapter.CustomAdapter;
import com.falconnect.dealermanagementsystem.Adapter.CustomList;
import com.falconnect.dealermanagementsystem.Adapter.QueryListAdapter;
import com.falconnect.dealermanagementsystem.FontAdapter.RoundImageTransform;
import com.falconnect.dealermanagementsystem.Model.DataModel;
import com.falconnect.dealermanagementsystem.Model.QueryListModel;
import com.falconnect.dealermanagementsystem.Model.SingleProductModel;
import com.falconnect.dealermanagementsystem.NavigationDrawer.BuyPageNavigation;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;
import com.navdrawer.SimpleSideDrawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyQueriesActivity extends AppCompatActivity {

    private boolean mVisible;


    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView queriesrecyclerView;
    private static ArrayList<DataModel> data;

    ImageView my_queries_back;

    BuyPageNavigation queries_buypagenavigation;

    private SimpleSideDrawer mNav_queries;

    SessionManager session_queries;
    ImageView imageView_queries;
    TextView profile_name_queries;
    TextView profile_address_queries;
    HashMap<String, String> user;
    String saved_name_queries, saved_address_queries;


    //ListView
    ListView queries_listview;

    public ArrayList<HashMap<String, String>> queries_list;
    HashMap<String, String> querieslist;

    QueryListAdapter queryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_queries);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mVisible = true;

        if (mVisible) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mVisible = false;
        } else {

        }


        queriesrecyclerView = (RecyclerView) findViewById(R.id.my_recycler_queries);
        queriesrecyclerView.setHasFixedSize(false);
        queriesrecyclerView.setLayoutManager(new LinearLayoutManager(MyQueriesActivity.this, LinearLayoutManager.HORIZONTAL, false));

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.id_[i],
                    MyData.drawableArrayWhite2[i]
            ));
        }

        adapter = new CustomAdapter(MyQueriesActivity.this, data);
        queriesrecyclerView.setAdapter(adapter);

        my_queries_back = (ImageView) findViewById(R.id.my_queries_back);
        mNav_queries = new SimpleSideDrawer(this);
        mNav_queries.setLeftBehindContentView(R.layout.activity_behind_left_simple);


        imageView_queries = (ImageView) mNav_queries.findViewById(R.id.profile_avatar);
        profile_name_queries = (TextView) mNav_queries.findViewById(R.id.profile_name);
        profile_address_queries = (TextView) mNav_queries.findViewById(R.id.profile_address);

        session_queries = new SessionManager(MyQueriesActivity.this);
        user = session_queries.getUserDetails();
        saved_name_queries = user.get("dealer_name");
        saved_address_queries = user.get("dealer_address");
        profile_name_queries.setText(saved_name_queries);
        if (user.get("dealer_img").isEmpty()) {
            Glide.with(getApplicationContext())
                    .load(R.drawable.default_avatar)
                    .transform(new RoundImageTransform(MyQueriesActivity.this))
                    .into(imageView_queries);
        } else {
            Glide.with(getApplicationContext())
                    .load(user.get("dealer_img"))
                    .transform(new RoundImageTransform(MyQueriesActivity.this))
                    .into(imageView_queries);
        }
        profile_address_queries.setText(saved_address_queries);

        my_queries_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav_queries.toggleLeftDrawer();
            }
        });

        //NAVIGATION DRAWER LIST VIEW
        queries_buypagenavigation = new BuyPageNavigation();
        CustomList adapter = new CustomList(MyQueriesActivity.this, queries_buypagenavigation.web, queries_buypagenavigation.imageId);

        ListView list = (ListView) findViewById(R.id.nav_list_view);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (queries_buypagenavigation.web[position] == "Buy") {
                    mNav_queries.closeLeftSide();
                    Toast.makeText(MyQueriesActivity.this, queries_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (queries_buypagenavigation.web[position] == "Sell") {
                    Intent intent = new Intent(MyQueriesActivity.this, SellDashBoardActivity.class);
                    startActivity(intent);
                    mNav_queries.closeLeftSide();
                    Toast.makeText(MyQueriesActivity.this, queries_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (queries_buypagenavigation.web[position] == "Manage") {
                    mNav_queries.closeLeftSide();
                    Toast.makeText(MyQueriesActivity.this, queries_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (queries_buypagenavigation.web[position] == "Communication") {
                    mNav_queries.closeLeftSide();
                    Toast.makeText(MyQueriesActivity.this, queries_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (queries_buypagenavigation.web[position] == "Reports") {
                } else if (queries_buypagenavigation.web[position] == "Logout") {
                    session_queries.logoutUser();
                    mNav_queries.closeLeftSide();
                    MyQueriesActivity.this.finish();
                } else {
                    //Toast.makeText(DashBoard.this, web[position], Toast.LENGTH_SHORT).show();
                }
            }
        });

        queries_listview = (ListView) findViewById(R.id.queries_listview);

        new Myqueries_Data().execute();
    }

    @Override
    public void onBackPressed() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        MyQueriesActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }

    private ArrayList<QueryListModel> getdata() {
        final ArrayList<QueryListModel> querydata = new ArrayList<>();
        for (int i = 0; i < queries_list.size(); i++) {
            String image = queries_list.get(i).get("imagelink");
            String name = queries_list.get(i).get("dealer_name");
            String carname = queries_list.get(i).get("make");
            String details = queries_list.get(i).get("message");
            String timedata = queries_list.get(i).get("Time");
            querydata.add(new QueryListModel(image, carname, name, details, timedata));
        }
        return querydata;
    }

    private class Myqueries_Data extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

            String queriesurl = Constant.QUREIS_PAGE_API + "session_user_id=" + user.get("user_id");

            String json = sh.makeServiceCall(queriesurl, ServiceHandler.POST);

            if (json != null) {

                queries_list = new ArrayList<>();

                try {
                    JSONObject jsonObj = new JSONObject(json);

                    JSONArray query = jsonObj.getJSONArray("queries_list");

                    for (int k = 0; k <= query.length(); k++) {

                        String noimages = query.getJSONObject(k).getString("noimages");
                        String imagelink = query.getJSONObject(k).getString("imagelink");
                        String price = query.getJSONObject(k).getString("price");
                        String car_id = query.getJSONObject(k).getString("car_id");
                        String status = query.getJSONObject(k).getString("status");
                        String from_dealer_id = query.getJSONObject(k).getString("from_dealer_id");
                        String to_dealer_name = query.getJSONObject(k).getString("to_dealer_name");
                        String to_dealer_id = query.getJSONObject(k).getString("to_dealer_id");
                        String make = query.getJSONObject(k).getString("make");
                        String title = query.getJSONObject(k).getString("title");
                        String dealer_name = query.getJSONObject(k).getString("dealer_name");
                        String dealer_email = query.getJSONObject(k).getString("dealer_email");
                        String message = query.getJSONObject(k).getString("message");
                        String contact_transactioncode = query.getJSONObject(k).getString("contact_transactioncode");
                        String time = query.getJSONObject(k).getString("Time");

                        querieslist = new HashMap<>();

                        querieslist.put("noimages", noimages);
                        querieslist.put("imagelink", imagelink);
                        querieslist.put("price", price);
                        querieslist.put("car_id", car_id);
                        querieslist.put("status", status);
                        querieslist.put("from_dealer_id", from_dealer_id);
                        querieslist.put("to_dealer_name", to_dealer_name);
                        querieslist.put("to_dealer_id", to_dealer_id);
                        querieslist.put("make", make);
                        querieslist.put("title", title);
                        querieslist.put("dealer_name", dealer_name);
                        querieslist.put("dealer_email", dealer_email);
                        querieslist.put("message", message);
                        querieslist.put("contact_transactioncode", contact_transactioncode);
                        querieslist.put("Time", time);

                        queries_list.add(querieslist);

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

            queryListAdapter = new QueryListAdapter(MyQueriesActivity.this, getdata());
            queries_listview.setAdapter(queryListAdapter);
            queries_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    QueryListModel queryListModel = (QueryListModel)parent.getItemAtPosition(position);
                    Toast.makeText(MyQueriesActivity.this, queryListModel.getCar_name(), Toast.LENGTH_SHORT).show();

                }
            });


        }

    }


}
