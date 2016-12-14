package com.falconnect.dealermanagementsystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.falconnect.dealermanagementsystem.BidsPostedActivity;
import com.falconnect.dealermanagementsystem.Constant;
import com.falconnect.dealermanagementsystem.DashBoard;
import com.falconnect.dealermanagementsystem.FundingActivity;
import com.falconnect.dealermanagementsystem.Model.DataModel;
import com.falconnect.dealermanagementsystem.MyQueriesActivity;
import com.falconnect.dealermanagementsystem.R;
import com.falconnect.dealermanagementsystem.SavedCarActivity;
import com.falconnect.dealermanagementsystem.SearchResultActivity;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataSet;
    private Context mContext;

    public CustomAdapter(Context context, ArrayList<DataModel> data) {
        this.mContext = context;
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_list_view_single, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        final DataModel singleItem = dataSet.get(listPosition);

        holder.textViewName.setText(singleItem.getName());

        holder.imageViewIcon.setImageResource(singleItem.getImage());

        holder.category_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (singleItem.getId() == 0) {
                    Toast.makeText(mContext, "Selected :" + singleItem.getName(), Toast.LENGTH_SHORT).show();
                } else if (singleItem.getId() == 1) {
                    Intent intent = new Intent(mContext.getApplicationContext(), SavedCarActivity.class);
                    mContext.startActivity(intent);
                    Toast.makeText(mContext, "Selected :" + singleItem.getName(), Toast.LENGTH_SHORT).show();
                } else if (singleItem.getId() == 2) {
                    Intent intent = new Intent(mContext.getApplicationContext(), MyQueriesActivity.class);
                    mContext.startActivity(intent);
                    Toast.makeText(mContext, "Selected :" + singleItem.getName(), Toast.LENGTH_SHORT).show();
                } else if (singleItem.getId() == 3) {
                    Intent intent = new Intent(mContext.getApplicationContext(), BidsPostedActivity.class);
                    mContext.startActivity(intent);
                    Toast.makeText(mContext, "Selected :" + singleItem.getName(), Toast.LENGTH_SHORT).show();
                } else if (singleItem.getId() == 4) {
                    Intent intent = new Intent(mContext.getApplicationContext(), FundingActivity.class);
                    mContext.startActivity(intent);
                    Toast.makeText(mContext, "Selected :" + singleItem.getName(), Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return (null != dataSet ? dataSet.size() : 0);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView imageViewIcon;
        LinearLayout category_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.mtitle);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.image_footer);
            this.category_item = (LinearLayout) itemView.findViewById(R.id.category_item);
        }
    }


}