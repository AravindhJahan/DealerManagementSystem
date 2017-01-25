package com.falconnect.dealermanagementsystem.Adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.falconnect.dealermanagementsystem.Model.ApplyFundingListModel;
import com.falconnect.dealermanagementsystem.Model.LoanModel;
import com.falconnect.dealermanagementsystem.Model.MyPostingListModel;
import com.falconnect.dealermanagementsystem.R;

import java.util.List;

public class MyPostingListAdapter extends ArrayAdapter<MyPostingListModel> {

    List<MyPostingListModel> loanModels;
    private Context context;
    ViewHolder holder;

    public MyPostingListAdapter(Context context, List<MyPostingListModel> loanModels) {
        super(context, R.layout.my_posting_single_item, loanModels);
        this.context = context;
        this.loanModels = loanModels;
    }

    @Override
    public int getCount() {
        return loanModels.size();
    }

    @Override
    public MyPostingListModel getItem(int position) {
        return loanModels.get(position);
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
            convertView = inflater.inflate(R.layout.my_posting_single_item, null);

            holder = new ViewHolder();

            holder.year = (TextView) convertView.findViewById(R.id.myposting_year1);
            holder.price = (TextView) convertView.findViewById(R.id.myposting_rate1);
            holder.kms = (TextView) convertView.findViewById(R.id.mypostion_kms1);
            holder.owner = (TextView) convertView.findViewById(R.id.myposting_owner1);
            holder.fuel_type = (TextView) convertView.findViewById(R.id.myposting_petrol1);
            holder.mongopushdate = (TextView) convertView.findViewById(R.id.myposting_date);
            holder.make = (TextView) convertView.findViewById(R.id.myposting_name1);

            holder.imageurl = (ImageView) convertView.findViewById(R.id.myposting_carimage1);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        final MyPostingListModel myPostingListModel = getItem(position);

        Glide.with(context)
                .load(myPostingListModel.getImageurl())
                .placeholder(R.drawable.car_iamge)
                .into(holder.imageurl);

        holder.year.setText(myPostingListModel.getYear());
        holder.price.setText(myPostingListModel.getPrice());
        holder.kms.setText(myPostingListModel.getKms());
        holder.owner.setText(myPostingListModel.getOwner());
        holder.fuel_type.setText(myPostingListModel.getFuel_type());
        holder.mongopushdate.setText(myPostingListModel.getMongopushdate());
        holder.make.setText(myPostingListModel.getMake());

        return convertView;
    }

    private class ViewHolder {
        TextView year, price, kms, owner, fuel_type, make, mongopushdate;
        ImageView imageurl;
    }

}