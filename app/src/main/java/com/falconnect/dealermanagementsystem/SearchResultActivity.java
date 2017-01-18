package com.falconnect.dealermanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.falconnect.dealermanagementsystem.Adapter.CustomAdapter;
import com.falconnect.dealermanagementsystem.Adapter.ProductListAdapter;
import com.falconnect.dealermanagementsystem.FontAdapter.MultiSelectSpinner;
import com.falconnect.dealermanagementsystem.Model.DataModel;
import com.falconnect.dealermanagementsystem.Model.SingleProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultActivity extends AppCompatActivity {

    private boolean mVisible;
    Context context;

    ListView listView;

    String City, Make, Model;

    SearchView search;

    ProductListAdapter listAdapter;

    String City_Search_List_Url;
    public ArrayList<HashMap<String, String>> city_search_list;
    HashMap<String, String> citysearchlist;

    ArrayList<HashMap<String, String>> top_notes_array;

    ImageView return_btn;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView_search;
    private static ArrayList<DataModel> data;


    public static final String TOP_NOTE = "top_note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_result);

        //Keyboard Hide
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Status Bar Hide
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mVisible = true;
        context = this;

        City = getIntent().getStringExtra("City");
        Make = getIntent().getStringExtra("Make");
        Model = getIntent().getStringExtra("Model");


        //Full Screen Activity
        if (mVisible) {
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mVisible = false;

        } else {
        }

        //Back Arrow
        return_btn = (ImageView) findViewById(R.id.nav_back_drawer);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResultActivity.this.finish();
            }
        });
        //Footer List View
        recyclerView_search = (RecyclerView) findViewById(R.id.my_recycler);
        //List View Fixed size
        recyclerView_search.setHasFixedSize(true);
        //List View Horizontal
        recyclerView_search.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this, LinearLayoutManager.HORIZONTAL, false));

        //Footer List View Get Data
        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.id_[i],
                    MyData.drawableArrayWhite0[i]
            ));
        }
        //Footer List View Adapter
        adapter = new CustomAdapter(SearchResultActivity.this, data);
        recyclerView_search.setAdapter(adapter);


        //get city url
        City_Search_List_Url = getIntent().getStringExtra("City_Url");


        new Search_List().execute();

        //End Of Footer List View


        //Search List ListView ID
        listView = (ListView) findViewById(R.id.list_view);


        search = (SearchView) findViewById(R.id.searchView);
        search.setQueryHint("Search Car...");


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String encodedUrl = null;
                search.clearFocus();
                Intent intent = new Intent(SearchResultActivity.this, SearchResultActivity.class);
                intent.putExtra("fromActivity", "search");
                intent.putExtra("search_query", encodedUrl);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private ArrayList<SingleProductModel> getData() {
        final ArrayList<SingleProductModel> imageItems = new ArrayList<>();
        for (int i = 0; i < city_search_list.size(); i++) {

            String make = city_search_list.get(i).get("make");
            String make_id = city_search_list.get(i).get("make_id");
            String model = city_search_list.get(i).get("model");
            String variant = city_search_list.get(i).get("variant");
            String car_locality = city_search_list.get(i).get("car_locality");
            String registration_year = city_search_list.get(i).get("registration_year");
            String kilometer_run = city_search_list.get(i).get("kilometer_run");
            String fuel_type = city_search_list.get(i).get("fuel_type");
            String owner_type = city_search_list.get(i).get("owner_type");
            String price = city_search_list.get(i).get("price");
            String daysstmt = city_search_list.get(i).get("daysstmt");
            String car_id = city_search_list.get(i).get("car_id");
            String dealer_id = city_search_list.get(i).get("dealer_id");
            String bid_image = city_search_list.get(i).get("bid_image");
            String no_images = city_search_list.get(i).get("no_images");
            String imagelinks = city_search_list.get(i).get("imagelinks");
            String saved_car = city_search_list.get(i).get("saved_car");
            String compare_car = city_search_list.get(i).get("compare_car");
            String notify_car = city_search_list.get(i).get("notify_car");
            String view_car = city_search_list.get(i).get("view_car");
            String auction = city_search_list.get(i).get("auction");
            String site_id = city_search_list.get(i).get("site_id");
            String site_image = city_search_list.get(i).get("site_image");

            imageItems.add(new SingleProductModel(make,make_id,model,variant,car_locality,registration_year,
                    kilometer_run,fuel_type,owner_type,price,daysstmt,car_id,
                    dealer_id,bid_image,no_images,imagelinks,saved_car,compare_car,
                    notify_car,view_car,auction,site_id,site_image));
        }
        return imageItems;
    }

    private class Search_List extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

            String json = sh.makeServiceCall(City_Search_List_Url, ServiceHandler.POST);

            if (json != null) {

                city_search_list = new ArrayList<>();
                //top_notes_array = new ArrayList<>();

                try {
                    JSONObject jsonObj = new JSONObject(json);

                    JSONArray city = jsonObj.getJSONArray("car_listing");

                    for (int k = 0; k <= city.length(); k++) {

                        String make = city.getJSONObject(k).getString("make");
                        String make_id = city.getJSONObject(k).getString("make_id");
                        String model = city.getJSONObject(k).getString("model");
                        String variant = city.getJSONObject(k).getString("variant");
                        String car_locality = city.getJSONObject(k).getString("car_locality");
                        String registration_year = city.getJSONObject(k).getString("registration_year");
                        String kilometer_run = city.getJSONObject(k).getString("kilometer_run");
                        String fuel_type = city.getJSONObject(k).getString("fuel_type");
                        String owner_type = city.getJSONObject(k).getString("owner_type");
                        String price = city.getJSONObject(k).getString("price");
                        String daysstmt = city.getJSONObject(k).getString("daysstmt");
                        String car_id = city.getJSONObject(k).getString("car_id");
                        String dealer_id = city.getJSONObject(k).getString("dealer_id");
                        String bid_image = city.getJSONObject(k).getString("bid_image");
                        String no_images = city.getJSONObject(k).getString("no_images");
                        String imagelinks = city.getJSONObject(k).getString("imagelinks");
                        String saved_car = city.getJSONObject(k).getString("saved_car");
                        String compare_car = city.getJSONObject(k).getString("compare_car");
                        String notify_car = city.getJSONObject(k).getString("notify_car");
                        String view_car = city.getJSONObject(k).getString("view_car");
                        String auction = city.getJSONObject(k).getString("auction");
                        String site_id = city.getJSONObject(k).getString("site_id");
                        String site_image = city.getJSONObject(k).getString("site_image");

                        citysearchlist = new HashMap<>();

                        citysearchlist.put("make", make);
                        citysearchlist.put("make_id", make_id);
                        citysearchlist.put("model", model);
                        citysearchlist.put("variant", variant);
                        citysearchlist.put("car_locality", car_locality);
                        citysearchlist.put("registration_year", registration_year);
                        citysearchlist.put("kilometer_run", kilometer_run);
                        citysearchlist.put("fuel_type", fuel_type);
                        citysearchlist.put("owner_type", owner_type);
                        citysearchlist.put("price", price);
                        citysearchlist.put("daysstmt", daysstmt);
                        citysearchlist.put("car_id", car_id);
                        citysearchlist.put("dealer_id", dealer_id);
                        citysearchlist.put("bid_image", bid_image);
                        citysearchlist.put("no_images", no_images);
                        citysearchlist.put("imagelinks", imagelinks);
                        citysearchlist.put("saved_car", saved_car);
                        citysearchlist.put("compare_car", compare_car);
                        citysearchlist.put("notify_car", notify_car);
                        citysearchlist.put("view_car", view_car);
                        citysearchlist.put("auction", auction);
                        citysearchlist.put("site_id", site_id);
                        citysearchlist.put("site_image", site_image);

                        city_search_list.add(citysearchlist);

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


            listAdapter = new ProductListAdapter(SearchResultActivity.this, getData());
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    SingleProductModel item = (SingleProductModel) parent.getItemAtPosition(position);

                    Toast.makeText(SearchResultActivity.this, "Selected Car Name :" + item.getMake(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

}