package com.falconnect.dealermanagementsystem.Adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.falconnect.dealermanagementsystem.Model.ApplyFundingListModel;
import com.falconnect.dealermanagementsystem.Model.BidsPostedListModel;
import com.falconnect.dealermanagementsystem.R;

import java.util.List;

public class ApplyFundingListAdapter extends ArrayAdapter<ApplyFundingListModel> {

    List<ApplyFundingListModel> products;
    private Context context;
    ViewHolder holder;

    public ApplyFundingListAdapter(Context context, List<ApplyFundingListModel> products) {
        super(context, R.layout.apply_fundind_single_item, products);
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public ApplyFundingListModel getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.apply_fundind_single_item, null);

            holder = new ViewHolder();

            holder.fund_name = (TextView) convertView.findViewById(R.id.funding_name);
            holder.fund_address = (TextView) convertView.findViewById(R.id.funding_address);
            holder.fund_rate = (TextView) convertView.findViewById(R.id.funding_rate);
            holder.fund_date = (TextView) convertView.findViewById(R.id.funding_date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ApplyFundingListModel applyFundingListModel = getItem(position);

        holder.fund_name.setText(applyFundingListModel.getToken());
        holder.fund_address.setText(applyFundingListModel.getContact());
        holder.fund_rate.setText(applyFundingListModel.getAmount());
        holder.fund_date.setText(applyFundingListModel.getDate());

        return convertView;
    }

    private class ViewHolder {
        TextView fund_name, fund_address, fund_rate, fund_date;
    }

}