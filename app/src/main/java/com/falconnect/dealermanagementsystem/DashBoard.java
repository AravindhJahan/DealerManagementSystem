package com.falconnect.dealermanagementsystem;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.falconnect.dealermanagementsystem.Adapter.CustomAdapter;
import com.falconnect.dealermanagementsystem.Model.DataModel;
import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;
import java.util.Arrays;
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

    List<String> cityList;
    List<String> budget_list;
    List<String> vehilist;
    List<String> brand_list;
    List<String> model_list;

    Button search;
    Spinner spinner;
    Spinner bud_spinner, vehi_spinner, mod_spinner, bran_spinner;

    Button bud_mod, by_mod;

    ArrayAdapter<String> spinnerArrayAdapter;

    TextView sites;

    // Initializing a City Spinner Array
    String[] cities = new String[]{
            "Select City...",
            "Chennai",
            "Thanjavur",
            "Thiruvarur",
            "Vellore",
            "Cuddalore",
            "Pondicherry"
    };

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

    // Initializing a Brand Spinner Array
    String[] brand = new String[]{
            "Select Brand...",
            "Audi",
            "BMW",
            "Renaut",
            "Lamborghini",
            "Mercedes-Benz"
    };

    // Initializing a Brand Spinner Array
    String[] model = new String[]{
            "Select Model...",
            "X1012",
            "Xuv",
            "Duster",
            "R8",
            "A6",
            "C-Class"
    };

    final String[] items = {
            "Quickr",
            "Carwale",
            "Cardekho",
            "OLX"};


    List<String> ItemsIntoList;

    boolean[] Selectedtruefalse = new boolean[]{
            false,
            false,
            false,
            false
    };

    final ArrayList itemsSelected = new ArrayList();

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

        //City Datas to Spinner
        spinnerdata();

        //Budget Datas to Spinner
        Budget_Datas();

        //Vehicle Datas to Spinner
        Vehi_Datas();

        //Brand Datas to Spinner
        Brand_Datas();

        //Model Datas to Spinner
        Model_Datas();

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

                        int a = 0;
                        while (a < Selectedtruefalse.length) {
                            boolean value = Selectedtruefalse[a];

                            if (value) {
                                sites.setText(sites.getText() + ItemsIntoList.get(a) + "\n");
                            }
                            a++;
                        }

                    }
                });

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
        by_mod.setOnClickListener(new View.OnClickListener() {
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

        search.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(DashBoard.this, SearchResultActivity.class);
                startActivity(j);
            }
        });

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

    public void spinnerdata() {
        cityList = new ArrayList<>(Arrays.asList(cities));
        spinnerArrayAdapter = new ArrayAdapter<String>(DashBoard.this, R.layout.spinner_single_item, cityList) {
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    public void Brand_Datas() {
        brand_list = new ArrayList<>(Arrays.asList(brand));
        spinnerArrayAdapter = new ArrayAdapter<String>(DashBoard.this, R.layout.spinner_single_item, brand_list) {
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

    public void Model_Datas() {

        model_list = new ArrayList<>(Arrays.asList(model));
        spinnerArrayAdapter = new ArrayAdapter<String>(DashBoard.this, R.layout.spinner_single_item, model_list) {
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


}