package com.falconnect.dealermanagementsystem.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import com.falconnect.dealermanagementsystem.Constant;
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

public class ProductListAdapter extends ArrayAdapter<SingleProductModel> {

    List<SingleProductModel> products;
    private Context context;
    public MyBounceInterpolator interpolator;
    private boolean clicked = false;
    SingleProductModel product;
    SessionManager sessionManager = new SessionManager(getContext());
    HashMap<String, String> user;
    String user_id;
    String result, message;
    public ArrayList<HashMap<String, String>> savecarList;
    HashMap<String, String> savemap;
    ViewHolder holder;

    public ProductListAdapter(Context context, List<SingleProductModel> products) {
        super(context, R.layout.search_list_single_item, products);
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public SingleProductModel getItem(int position) {
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
            convertView = inflater.inflate(R.layout.search_list_single_item, null);

            holder = new ViewHolder();

            holder.car_image = (ImageView) convertView.findViewById(R.id.car_image);
            holder.car_name = (TextView) convertView.findViewById(R.id.car_name);
            holder.car_rate = (TextView) convertView.findViewById(R.id.car_rate);
            holder.car_date = (TextView) convertView.findViewById(R.id.posted_day);
            holder.car_fuel = (TextView) convertView.findViewById(R.id.car_details_liquid);
            holder.car_kms = (TextView) convertView.findViewById(R.id.car_details_kms);
            holder.car_address = (TextView) convertView.findViewById(R.id.car_address);
            holder.car_year = (TextView) convertView.findViewById(R.id.car_details_year);
            holder.car_owner = (TextView) convertView.findViewById(R.id.car_details_owner);
            holder.no_of_images = (TextView) convertView.findViewById(R.id.noofimages);
            holder.favoriteImg = (ImageView) convertView.findViewById(R.id.chola);
            holder.saved_car = (ImageView) convertView.findViewById(R.id.car_saved);
            holder.bid_image = (ImageView) convertView.findViewById(R.id.like);
            holder.alert_image = (ImageView) convertView.findViewById(R.id.car_alert);



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        product = (SingleProductModel) getItem(position);

        Glide.with(getContext())
                .load(product.getImagelinks())
                .placeholder(R.drawable.carimageplaceholder)
                .into(holder.car_image);

        holder.car_name.setText(product.getMake());
        holder.car_rate.setText(product.getPrice());
        holder.car_date.setText(product.getDaysstmt());
        holder.car_kms.setText(product.getKilometer_run());
        holder.car_fuel.setText(product.getFuel_type());
        holder.car_year.setText(product.getRegistration_year());
        holder.car_owner.setText(product.getOwner_type());
        holder.car_address.setText(product.getCar_locality());
        holder.no_of_images.setText(product.getNo_images());

        Glide.with(getContext())
                .load(product.getSite_image())
                .into(holder.favoriteImg);

        if (product.getSaved_car().equals("0")) {
            Glide.with(getContext())
                    .load(R.drawable.like_white)
                    .into(holder.saved_car);
        } else {
            Glide.with(getContext())
                    .load(R.drawable.like_red)
                    .into(holder.saved_car);
        }

        if (product.getNotify_car().equals("0")) {
            Glide.with(getContext())
                    .load(R.drawable.alert_white)
                    .into(holder.alert_image);
        } else {
            Glide.with(getContext())
                    .load(R.drawable.alert_red)
                    .into(holder.alert_image);
        }

        Glide.with(getContext()).load(product.getBid_image()).into(holder.bid_image);

        holder.saved_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clicked) {
                    new Favbutton().execute();
                } else {
                    new Favbutton().execute();
                }
                clicked = true;
             }
        });

        return convertView;
    }

    class Favbutton extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            ServiceHandler sh = new ServiceHandler();
            user = sessionManager.getUserDetails();
            user_id = user.get("user_id");
            String fav_url = Constant.SAVE_CAR_API
                    + "session_user_id=" + user_id
                    + "&carid=" + product.getCar_id();
            String json = sh.makeServiceCall(fav_url, ServiceHandler.POST);

            if (json != null) {

                savecarList = new ArrayList<>();
                savemap = new HashMap<String, String>();

                try {
                    JSONObject obj = new JSONObject(json);

                    for (int i = 0; i <= obj.length(); i++) {

                        result = obj.getString("Result");
                        message = obj.getString("message");
                        savemap.put("REsult", result);
                        savemap.put("Message", message);
                        savecarList.add(savemap);

                    }
                } catch (final JSONException e) {
                    Toast.makeText(getContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (savemap.get("REsult").equals("3")) {
                Glide.with(getContext())
                        .load(R.drawable.like_red)
                        .into(holder.saved_car);
                //Animation
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);
                holder.saved_car.startAnimation(myAnim);
                Toast.makeText(getContext(), savemap.get("Message"), Toast.LENGTH_SHORT).show();
            } else {

            }
        }
    }

    private class ViewHolder {
        ImageView car_image;
        TextView car_name;
        TextView car_rate, car_date;
        TextView car_kms, car_fuel, car_year, car_owner, car_address, no_of_images;
        ImageView favoriteImg;
        ImageView saved_car;
        ImageView bid_image, alert_image;
    }

}