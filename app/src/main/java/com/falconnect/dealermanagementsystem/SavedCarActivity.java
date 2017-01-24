package com.falconnect.dealermanagementsystem;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.falconnect.dealermanagementsystem.Adapter.CustomAdapter;
import com.falconnect.dealermanagementsystem.Adapter.CustomList;
import com.falconnect.dealermanagementsystem.Adapter.ProductListAdapter;
import com.falconnect.dealermanagementsystem.Adapter.SavedCarAdapter;
import com.falconnect.dealermanagementsystem.FontAdapter.RoundImageTransform;
import com.falconnect.dealermanagementsystem.Model.DataModel;
import com.falconnect.dealermanagementsystem.Model.SingleProductModel;
import com.falconnect.dealermanagementsystem.NavigationDrawer.BuyPageNavigation;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;
import com.navdrawer.SimpleSideDrawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SavedCarActivity extends AppCompatActivity {


    private boolean mVisible;
    Context context;

    ListView listView;

    SavedCarAdapter listAdapter;

    String Saved_Car_url;

    ImageView savedcar_back;
    private SimpleSideDrawer mNav_savedcar;

    BuyPageNavigation savedcar_buypagenavigation;

    TextView title;
    public ArrayList<HashMap<String, String>> saved_car_list;
    HashMap<String, String> savedcarlist;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView_search;
    private static ArrayList<DataModel> data;

    SessionManager session_savedcar;
    ImageView imageView_savedcar;
    TextView profile_name_savedcar;
    TextView profile_address_savedcar;
    String saved_name_savedcar, saved_address_savedcar, saved_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_saved_car);


        //Keyboard Hide
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Status Bar Hide
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mVisible = true;
        context = this;

        //Full Screen Activity
        if (mVisible) {
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mVisible = false;

        } else {
        }

        //Footer List View
        recyclerView_search = (RecyclerView) findViewById(R.id.my_recycler);
        //List View Fixed size
        recyclerView_search.setHasFixedSize(true);
        //List View Horizontal
        recyclerView_search.setLayoutManager(new LinearLayoutManager(SavedCarActivity.this, LinearLayoutManager.HORIZONTAL, false));

        //Footer List View Get Data
        data = new ArrayList<DataModel>();

        for (int i = 0; i < MyData.nameArray.length; i++) {

            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.id_[i],
                    MyData.drawableArrayWhite1[i]
            ));

        }

        //Footer List View Adapter
        adapter = new CustomAdapter(SavedCarActivity.this, data);
        recyclerView_search.setAdapter(adapter);

        //get city url
        title = (TextView) findViewById(R.id.title_saved_cars);

        listView = (ListView) findViewById(R.id.saved_car_list);

        savedcar_back = (ImageView) findViewById(R.id.savedcar_back);
        mNav_savedcar = new SimpleSideDrawer(this);
        mNav_savedcar.setLeftBehindContentView(R.layout.activity_behind_left_simple);

        imageView_savedcar = (ImageView) mNav_savedcar.findViewById(R.id.profile_avatar);
        profile_name_savedcar= (TextView) mNav_savedcar.findViewById(R.id.profile_name);
        profile_address_savedcar = (TextView) mNav_savedcar.findViewById(R.id.profile_address);

        session_savedcar = new SessionManager(SavedCarActivity.this);
        HashMap<String, String> user = session_savedcar.getUserDetails();
        saved_user_id = user.get("user_id");
        saved_name_savedcar = user.get("dealer_name");
        saved_address_savedcar = user.get("dealer_address");
        profile_name_savedcar.setText(saved_name_savedcar);
        if (user.get("dealer_img").isEmpty()) {
            Glide.with(getApplicationContext()).load(R.drawable.default_avatar)
                    .transform(new RoundImageTransform(SavedCarActivity.this))
                    .into(imageView_savedcar);
        } else {
            Glide.with(getApplicationContext())
                    .load(user.get("dealer_img"))
                    .transform(new RoundImageTransform(SavedCarActivity.this))
                    .into(imageView_savedcar);
        }
        profile_address_savedcar.setText(saved_address_savedcar);

        savedcar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav_savedcar.toggleLeftDrawer();
            }
        });

        Saved_Car_url = Constant.SAVED_CAR_API + "&session_user_id=" + saved_user_id;

        //NAVIGATION DRAWER LIST VIEW
        savedcar_buypagenavigation = new BuyPageNavigation();
        CustomList adapter = new CustomList(SavedCarActivity.this, savedcar_buypagenavigation.web, savedcar_buypagenavigation.imageId);
        ListView list = (ListView) findViewById(R.id.nav_list_view);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (savedcar_buypagenavigation.web[position] == "Buy") {
                    mNav_savedcar.closeLeftSide();
                    Toast.makeText(SavedCarActivity.this, savedcar_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (savedcar_buypagenavigation.web[position] == "Sell") {
                    Intent intent = new Intent(SavedCarActivity.this, SellDashBoardActivity.class);
                    startActivity(intent);
                    SavedCarActivity.this.finish();
                    mNav_savedcar.closeLeftSide();
                    Toast.makeText(SavedCarActivity.this, savedcar_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (savedcar_buypagenavigation.web[position] == "Manage") {
                    mNav_savedcar.closeLeftSide();
                    Toast.makeText(SavedCarActivity.this, savedcar_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (savedcar_buypagenavigation.web[position] == "Communication") {
                    mNav_savedcar.closeLeftSide();
                    Toast.makeText(SavedCarActivity.this, savedcar_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();
                } else if (savedcar_buypagenavigation.web[position] == "Reports") {

                    mNav_savedcar.closeLeftSide();
                    Toast.makeText(SavedCarActivity.this, savedcar_buypagenavigation.web[position], Toast.LENGTH_SHORT).show();

                } else if (savedcar_buypagenavigation.web[position] == "Logout") {
                    session_savedcar.logoutUser();
                    mNav_savedcar.closeLeftSide();
                    SavedCarActivity.this.finish();
                } else {
                    //Toast.makeText(DashBoard.this, web[position], Toast.LENGTH_SHORT).show();
                }

            }
        });

        new Saved_Car().execute();

    }

    @Override
    public void onBackPressed() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        SavedCarActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }

    private ArrayList<SingleProductModel> getData() {
        final ArrayList<SingleProductModel> imageItems = new ArrayList<>();
        for (int i = 0; i < saved_car_list.size(); i++) {
            String make = saved_car_list.get(i).get("make");
            String make_id = saved_car_list.get(i).get("make_id");
            String model = saved_car_list.get(i).get("model");
            String variant = saved_car_list.get(i).get("variant");
            String car_locality = saved_car_list.get(i).get("car_locality");
            String registration_year = saved_car_list.get(i).get("registration_year");
            String kilometer_run = saved_car_list.get(i).get("kilometer_run");
            String fuel_type = saved_car_list.get(i).get("fuel_type");
            String owner_type = saved_car_list.get(i).get("owner_type");
            String price = saved_car_list.get(i).get("price");
            String daysstmt = saved_car_list.get(i).get("daysstmt");
            String car_id = saved_car_list.get(i).get("car_id");
            String dealer_id = saved_car_list.get(i).get("dealer_id");
            String bid_image = saved_car_list.get(i).get("bid_image");
            String no_images = saved_car_list.get(i).get("no_images");
            String imagelinks = saved_car_list.get(i).get("imagelinks");
            String saved_car = saved_car_list.get(i).get("saved_car");
            String compare_car = saved_car_list.get(i).get("compare_car");
            String notify_car = saved_car_list.get(i).get("notify_car");
            String view_car = saved_car_list.get(i).get("view_car");
            String auction = saved_car_list.get(i).get("auction");
            String site_id = saved_car_list.get(i).get("site_id");
            String site_image = saved_car_list.get(i).get("site_image");

            imageItems.add(new SingleProductModel(make,make_id,model,variant,car_locality,registration_year,
                    kilometer_run,fuel_type,owner_type,price,daysstmt,car_id,
                    dealer_id,bid_image,no_images,imagelinks,saved_car,compare_car,
                    notify_car,view_car,auction,site_id,site_image));
        }
        return imageItems;
    }

    private class Saved_Car extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

            String json = sh.makeServiceCall(Saved_Car_url, ServiceHandler.POST);

            if (json != null) {

                saved_car_list = new ArrayList<>();

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

                        savedcarlist = new HashMap<>();

                        savedcarlist.put("make", make);
                        savedcarlist.put("make_id", make_id);
                        savedcarlist.put("model", model);
                        savedcarlist.put("variant", variant);
                        savedcarlist.put("car_locality", car_locality);
                        savedcarlist.put("registration_year", registration_year);
                        savedcarlist.put("kilometer_run", kilometer_run);
                        savedcarlist.put("fuel_type", fuel_type);
                        savedcarlist.put("owner_type", owner_type);
                        savedcarlist.put("price", price);
                        savedcarlist.put("daysstmt", daysstmt);
                        savedcarlist.put("car_id", car_id);
                        savedcarlist.put("dealer_id", dealer_id);
                        savedcarlist.put("bid_image", bid_image);
                        savedcarlist.put("no_images", no_images);
                        savedcarlist.put("imagelinks", imagelinks);
                        savedcarlist.put("saved_car", saved_car);
                        savedcarlist.put("compare_car", compare_car);
                        savedcarlist.put("notify_car", notify_car);
                        savedcarlist.put("view_car", view_car);
                        savedcarlist.put("auction", auction);
                        savedcarlist.put("site_id", site_id);
                        savedcarlist.put("site_image", site_image);

                        saved_car_list.add(savedcarlist);

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


            listAdapter = new SavedCarAdapter(SavedCarActivity.this, getData());
            listAdapter.notifyDataSetChanged();
            listView.setAdapter(listAdapter);
            listView.invalidateViews();
            reloadAllData();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    SingleProductModel item = (SingleProductModel) parent.getItemAtPosition(position);

                    Toast.makeText(SavedCarActivity.this, "Selected Car Name :" + item.getMake(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    private void reloadAllData(){
        // get new modified random data
        List<SingleProductModel> objects = getData();
        // update data in our adapter
        listAdapter.getData().clear();
        listAdapter.getData().addAll(objects);
        // fire the event
        listAdapter.notifyDataSetChanged();
    }

}