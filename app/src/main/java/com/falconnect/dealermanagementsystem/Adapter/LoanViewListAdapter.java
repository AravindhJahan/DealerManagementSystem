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

import com.falconnect.dealermanagementsystem.Model.LoanModel;
import com.falconnect.dealermanagementsystem.R;

import java.util.List;

public class LoanViewListAdapter extends ArrayAdapter<LoanModel> {

    List<LoanModel> loanModels;
    private Context context;
    ViewHolder holder;

    public LoanViewListAdapter(Context context, List<LoanModel> loanModels) {
        super(context, R.layout.loanview_single_item , loanModels);
        this.context = context;
        this.loanModels = loanModels;
    }

    @Override
    public int getCount() {
        return loanModels.size();
    }

    @Override
    public LoanModel getItem(int position) {
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
            convertView = inflater.inflate(R.layout.loan_single_item, null);

            holder = new ViewHolder();

            holder.loan_apply_owner_name_text = (TextView) convertView.findViewById(R.id.loan_apply_owner_name_text);
            holder.loan_apply_owner_name = (TextView) convertView.findViewById(R.id.loan_apply_owner_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        final LoanModel loanModel = getItem(position);

        holder.loan_apply_owner_name.setText(loanModel.getCustname());
        holder.loan_apply_owner_name.setText(loanModel.getToken());
        holder.loan_apply_owner_name.setText(loanModel.getAmount());
        holder.loan_apply_owner_name.setText(loanModel.getDate());
        holder.loan_apply_owner_name.setText(loanModel.getCustomermobileno());
        holder.loan_apply_owner_name.setText(loanModel.getEmail());

        return convertView;
    }

    private class ViewHolder {
        TextView loan_apply_owner_name_text;
        TextView loan_apply_owner_name;
    }

}