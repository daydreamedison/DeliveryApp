package com.wong.joanne.deliveryapp.Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        vendorNameTextView.setText(vendorList.get(position).name);
        if(vendorList.get(position).price.equals("0.00")){
            vendorPriceTextView.setText( "Services Unavailable.");
        }else {
            vendorPriceTextView.setText("RM " + vendorList.get(position).price);
        }
        if(vendorList.get(position).name.toLowerCase().equals("delivery app"))
            deliveryWorkingDayTextView.setText("1 working day");
        else
            deliveryWorkingDayTextView.setText("3 to 5 working days");

        return rowView;
    }
}
