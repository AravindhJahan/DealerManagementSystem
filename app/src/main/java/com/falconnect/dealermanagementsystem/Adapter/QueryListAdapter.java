package com.falconnect.dealermanagementsystem.Adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.falconnect.dealermanagementsystem.Constant;
import com.falconnect.dealermanagementsystem.FontAdapter.RoundImageTransform;
import com.falconnect.dealermanagementsystem.Model.QueryListModel;
import com.falconnect.dealermanagementsystem.Model.SingleProductModel;
import com.falconnect.dealermanagementsystem.R;
import com.falconnect.dealermanagementsystem.ServiceHandler;
import com.falconnect.dealermanagementsystem.SharedPreference.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class QueryListAdapter extends ArrayAdapter<QueryListModel> {

    List<QueryListModel> products;
    private Context context;

    ViewHolder holder;

    SessionManager sessionManager = new SessionManager(getContext());
    HashMap<String, String> user;

    public QueryListAdapter(Context context, List<QueryListModel> products) {
        super(context, R.layout.queries_list_single, products);
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public QueryListModel getItem(int position) {
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
            convertView = inflater.inflate(R.layout.queries_list_single, null);

            holder = new ViewHolder();

            holder.image1 = (ImageView) convertView.findViewById(R.id.queryimage1);
            holder.image2 = (ImageView) convertView.findViewById(R.id.queryimage2);
            holder.name = (TextView) convertView.findViewById(R.id.query_owner_name);
            holder.car_name = (TextView) convertView.findViewById(R.id.query_car_name);
            holder.car_message = (TextView) convertView.findViewById(R.id.query_car_details);
            holder.car_date = (TextView) convertView.findViewById(R.id.query_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final QueryListModel queryListModel = getItem(position);

        user = sessionManager.getUserDetails();

        String userimage = user.get("dealer_img");

        Glide.with(context)
                .load(queryListModel.getImage1())
                .transform(new RoundImageTransform(getContext()))
                .placeholder(R.drawable.car_iamge)
                .into(holder.image1);

        Glide.with(context)
                .load(userimage)
                .transform(new RoundImageTransform(getContext()))
                .placeholder(R.drawable.default_avatar)
                .into(holder.image2);

        holder.name.setText(queryListModel.getOwner_name());
        holder.car_name.setText(queryListModel.getCar_name());
        holder.car_message.setText(queryListModel.getCar_details());
        holder.car_date.setText(queryListModel.getDate_time());

        return convertView;
    }


    private class ViewHolder {

        ImageView image1 , image2;
        TextView name, car_name, car_message, car_date;
    }

}