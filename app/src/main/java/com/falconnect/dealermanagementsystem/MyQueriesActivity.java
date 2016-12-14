package com.falconnect.dealermanagementsystem;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.falconnect.dealermanagementsystem.Adapter.CustomAdapter;
import com.falconnect.dealermanagementsystem.Adapter.QueryListAdapter;
import com.falconnect.dealermanagementsystem.Model.DataModel;
import com.falconnect.dealermanagementsystem.Model.SingleProductModel;

import java.util.ArrayList;

public class MyQueriesActivity extends AppCompatActivity {

    private boolean mVisible;

    ListView list_queries;


    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView queriesrecyclerView;
    private static ArrayList<DataModel> data;


    String[] car_name = {
            "Audi",
            "Hyundai",
            "Omini"
    };

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
        queriesrecyclerView.setHasFixedSize(true);
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

        list_queries = (ListView) findViewById(R.id.queries_list);

    }
}
