package com.falconnect.dealermanagementsystem.Adapter;


import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.falconnect.dealermanagementsystem.Model.SingleProductModel;
import com.falconnect.dealermanagementsystem.R;

import java.util.List;

public class ProductListAdapter extends ArrayAdapter<SingleProductModel> {

    List<SingleProductModel> products;
    private Context context;


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
        ViewHolder holder = null;
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


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SingleProductModel product = (SingleProductModel) getItem(position);

       /* Glide.with(getContext())
                .load(product.getImage())
                .placeholder(R.drawable.image1)
                .into(holder.car_image);*/

        holder.car_image.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.car_name.setText(product.getName());
        holder.car_rate.setText(product.getRate());
        holder.car_date.setText(product.getPosted_date());
        holder.car_kms.setText(product.getKms());
        holder.car_fuel.setText(product.getFuel());
        holder.car_year.setText(product.getYear());
        holder.car_owner.setText(product.getOwner());
        holder.car_address.setText(product.getAddress());
        holder.no_of_images.setText(product.getNum_of_image());

        Glide.with(getContext()).load(product.getSite_image()).placeholder(R.drawable.chola).into(holder.favoriteImg);

        return convertView;
    }


    private class ViewHolder {
        ImageView car_image;
        TextView car_name;
        TextView car_rate, car_date;
        TextView car_kms, car_fuel, car_year, car_owner, car_address, no_of_images;
        ImageView favoriteImg;

    }

}
