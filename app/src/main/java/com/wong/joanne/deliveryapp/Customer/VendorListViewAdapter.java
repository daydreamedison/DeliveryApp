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
        TextView documentPriceRateTextView = (TextView) rowView.findViewById(R.id.document_price_rate);
        TextView documentTxt = (TextView) rowView.findViewById(R.id.document_price_rate_txt);
        TextView parcelPriceRateTextView = (TextView) rowView.findViewById(R.id.parcel_price_rate);
        TextView parcelTxt = (TextView) rowView.findViewById(R.id.parcel_price_rate_txt);
        TextView deliveryWorkingDayTextView = (TextView) rowView.findViewById(R.id.delivery_working_day);

        vendorNameTextView.setText(vendorList.get(position).name);
        if(vendorList.get(position).parcelPrice == ""){
            documentPriceRateTextView.setText(vendorList.get(position).documentPrice);
            parcelPriceRateTextView.setVisibility(View.GONE);
            parcelTxt.setVisibility(View.GONE);
        }
        else
        {
            parcelPriceRateTextView.setText(vendorList.get(position).parcelPrice);
            documentPriceRateTextView.setVisibility(View.GONE);
            documentTxt.setVisibility(View.GONE);
        }

        deliveryWorkingDayTextView.setText(vendorList.get(position).workingDaysWithSameCity);

        return rowView;
    }
}
