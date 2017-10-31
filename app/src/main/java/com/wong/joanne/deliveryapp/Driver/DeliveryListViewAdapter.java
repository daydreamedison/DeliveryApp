package com.wong.joanne.deliveryapp.Driver;

import android.content.Context;
import android.util.EventLogTags;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.Delivery;
import com.wong.joanne.deliveryapp.Utility.DeliveryFirebaseModel;
import com.wong.joanne.deliveryapp.Utility.DeliveryItem;
import com.wong.joanne.deliveryapp.Utility.ReceiverInformation;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Sam on 10/21/2017.
 */

public class DeliveryListViewAdapter extends ArrayAdapter<DeliveryFirebaseModel> {

    private final Context context;
    private List<DeliveryFirebaseModel> deliveryList;
    private LayoutInflater inflater;

    public DeliveryListViewAdapter(Context context, List<DeliveryFirebaseModel> deliveryList){
        super(context, R.layout.driver_delivery_list_item , deliveryList);

        this.context = context;
        this.deliveryList = deliveryList;
    }

    public int getCount() {
        return (deliveryList == null) ? 0 : deliveryList.size();
    }

    public View getView(int position, View convertView, ViewGroup viewGroup){
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView = inflater.inflate(R.layout.driver_delivery_list_item, viewGroup, false);
        }


        if(deliveryList.size() > 0 ) {
            TextView DeliveryDescription = (TextView) convertView.findViewById(R.id.delivery_desscription);
            TextView DeliveryOfferedPrice = (TextView) convertView.findViewById(R.id.delivery_offered_price);
            TextView DeliveryLocation = (TextView) convertView.findViewById(R.id.delivery_location);

            DeliveryFirebaseModel delivery = deliveryList.get(position);
            DeliveryItem deliveryItem = delivery.DeliveryItem;
            String description = deliveryItem.ItemDescription;

            DeliveryDescription.setText(deliveryList.get(position).DeliveryItem.ItemDescription);
            DeliveryOfferedPrice.setText( getTotalPrice(deliveryList.get(position).DeliveryItem));
            DeliveryLocation.setText( getLocation(deliveryList.get(position).receiverInformation));
        }else
        {
            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
        }


        return convertView;
    }

    private String getLocation(ReceiverInformation receiver){
        return receiver.Address + " "
                + receiver.City + " "
                + receiver.State;
    }

    public String getTotalPrice(DeliveryItem deliveryItem){
        return String.valueOf(deliveryItem.Price);
    }

}
