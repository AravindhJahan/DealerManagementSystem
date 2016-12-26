package com.falconnect.dealermanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.falconnect.dealermanagementsystem.Adapter.MultiSelectListViewAdapter;
import com.falconnect.dealermanagementsystem.Adapter.ProductListAdapter;
import com.falconnect.dealermanagementsystem.Model.MultiSelectModel;
import com.falconnect.dealermanagementsystem.Model.SingleProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class SitesActivity extends AppCompatActivity {

    private boolean mVisible;

    private ListView sitelistView;

    private ImageView btn_apply_site;
    private ImageView btn_close_site;

    MultiSelectListViewAdapter adapter;

    public ArrayList<HashMap<String, String>> site_spinner_list;
    HashMap<String, String> sitelist;
    String site_id, site_name;
    private ArrayList<String> site_datas;

    ArrayList<MultiSelectModel> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sites);

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

        intialize();

        items = new ArrayList<MultiSelectModel>();
        //myList = (ArrayList<String>) getIntent().getSerializableExtra("Site_List");

        // XML Parsing Using AsyncTask...
        if (isNetworkAvailable()) {
            new Site_Datas().execute();
        } else {
            Toast.makeText(SitesActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        /*ArrayAdapter adapter = new ArrayAdapter(SitesActivity.this, R.layout.single_site_list_item, myList);
        sitelistView.setAdapter(adapter);*/


    }

    @Override
    public void onBackPressed() {

        Toast.makeText(SitesActivity.this, "Please select any one city", Toast.LENGTH_SHORT).show();

    }



    private ArrayList<MultiSelectModel> get_data() {
        final ArrayList<MultiSelectModel> imageItemsss = new ArrayList<>();
        for (int i = 0; i < site_spinner_list.size(); i++) {

            String name = site_spinner_list.get(i).get("sitename");
            String id = site_spinner_list.get(i).get("id");

            imageItemsss.add(new MultiSelectModel(id,name));

        }

        return imageItemsss;
    }
    public void intialize() {

        sitelistView = (ListView) findViewById(R.id.multiselectlisttitle);
        btn_apply_site = (ImageView) findViewById(R.id.apply_icon);
        btn_close_site = (ImageView) findViewById(R.id.close_icon);
    }

    private class Site_Datas extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

            String city_url = Constant.DASH_BOARD_SPINNER_API;

            String json = sh.makeServiceCall(city_url, ServiceHandler.GET);

            if (json != null) {

                site_spinner_list = new ArrayList<>();

                site_datas = new ArrayList<>();
                try {
                    JSONObject jsonObj = new JSONObject(json);

                    JSONArray city = jsonObj.getJSONArray("site_names");

                    for (int k = 0; k <= city.length(); k++) {

                        site_id = city.getJSONObject(k).getString("id");
                        site_name = city.getJSONObject(k).getString("sitename");

                        sitelist = new HashMap<>();

                        sitelist.put("id", site_id);
                        sitelist.put("sitename", site_name);

                        site_spinner_list.add(sitelist);

                        //site_datas.add(site_name);
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

            adapter = new MultiSelectListViewAdapter(SitesActivity.this, get_data());
            
            sitelistView.setAdapter(adapter);

        }
    }


    // Check Internet Connection!!!
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

