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

import com.falconnect.dealermanagementsystem.Model.ApplyFundingListModel;
import com.falconnect.dealermanagementsystem.Model.LoanModel;
import com.falconnect.dealermanagementsystem.R;

import java.util.List;

public class LoanListAdapter extends ArrayAdapter<LoanModel> {

    List<LoanModel> loanModels;
    private Context context;
    ViewHolder holder;

    public LoanListAdapter(Context context, List<LoanModel> loanModels) {
        super(context, R.layout.loan_single_item, loanModels);
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

            holder.loan_list_image = (ImageView) convertView.findViewById(R.id.loan_list_image);
            holder.loan_cust_name = (TextView) convertView.findViewById(R.id.loan_cust_name);
            holder.loan_cust_phonenum = (TextView) convertView.findViewById(R.id.loan_cust_phonenum);
            holder.loan_cust_email = (TextView) convertView.findViewById(R.id.loan_cust_email);
            holder.loan_cust_personal = (TextView) convertView.findViewById(R.id.loan_cust_personal);
            holder.loan_cust_approved = (TextView) convertView.findViewById(R.id.loan_cust_approved);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        final LoanModel loanModel = getItem(position);

        holder.loan_cust_name.setText(loanModel.getCustname());
        holder.loan_cust_phonenum.setText(loanModel.getCustomermobileno());
        holder.loan_cust_email.setText(loanModel.getAmount());
        holder.loan_cust_personal.setText(loanModel.getDate());

        if(holder.loan_cust_approved.getText().toString().equals("0"))
        {
            holder.loan_cust_approved.setText("PENDING");
            holder.loan_cust_approved.setTextColor(Color.RED);
        }
        else if(holder.loan_cust_approved.getText().toString().equals("1"))
        {
            holder.loan_cust_approved.setText("INPROGRESS");
            holder.loan_cust_approved.setTextColor(Color.YELLOW);
        }
        else if(holder.loan_cust_approved.getText().toString().equals("2"))
        {
            holder.loan_cust_approved.setText("COMPLETED");
            holder.loan_cust_approved.setTextColor(Color.GREEN);
        }
        else if(holder.loan_cust_approved.getText().toString().equals("3"))
        {
            holder.loan_cust_approved.setText("DISMISS");
            holder.loan_cust_approved.setTextColor(Color.RED);
        }
        else if(holder.loan_cust_approved.getText().toString().equals("4"))
        {
            holder.loan_cust_approved.setText("REVOKE");
            holder.loan_cust_approved.setTextColor(Color.RED);
        }
        else {

        }



        return convertView;
    }

    private class ViewHolder {
        ImageView loan_list_image;
        TextView loan_cust_name, loan_cust_phonenum, loan_cust_email, loan_cust_personal;
        TextView loan_cust_approved;
    }

}