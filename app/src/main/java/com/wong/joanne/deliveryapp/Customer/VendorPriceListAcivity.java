package com.wong.joanne.deliveryapp.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;
import com.wong.joanne.deliveryapp.CalculatorHelper.ConvertToVendorPriceRateModel;
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
    List<VendorPriceRate> vendorList = new ArrayList<>();
    ReceiverInformation receiverInformation;
    ReceiverInformation senderInformation ;
    DeliveryItem deliveryItem ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_vendors_list_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        receiverInformation = (ReceiverInformation) intent.getSerializableExtra("receiver");
        senderInformation = (ReceiverInformation) intent.getSerializableExtra("sender");
        deliveryItem = (DeliveryItem) intent.getSerializableExtra("deliveryItem");

        //add price list
        addDeliveryAppPrice(deliveryItem);
        vendorList.addAll(getAllVendorPriceList(receiverInformation, senderInformation, deliveryItem));

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
        Intent intent = new Intent(VendorPriceListAcivity.this, OrderConfirmationActivity.class);
        intent.putExtra("sender", senderInformation);
        intent.putExtra("receiver", receiverInformation);
        intent.putExtra("item", deliveryItem);
        VendorPriceListAcivity.this.startActivity(intent);
    }

    private List<VendorPriceRate> getAllVendorPriceList(ReceiverInformation receiver, ReceiverInformation sender, DeliveryItem Item){

        boolean isSameCity = false;
        if(receiver.City.equals(sender.City)){
            isSameCity = true;
        }
        List<VendorPriceRate> allVendorPriceList = new ArrayList<>();
        HashMap<String, String> availableVendorList =  new VendorsList().getVendorLis();
        for(Map.Entry<String, String> vendor: availableVendorList.entrySet()){

            ConvertToVendorPriceRateModel converter = new ConvertToVendorPriceRateModel(isSameCity, Item.ItemWeight, Item.ItemType);
            allVendorPriceList.add(converter.convert(vendor.getKey()));
        }

        return allVendorPriceList;
    }

    private void addDeliveryAppPrice(DeliveryItem item){
        VendorPriceRate deliveryAppPrice = new VendorPriceRate();
        deliveryAppPrice.name = "Delivery App";
        deliveryAppPrice.workingDaysWithSameCity = "1 working day";
        deliveryAppPrice.workingDaysWithNotSameCity = "3 working days";
        if(item.ItemType.toLowerCase().equals("document")){
            deliveryAppPrice.parcelPrice = "";
            deliveryAppPrice.documentPrice = item.Price;
        }else
        {
            deliveryAppPrice.parcelPrice = item.Price;
            deliveryAppPrice.documentPrice = "";
        }
        vendorList.add(deliveryAppPrice);
    }


    private void createDeliveryOrder()
    {

    }

    private void backMainPage()
    {

    }


}
