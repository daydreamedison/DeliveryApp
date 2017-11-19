package com.wong.joanne.deliveryapp.Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.DeliveryFirebaseModel;
import com.wong.joanne.deliveryapp.Utility.DeliveryItem;

import java.util.List;

/**
 * Created by Sam on 11/19/2017.
 */

public class DeliveryHistoriesListViewAdapter extends ArrayAdapter<DeliveryFirebaseModel> {

    private final Context context;
    private List<DeliveryFirebaseModel> historiesList;
    private LayoutInflater inflater;

    public DeliveryHistoriesListViewAdapter(Context context, List<DeliveryFirebaseModel> historiesList){
        super(context, R.layout.customer_order_histories_list_item, historiesList);

        this.context = context;
        this.historiesList = historiesList;
    }

    public int getCount() {
        return (historiesList ==null) ? 0 : historiesList.size();
    }

    public View getView (int position, View convertView, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView = inflater.inflate(R.layout.customer_order_histories_list_item, viewGroup, false);
        }

        if(historiesList.size() > 0){
            TextView DeliveryDescription = (TextView) convertView.findViewById(R.id.delivery_desscription);
            TextView DeliveryStatus = (TextView) convertView.findViewById(R.id.delivery_status);
            TextView DeliveryDriver = (TextView) convertView.findViewById(R.id.delivery_driver);

            DeliveryFirebaseModel delivery = historiesList.get(position);
            DeliveryItem deliveryItem = delivery.DeliveryItem;

            DeliveryDescription.setText(deliveryItem.ItemDescription);
            DeliveryStatus.setText(delivery.Status );
            DeliveryDriver.setText( delivery.Driver);
        }
        else
        {
            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
        }
        return convertView;
    }
}
