package com.falconnect.dealermanagementsystem;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.falconnect.dealermanagementsystem.Adapter.CustomListViewAdapter;
import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashBoard extends AppCompatActivity {


    private boolean mVisible;
    Context context;

    ImageView nav;

    private SimpleSideDrawer mNav;


    public static int [] prgmImages={
            R.drawable.search_blue,
            R.drawable.savecar_blue,
            R.drawable.queries_blue,
            R.drawable.bids_blue,
            R.drawable.funding_blue};

    public static String [] prgmNameList = {
            "Search",
            "Saved Cars",
            "My Queries",
            "Bids Posted",
            "Funding"};

    List<String> cityList;
    Spinner spinner;
    ArrayAdapter<String> spinnerArrayAdapter;

    // Initializing a String Array
    String[] cities = new String[]{
            "Select City...",
            "Chennai",
            "Thanjavur",
            "Thiruvarur",
            "Vellore",
            "Cuddalore",
            "Pondicherry"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dash_board);

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
        spinnerdata();
       /* lv =(GridView) findViewById(R.id.list);

        lv.setAdapter(new CustomListViewAdapter(DashBoard.this, prgmNameList, prgmImages));*/


        nav = (ImageView) findViewById(R.id.nav_icon_drawer);
        mNav = new SimpleSideDrawer(this);
        mNav.setLeftBehindContentView(R.layout.activity_behind_left_simple);

        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNav.toggleLeftDrawer();
            }
        });
    }

    public void intialize() {
        spinner = (Spinner) findViewById(R.id.search_city_spinner);
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
}