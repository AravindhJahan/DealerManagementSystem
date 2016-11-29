package com.falconnect.dealermanagementsystem;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.falconnect.dealermanagementsystem.Adapter.CustomAdapter;
import com.falconnect.dealermanagementsystem.Model.DataModel;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    private boolean mVisible;
    Context context;

    ListView listView;

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView_search;
    private static ArrayList<DataModel> data;

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
        recyclerView_search.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this, LinearLayoutManager.HORIZONTAL, false));

        //Footer List View Get Data
        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.id_[i],
                    MyData.drawableArray[i]
            ));
        }
        //Footer List View Adapter
        adapter = new CustomAdapter(data);
        recyclerView_search.setAdapter(adapter);
        //End Of Footer List View

        //Search List ListView ID
        listView = (ListView) findViewById(R.id.list_view);


    }

}