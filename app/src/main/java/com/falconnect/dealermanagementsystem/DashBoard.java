package com.falconnect.dealermanagementsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doodle.android.chips.ChipsView;
import com.falconnect.dealermanagementsystem.Adapter.CustomAdapter;
import com.falconnect.dealermanagementsystem.Adapter.CustomList;
import com.falconnect.dealermanagementsystem.Model.City_Make_Spinner_Model;
import com.falconnect.dealermanagementsystem.Model.DataModel;
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
    AlertDialog.Builder alertdialogbuilder;
    ImageView nav;
    private SimpleSideDrawer mNav;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;

    //List<String> cityList;

    List<String> budget_list;
    List<String> vehilist;
    // List<String> model_list;


    String selected_city, selected_make, selected_model, selected_vehi, selected_budget;

    public ArrayList<HashMap<String, String>> city_spinner_list;

    HashMap<String, String> citylist;

    public ArrayList<HashMap<String, String>> make_spinner_list;

    HashMap<String, String> makelist;

    public ArrayList<HashMap<String, String>> model_spinner_list;

    HashMap<String, String> modelist;


    ArrayList<City_Make_Spinner_Model> datas;


    Button search;
    Spinner spinner;
    Spinner bud_spinner, vehi_spinner, mod_spinner, bran_spinner;

    Button bud_mod, by_mod;

    ArrayAdapter<String> spinnerArrayAdapter;

    TextView sites;

    boolean value_new, value_sample;

    int value = 0;

    // Initializing a Budget Spinner Array
    String[] budget = new String[]{
            "Select Budget Range...",
            "Below 1 Lakh",
            "1 Lakh - 2 Lakh",
            "2 Lakh - 3 Lakh",
            "3 Lakh - 4 Lakh",
            "5 Lakh - Above"
    };

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

    final String[] items = {
            "Quickr",
            "Carwale",
            "Cardekho",
            "OLX"};

   /* // Initializing a Brand Spinner Array
    String[] model = new String[]{
            "Select Model...",
            "X1012",
            "Xuv",
            "Duster",
            "R8",
            "A6",
            "C-Class"
    };
*/

    List<String> ItemsIntoList;

    boolean[] Selectedtruefalse = new boolean[]{
            false,
            false,
            false,
            false
    };

    AutoCompleteTextView text;

    int a = 0;

    final ArrayList itemsSelected = new ArrayList();

    private ProgressDialog pDialog;
    private ArrayList<String> spinner_datas, make_datas, model_datas;


    String get_city_id, get_city_name, get_brand_id, get_brand_name, get_model_id, get_model_name;


    ///Navigation Drawer ListView Content
    ListView list;

    String[] web = {
            "Home",
            "Compare",
            "Buy",
            "Sell",
            "Manage",
            "Communication",
            "Reports",
            "Contact",
            "Logout"
    };
    Integer[] imageId = {
            R.drawable.search_white,
            R.drawable.bids_white,
            R.drawable.funding_white,
            R.drawable.queries_white,
            R.drawable.savecar_white,
            R.drawable.queries_white,
            R.drawable.savecar_white,
            R.drawable.search_white,
            R.drawable.funding_white
    };

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

        new City_Datas().execute();
        new Make_Datas().execute();


        //Budget Datas to Spinner
        Budget_Datas();

        //Vehicle Datas to Spinner
        Vehi_Datas();

        //Model Datas to Spinner
        // Model_Datas();

        spinner_datas = new ArrayList<String>();

        make_datas = new ArrayList<String>();

        model_datas = new ArrayList<String>();

        nav = (ImageView) findViewById(R.id.nav_icon_drawer);
        mNav = new SimpleSideDrawer(this);
        mNav.setLeftBehindContentView(R.layout.activity_behind_left_simple);

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
                    MyData.drawableArray[i]
            ));
        }

        adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);


        //Site checkbox
        sites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialogbuilder = new AlertDialog.Builder(DashBoard.this);

                ItemsIntoList = Arrays.asList(items);

                alertdialogbuilder.setMultiChoiceItems(items, Selectedtruefalse, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    }
                });

                alertdialogbuilder.setCancelable(false);

                alertdialogbuilder.setTitle("Select Sites");

                alertdialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        while (a < Selectedtruefalse.length) {

                            value_new = Selectedtruefalse[a];

                            if (value_new) {
                                sites.setText(sites.getText() + ItemsIntoList.get(a) + " ");
                            }
                            a++;
                        }

                    }
                });

                if (sites.isClickable()) {
                    sites.setText("");
                }

                alertdialogbuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = alertdialogbuilder.create();

                dialog.show();
            }
        });


        //Button By Model
        by_mod.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
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

            }
        });

        //Button Budget Model
        bud_mod.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
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

        CustomList adapter = new CustomList(DashBoard.this, web, imageId);
        list = (ListView) findViewById(R.id.nav_list_view);
        list.setAdapter(adapter);
        //list.setDivider(null);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DashBoard.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
            }
        });

        //Button Event
        search_button();

    }

    public void intialize() {
        //Spinners
        spinner = (Spinner) findViewById(R.id.search_city_spinner);
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

    public void Budget_Datas() {
        budget_list = new ArrayList<>(Arrays.asList(budget));
        spinnerArrayAdapter = new ArrayAdapter<String>(DashBoard.this, R.layout.spinner_single_item, budget_list) {
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

                if (position > 0) {
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                String selectedItemText = (String) parent.getItemAtPosition(position);

                if (position > 0) {
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private class City_Datas extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DashBoard.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler sh = new ServiceHandler();

            String city_url = Constant.DASH_BOARD_SPINNER_API;

            String json = sh.makeServiceCall(city_url, ServiceHandler.GET);

            datas = new ArrayList<City_Make_Spinner_Model>();

            if (json != null) {

                city_spinner_list = new ArrayList<>();

                try {
                    JSONObject jsonObj = new JSONObject(json);

                    spinner_datas.add("Select City");

                    JSONArray city = jsonObj.getJSONArray("model_city");

                    for (int k = 0; k <= city.length(); k++) {

                        get_city_id = city.getJSONObject(k).getString("city_id");
                        get_city_name = city.getJSONObject(k).getString("city_name");

                        citylist = new HashMap<>();

                        citylist.put("city_id", get_city_id);
                        citylist.put("city_name", get_city_name);

                        city_spinner_list.add(citylist);
                        spinner_datas.add(get_city_name);
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

            pDialog.dismiss();

            ////City Data Get
            spinnerArrayAdapter = new ArrayAdapter<String>(DashBoard.this, R.layout.spinner_single_item, spinner_datas) {
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

            spinner.setAdapter(spinnerArrayAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItemText = (String) parent.getItemAtPosition(position);

                    if (position > 0) {
                        Toast.makeText
                                (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                                .show();

                        selected_city = selectedItemText;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            ////End City Data Get

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

            // datas = new ArrayList<City_Make_Spinner_Model>();

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

    public void search_button() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(DashBoard.this, SearchResultActivity.class);
                j.putExtra("City", selected_city);
                j.putExtra("Make", selected_make);
                j.putExtra("Model", selected_model);
                startActivity(j);
            }
        });
    }

}
