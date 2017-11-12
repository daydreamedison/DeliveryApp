package com.wong.joanne.deliveryapp.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;
import com.wong.joanne.deliveryapp.CalculatorHelper.PosLajuPrice;
import com.wong.joanne.deliveryapp.CalculatorHelper.PosLajuPriceRateModel;
import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.DeliveryItem;
import com.wong.joanne.deliveryapp.Utility.ReceiverInformation;
import com.wong.joanne.deliveryapp.Utility.VendorPriceRate;
import com.wong.joanne.deliveryapp.Utility.VendorsList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sam on 10/20/2017.
 */

public class VendorPriceListAcivity extends AppCompatActivity{

    private static final String ns = null;
    private Button btnContinue;
    List<VendorPriceRate> vendorList;
    ReceiverInformation receiverInformation;
    ReceiverInformation senderInformation ;
    DeliveryItem deliveryItem ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_vendors_list_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get vendor list from previous activity
        Intent intent = this.getIntent();
        vendorList = (ArrayList<VendorPriceRate>)getIntent().getSerializableExtra("vendorList");
        receiverInformation = (ReceiverInformation) getIntent().getSerializableExtra("receiver");
        senderInformation = (ReceiverInformation) getIntent().getSerializableExtra("sender");
        deliveryItem = (DeliveryItem) getIntent().getSerializableExtra("deliveryitem");

        //initialise list view
        final VendorListViewAdapter adapter = new VendorListViewAdapter(this.getApplicationContext(),
                vendorList);
        ListView listView = (ListView) this.findViewById(R.id.confirmation_item_list);
        listView.setAdapter(adapter);

        btnContinue = (Button) findViewById(R.id.button_continue_placeorder);
        btnContinue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                gotoConfirmationPage(receiverInformation, senderInformation, deliveryItem);
            }

        });

    }

    private void gotoConfirmationPage(ReceiverInformation receiverInformation, ReceiverInformation senderInformation, DeliveryItem deliveryItem){

        for(VendorPriceRate vendorPriceRate: vendorList){
            if(vendorPriceRate.name.toLowerCase().equals("parcel 2 go")){
                deliveryItem.Price = String.valueOf(vendorPriceRate.price);
            }
        }

        Intent intent = new Intent(VendorPriceListAcivity.this, OrderConfirmationActivity.class);
        intent.putExtra("sender", senderInformation);
        intent.putExtra("receiver", receiverInformation);
        intent.putExtra("item", deliveryItem);
        VendorPriceListAcivity.this.startActivity(intent);
    }
}
