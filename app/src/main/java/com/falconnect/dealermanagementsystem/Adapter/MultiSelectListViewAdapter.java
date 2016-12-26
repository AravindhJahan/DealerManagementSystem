package com.falconnect.dealermanagementsystem.Adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.falconnect.dealermanagementsystem.Model.MultiSelectModel;
import com.falconnect.dealermanagementsystem.Model.SingleProductModel;
import com.falconnect.dealermanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

public class MultiSelectListViewAdapter extends ArrayAdapter<MultiSelectModel> {

    private ArrayList<MultiSelectModel> datas;
    private LayoutInflater inflator;
    private List<MultiSelectModel> products;

    public MultiSelectListViewAdapter(Activity context, List<MultiSelectModel> products) {
        super(context, R.layout.single_site_list_item, products);
        this.products = products;
        inflator = context.getLayoutInflater();
    }

    public MultiSelectModel getItem(int position) {
        return products.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = inflator.inflate(R.layout.single_site_list_item, null);
            holder = new ViewHolder();
            holder.id = (TextView)convertView.findViewById(R.id.multiselectlistid);
            holder.title = (TextView) convertView.findViewById(R.id.multiselectlisttitle);
            holder.chk = (CheckBox) convertView.findViewById(R.id.checkbox);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.id.setText(products.get(position).getId());

        holder.title.setText(products.get(position).getName());

       /* if (holder.chk.isChecked()) {
            holder.chk.setChecked(false);
        } else {
            holder.chk.setChecked(true);

        }*/

        return convertView;
    }

    static class ViewHolder {
        TextView title, id;
        CheckBox chk;

    }
}