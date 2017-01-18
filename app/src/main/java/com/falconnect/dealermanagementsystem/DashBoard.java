package com.falconnect.dealermanagementsystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.falconnect.dealermanagementsystem.Adapter.CustomAdapter;
import com.falconnect.dealermanagementsystem.Adapter.CustomList;
import com.falconnect.dealermanagementsystem.FontAdapter.RoundImageTransform;
import com.falconnect.dealermanagementsystem.Model.City_Make_Spinner_Model;
import com.falconnect.dealermanagementsystem.Model.DataModel;
import com.falconnect.dealermanagementsystem.NavigationDrawer.BuyPageNavigation;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;
import com.navdrawer.SimpleSideDrawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DashBoard extends AppCompatActivity {

    private boolean mVisible;
    Context context;
    ImageView nav;
    private SimpleSideDrawer mNav;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;

    List<String> vehilist;

    Intent intent;
    ArrayList<String> stringArray;

    String site_key, selected_city, selected_make, selected_model, selected_budget, selected_vehicle_type;

    public ArrayList<HashMap<String, String>> make_spinner_list;
    HashMap<String, String> makelist;


    public ArrayList<HashMap<String, String>> model_spinner_list;
    HashMap<String, String> modelist;

    public ArrayList<HashMap<String, String>> budget_spinner_list;
    HashMap<String, String> budgetlist;

    ArrayList<City_Make_Spinner_Model> datas;


    Button search;
    TextView spinner;
    Spinner bud_spinner, vehi_spinner, mod_spinner, bran_spinner;

    Button bud_mod, by_mod;

    ArrayAdapter<String> spinnerArrayAdapter;

    TextView sites;

    String encodedUrl = null;


    int value = 0;
    // Initializing a Vehicle Spinner Array
    String[] vehi = new String[]{
            "Select Vehicle type...",
            "Sedan",
            "Coupe",
            "Hatchback",
            "Minivan",
            "SUV",
            "Wagon"
    };

    private ArrayList<String> make_datas, model_datas, budget_datas;

    public ArrayList<HashMap<String, String>> site_spinner_list;
    HashMap<String, String> sitelist;
    String site_id, site_name;
    private ArrayList<String> site_datas;

    String get_brand_id, get_brand_name, get_model_id, get_model_name;

    String budget_id, budget_name;


    ///Navigation Drawer ListView Content
    ListView list;

    String selectedcity;

    SessionManager session;
    ImageView imageView;
    TextView profile_name;
    HashMap<String, String> user;
    TextView profile_address;
    String saved_name, saved_address, user_id;

    //int bud_mod_value, by_mod_value;

    String sitekey;
    //String sites_get;

    BuyPageNavigation mnavgation;

    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String SITE_KEY = "sitekey";
    public static final String CITY_KEY = "citykey";

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    int radioinline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dash_board);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mVisible = true;
        context = this;

        if (mVisible) {
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mVisible = false;

        } else {
        }

        intialize();
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        sitekey = getIntent().getStringExtra("sites_array");

        if (sitekey == null) {
            sites.setText("Select Sites");
        } else {
            sitekey = sitekey.replace("]", "").replace("[", "");
            sites.setText(sitekey);
            editor.putString(SITE_KEY, sitekey);
        }

        selectedcity = getIntent().getStringExtra("selected_item");
        if (selectedcity == null) {
            spinner.setText("Select City");
        } else {
            spinner.setText(selectedcity);
            editor.putString(CITY_KEY, selectedcity);
        }

        editor.commit();
        Log.e("Site_TAF", settings.getAll().toString());

        sites.setText(settings.getString(SITE_KEY, "Select Sites"));
        spinner.setText(settings.getString(CITY_KEY, "Select City"));

        // XML Parsing Using AsyncTask...
        if (isNetworkAvailable()) {
            new Site_Datas().execute();
            new Make_Datas().execute();
            new Budget_Datas().execute();
        } else {
            Toast.makeText(DashBoard.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        //Vehicle Datas to Spinner
        Vehi_Datas();
        make_datas = new ArrayList<String>();
        model_datas = new ArrayList<String>();
        budget_datas = new ArrayList<String>();
        site_datas = new ArrayList<String>();


        nav = (ImageView) findViewById(R.id.nav_icon_drawer);
        mNav = new SimpleSideDrawer(this);
        mNav.setLeftBehindContentView(R.layout.activity_behind_left_simple);

        imageView = (ImageView) mNav.findViewById(R.id.profile_avatar);
        profile_name = (TextView) mNav.findViewById(R.id.profile_name);
        profile_address = (TextView) mNav.findViewById(R.id.profile_address);

        session = new SessionManager(DashBoard.this);
        user = session.getUserDetails();
        saved_name = user.get("dealer_name");
        saved_address = user.get("dealer_address");
        profile_name.setText(saved_name);

        if (user.get("dealer_img").isEmpty()) {
            Glide.with(getApplicationContext())
                    .load(R.drawable.default_avatar)
                    .transform(new RoundImageTransform(DashBoard.this))
                    .into(imageView);
        } else {
            Glide.with(getApplicationContext())
                    .load(user.get("dealer_img"))
                    .transform(new RoundImageTransform(DashBoard.this))
                    .into(imageView);
        }
        profile_address.setText(saved_address);

        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav.toggleLeftDrawer();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DashBoard.this, LinearLayoutManager.HORIZONTAL, false));

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.id_[i],
                    MyData.drawableArrayWhite0[i]
            ));
        }

        adapter = new CustomAdapter(DashBoard.this, data);
        recyclerView.setAdapter(adapter);

        //Button By Model
        by_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioinline = 1;
                by_mod.setBackgroundResource(R.drawable.budget_model);
                bud_mod.setBackgroundResource(R.drawable.by_model);
                by_mod.setTextColor(Color.WHITE);
                bud_mod.setTextColor(Color.BLACK);

                //Visibility GONE spinner
                bud_spinner.setVisibility(View.GONE);
                vehi_spinner.setVisibility(View.GONE);

                //VISIBLE SPINNER
                mod_spinner.setVisibility(View.VISIBLE);
                bran_spinner.setVisibility(View.VISIBLE);

                model_datas.add("Select Model");
                spinnerArrayAdapter = new ArrayAdapter<String>(DashBoard.this, R.layout.spinner_single_item, model_datas);
                mod_spinner.setAdapter(spinnerArrayAdapter);

            }
        });

        //Button Budget Model
        bud_mod.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                radioinline = 0;
                by_mod.setBackgroundResource(R.drawable.by_model);
                bud_mod.setBackgroundResource(R.drawable.budget_model);
                by_mod.setTextColor(Color.BLACK);
                bud_mod.setTextColor(Color.WHITE);

                //Visibility GONE spinner
                mod_spinner.setVisibility(View.GONE);
                bran_spinner.setVisibility(View.GONE);

                //VISIBLE SPINNER
                bud_spinner.setVisibility(View.VISIBLE);
                vehi_spinner.setVisibility(View.VISIBLE);
            }
        });

        //NAVIGATION DRAWER LIST VIEW
        mnavgation = new BuyPageNavigation();
        CustomList adapter = new CustomList(DashBoard.this, mnavgation.web, mnavgation.imageId);
        list = (ListView) findViewById(R.id.nav_list_view);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mnavgation.web[position] == "Buy") {
                    mNav.closeLeftSide();
                    Toast.makeText(DashBoard.this, mnavgation.web[position], Toast.LENGTH_SHORT).show();
                } else if (mnavgation.web[position] == "Sell") {
                    Intent intent = new Intent(DashBoard.this, SellDashBoardActivity.class);
                    startActivity(intent);
                    DashBoard.this.finish();
                    mNav.closeLeftSide();
                    Toast.makeText(DashBoard.this, mnavgation.web[position], Toast.LENGTH_SHORT).show();
                } else if (mnavgation.web[position] == "Manage") {
                    Toast.makeText(DashBoard.this, mnavgation.web[position], Toast.LENGTH_SHORT).show();
                    mNav.closeLeftSide();
                } else if (mnavgation.web[position] == "Communication") {
                    mNav.closeLeftSide();
                    Toast.makeText(DashBoard.this, mnavgation.web[position], Toast.LENGTH_SHORT).show();
                } else if (mnavgation.web[position] == "Reports") {
                    mNav.closeLeftSide();
                    Toast.makeText(DashBoard.this, mnavgation.web[position], Toast.LENGTH_SHORT).show();
                } else if (mnavgation.web[position] == "Logout") {
                    session.logoutUser();
                    mNav.closeLeftSide();
                    editor.clear();
                    editor.commit();
                    DashBoard.this.finish();
                } else {
                    //Toast.makeText(DashBoard.this, web[position], Toast.LENGTH_SHORT).show();
                }
            }
        });

        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashBoard.this, CityActivity.class);
                startActivity(i);
                DashBoard.this.finish();

            }
        });


        //Button Event
        search_button();
    }

    public void intialize() {
        //Spinners
        spinner = (TextView) findViewById(R.id.search_city_spinner);
        bud_spinner = (Spinner) findViewById(R.id.budget_spinner);
        vehi_spinner = (Spinner) findViewById(R.id.vehi_type);
        bran_spinner = (Spinner) findViewById(R.id.brand_spinner);
        mod_spinner = (Spinner) findViewById(R.id.model_spinner);

        //TextView
        sites = (TextView) findViewById(R.id.search_sites);

        //Buttons
        bud_mod = (Button) findViewById(R.id.budget_model);
        by_mod = (Button) findViewById(R.id.by_model);
        search = (Button) findViewById(R.id.search_btn);
    }

    @Override
    public void onBackPressed() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        DashBoard.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }

    public void Vehi_Datas() {

        vehilist = new ArrayList<>(Arrays.asList(vehi));
        spinnerArrayAdapter = new ArrayAdapter<String>(DashBoard.this, R.layout.spinner_single_item, vehilist) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_single_item);

        vehi_spinner.setAdapter(spinnerArrayAdapter);

        vehi_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText1 = (String) parent.getItemAtPosition(position);

                if (position > 0) {
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText1, Toast.LENGTH_SHORT)
                            .show();

                    selected_vehicle_type = selectedItemText1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

                //site_datas = new ArrayList<>();

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

                        site_datas.add(site_name);
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

            sites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(DashBoard.this, SitesActivity.class);
                    i.putExtra("string_array", site_datas);
                    startActivity(i);
                    Toast.makeText(DashBoard.this, "Sites", Toast.LENGTH_SHORT).show();
                    DashBoard.this.finish();
                }
            });
        }
    }

    private class Make_Datas extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

            String city_url = Constant.DASH_BOARD_SPINNER_API;

            String json = sh.makeServiceCall(city_url, ServiceHandler.GET);

            datas = new ArrayList<City_Make_Spinner_Model>();

            if (json != null) {

                make_spinner_list = new ArrayList<>();

                try {
                    JSONObject jsonObj = new JSONObject(json);

                    make_datas.add("Select Brand");

                    JSONArray make = jsonObj.getJSONArray("model_make");

                    for (int j = 0; j <= make.length(); j++) {
                        get_brand_id = make.getJSONObject(j).getString("make_id");
                        get_brand_name = make.getJSONObject(j).getString("makename");

                        makelist = new HashMap<>();

                        makelist.put("make_id", get_brand_id);
                        makelist.put("makename", get_brand_name);

                        make_spinner_list.add(makelist);

                        make_datas.add(get_brand_name);
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

            //Brand Data Get
            spinnerArrayAdapter = new ArrayAdapter<String>(DashBoard.this, R.layout.spinner_single_item, make_datas) {
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0) {
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        tv.setTextColor(Color.GRAY);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_single_item);

            bran_spinner.setAdapter(spinnerArrayAdapter);

            bran_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String selectedItem_Text = (String) parent.getItemAtPosition(position);

                    if (position > 0) {
                        Toast.makeText
                                (getApplicationContext(), "Selected : " + selectedItem_Text, Toast.LENGTH_SHORT)
                                .show();

                        value = position;

                        selected_make = selectedItem_Text;

                        new Sub_model().execute();

                    }

                    if (bran_spinner.isClickable()) {
                        model_datas.clear();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

    }

    private class Sub_model extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

            String sub_make = Constant.DASH_BOARD_SUB_SPINNER_API + "make=" + value;

            String json = sh.makeServiceCall(sub_make, ServiceHandler.POST);

            //datas = new ArrayList<City_Make_Spinner_Model>();

            if (json != null) {

                model_spinner_list = new ArrayList<>();

                try {
                    JSONObject jsonObj = new JSONObject(json);

                    model_datas.add("Select Model");

                    JSONArray model = jsonObj.getJSONArray("model_makeid");

                    for (int j = 0; j <= model.length(); j++) {
                        get_model_id = model.getJSONObject(j).getString("model_id");
                        get_model_name = model.getJSONObject(j).getString("model_name");

                        modelist = new HashMap<>();

                        modelist.put("model_id", get_model_id);
                        modelist.put("model_name", get_model_name);

                        model_spinner_list.add(modelist);

                        model_datas.add(get_model_name);
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

            //Brand Data Get
            spinnerArrayAdapter = new ArrayAdapter<String>(DashBoard.this, R.layout.spinner_single_item, model_datas) {
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0) {
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        tv.setTextColor(Color.GRAY);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_single_item);

            mod_spinner.setAdapter(spinnerArrayAdapter);

            mod_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String selected_Item_Text = (String) parent.getItemAtPosition(position);

                    selected_Item_Text = selected_Item_Text.replaceAll(" ", "%20");

                    if (position > 0) {
                        Toast.makeText
                                (getApplicationContext(), "Selected : " + selected_Item_Text, Toast.LENGTH_SHORT)
                                .show();

                        selected_model = selected_Item_Text;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }

    private class Budget_Datas extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

            String budget_url = Constant.DASH_BOARD_SPINNER_API;

            String json = sh.makeServiceCall(budget_url, ServiceHandler.GET);

            datas = new ArrayList<City_Make_Spinner_Model>();

            if (json != null) {

                budget_spinner_list = new ArrayList<>();

                try {
                    JSONObject jsonObj = new JSONObject(json);

                    budget_datas.add("Select Budget");

                    JSONArray budget = jsonObj.getJSONArray("car_budget");

                    for (int j = 0; j <= budget.length(); j++) {
                        budget_id = budget.getJSONObject(j).getString("id");
                        budget_name = budget.getJSONObject(j).getString("budget_varient_name");

                        budgetlist = new HashMap<>();

                        budgetlist.put("ID", budget_id);
                        budgetlist.put("BUDGET", budget_name);

                        budget_spinner_list.add(budgetlist);

                        budget_datas.add(budget_name);
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

            spinnerArrayAdapter = new ArrayAdapter<String>(DashBoard.this, R.layout.spinner_single_item, budget_datas) {
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0) {
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        tv.setTextColor(Color.GRAY);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_single_item);

            bud_spinner.setAdapter(spinnerArrayAdapter);

            bud_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String selectedItemText = (String) parent.getItemAtPosition(position);

                    selectedItemText = selectedItemText.replaceAll(" ", "%20");


                    /*try {
                        encodedUrl = URLEncoder.encode(selectedItemText, "UTF-8");
                    } catch (UnsupportedEncodingException ignored) {
                    }*/

                    if (position > 0) {
                        Toast.makeText
                                (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                                .show();

                        selected_budget = selectedItemText;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

    }

    public void search_button() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selected_city = spinner.getText().toString();

                user_id = user.get("user_id");

                site_key = sites.getText().toString();
                site_key = site_key.toLowerCase();

                if (selected_city == "Select City" && site_key == "Select Site") {
                    Toast.makeText(DashBoard.this, "You Must Select Your Site", Toast.LENGTH_SHORT).show();
                    Toast.makeText(DashBoard.this, "You Must Select Your City", Toast.LENGTH_SHORT).show();
                } else {
                    String upperString = site_key.substring(0, 1).toUpperCase()
                            + site_key.substring(1);

                    if (selected_budget == null) {
                        selected_budget = "";
                    } else {

                    }
                    if (selected_make == null) {
                        selected_make = "";
                    } else {

                    }
                    if (selected_model == null) {
                        selected_model = "";
                    } else {

                    }
                    if (selected_vehicle_type == null) {
                        selected_vehicle_type = "";
                    } else {

                    }

                    String city_search_url = Constant.SEARCH_CAR_LISTING_API +
                            "session_user_id=" + user_id +
                            "&car_sites=" + upperString +
                            "&city_name=" + selected_city +
                            "&radioInline=" + radioinline +
                            "&car_budget=" + selected_budget +
                            "&vehicle_type=" + selected_vehicle_type +
                            "&vehicle_model=" + selected_model +
                            "&vehicle_make=" + selected_make +
                            "&page_name=searchpage";
                    Log.e("city_url_search", city_search_url);
                    Intent j = new Intent(DashBoard.this, SearchResultActivity.class);
                    j.putExtra("City", selected_city);
                    j.putExtra("City_Url", city_search_url);
                    startActivity(j);
                }
            }
        });
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
