package com.wong.joanne.deliveryapp.Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.VendorPriceRate;

import java.util.List;

/**
 * Created by Sam on 11/1/2017.
 */

public class VendorListViewAdapter extends ArrayAdapter<VendorPriceRate> {
    private final Context context;
    private List<VendorPriceRate> vendorList;

    public VendorListViewAdapter(Context context, List<VendorPriceRate> vendorList){
        super(context, R.layout.customer_vendors_list_item, vendorList);

        this.context = context;
        this.vendorList = vendorList;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.customer_vendors_list_item, parent, false);

        TextView vendorNameTextView = (TextView) rowView.findViewById(R.id.vendor_name);
        TextView vendorPriceTextView = (TextView) rowView.findViewById(R.id.vendor_price_rate);
        TextView deliveryWorkingDayTextView = (TextView) rowView.findViewById(R.id.delivery_working_day);
        ImageView bookNowIcon = (ImageView) rowView.findViewById(R.id.booknow_icon);

        vendorNameTextView.setText(vendorList.get(position).name);
        if(vendorList.get(position).price.equals("0.00")){
            vendorPriceTextView.setText( "Services Unavailable.");
        }else {
            vendorPriceTextView.setText("RM " + vendorList.get(position).price);
        }
        if(vendorList.get(position).name.toLowerCase().equals("parcel 2 go")) {
            deliveryWorkingDayTextView.setText("1 working day");
            bookNowIcon.setVisibility(View.VISIBLE);
        }
        else
            deliveryWorkingDayTextView.setText("1 to 2 working days");

        return rowView;
    }
}
