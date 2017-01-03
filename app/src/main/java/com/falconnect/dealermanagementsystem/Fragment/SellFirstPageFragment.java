package com.falconnect.dealermanagementsystem.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.falconnect.dealermanagementsystem.Adapter.SellCustomAdapter;
import com.falconnect.dealermanagementsystem.Adapter.SellFooterCustomAdapter;
import com.falconnect.dealermanagementsystem.Model.SellFooterDataModel;
import com.falconnect.dealermanagementsystem.R;

import java.util.ArrayList;


public class SellFirstPageFragment extends Fragment {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView sellrecyclerView;
    private static ArrayList<SellDataModel> selldata;

    private static RecyclerView sellinventory_footer;
    private static ArrayList<SellFooterDataModel> sellfooterdata;
    private static RecyclerView.Adapter selladapter;

    public static String[] nameSellArray = {
            "20",
            "14",
            "01",
            "10"};


    public static Integer[] drawableSellArray = {
            R.drawable.car_iamge,
            R.drawable.car_iamge,
            R.drawable.car_iamge,
            R.drawable.car_iamge};


    public SellFirstPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first_page_sell, container, false);

        sellrecyclerView = (RecyclerView) rootView.findViewById(R.id.myinventory_recycle_content);
        sellrecyclerView.setHasFixedSize(true);
        sellrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        selldata = new ArrayList<SellDataModel>();
        for (int i = 0; i < nameSellArray.length; i++) {
            selldata.add(new SellDataModel(
                    nameSellArray[i],
                    drawableSellArray[i]
            ));
        }
        adapter = new SellCustomAdapter(getContext(), selldata);
        sellrecyclerView.setAdapter(adapter);

        sellinventory_footer = (RecyclerView) rootView.findViewById(R.id.sell_footer_recycyle);
        sellinventory_footer.setHasFixedSize(true);
        sellinventory_footer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        sellfooterdata = new ArrayList<SellFooterDataModel>();
        for (int i = 0; i < MySellData.sellnameArray.length; i++) {

            sellfooterdata.add(new SellFooterDataModel(
                    MySellData.sellnameArray[i],
                    MySellData.selldrawableArrayWhite0[i],
                    MySellData.sellid_[i]
            ));
        }
        selladapter = new SellFooterCustomAdapter(getContext(), sellfooterdata);
        sellinventory_footer.setAdapter(selladapter);

        return rootView;
    }

}