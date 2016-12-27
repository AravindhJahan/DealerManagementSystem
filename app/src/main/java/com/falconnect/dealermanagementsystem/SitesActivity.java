package com.falconnect.dealermanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.falconnect.dealermanagementsystem.Adapter.MultiSelectionAdapter;
import com.falconnect.dealermanagementsystem.Model.Product;

import java.util.ArrayList;


public class SitesActivity extends AppCompatActivity {

    private boolean mVisible;

    private ListView sitelistView;

    private ImageView btn_apply_site;
    private ImageView btn_close_site;

    ArrayList<Product> mProducts;
    MultiSelectionAdapter<Product> mAdapter;

    ArrayList<String> list = new ArrayList<String>();

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

        sitelistView = (ListView) findViewById(R.id.sitelistview);
        Intent intent = getIntent();
        list = intent.getStringArrayListExtra("string_array");

        mProducts = (ArrayList<Product>) list.clone();

        mAdapter = new MultiSelectionAdapter<Product>(SitesActivity.this, mProducts);
        sitelistView.setAdapter(mAdapter);

        sitelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CheckBox chk = (CheckBox) view.findViewById(R.id.cbBox);

                chk.setSelected(true);

            }
        });

        btn_close_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SitesActivity.this.finish();
                Intent intent = new Intent(SitesActivity.this, DashBoard.class);
                startActivity(intent);
            }
        });

        btn_apply_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mAdapter != null) {
                    ArrayList<Product> mArrayProducts = mAdapter.getCheckedItems();
                    Toast.makeText(getApplicationContext(), "Selected Items: " + mArrayProducts.toString(), Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "Selected Items: " + mArrayProducts.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onBackPressed() {

        Toast.makeText(SitesActivity.this, "Please select any one Sites", Toast.LENGTH_SHORT).show();

    }

    public void intialize() {
        btn_apply_site = (ImageView) findViewById(R.id.apply_icon);
        btn_close_site = (ImageView) findViewById(R.id.close_icon);
    }

}

