package com.wong.joanne.deliveryapp.Driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.Delivery;

/**
 * Created by Sam on 10/21/2017.
 */

public class DeliveryDetailActivity extends AppCompatActivity{

    TextView description;
    TextView sendTo;
    TextView sendFrom ;
    TextView price;
    TextView senderName;
    TextView senderContact;
    TextView receiverName;
    TextView receiverContact;

    //Item Section
    TableRow luggageSec;
    TextView luggage;
    TableRow luggageWeightSec;
    TextView luggageWeight;

    TableRow parcelSec;
    TextView parcel;
    TableRow parcelWeightSec;
    TextView parcelWeight;

    TableRow docSection ;
    TextView doc;
    TableRow docWeightSection;
    TextView docWeight;

    Button btnAccept;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_delivery_detail_layout);

        Intent intent = this.getIntent();
        Delivery item = (Delivery) intent.getSerializableExtra("item");
        initView();
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptDelivery();
            }
        });
        getDeliveryDetail(item);
    }

    private void acceptDelivery(){

    }

    private void getDeliveryDetail(Delivery item){
        description.setText(item.getDeliveryItem().ItemDescription);
        sendTo.setText(item.getDeliveryItem().ItemDescription);
        sendFrom.setText(item.getDeliveryItem().ItemDescription);
        price.setText(item.getDeliveryItem().ItemDescription);
        senderName.setText(item.getDeliveryItem().ItemDescription);
        senderContact.setText(item.getDeliveryItem().ItemDescription);
        receiverName.setText(item.getDeliveryItem().ItemDescription);
        receiverContact.setText(item.getDeliveryItem().ItemDescription);

        if(item.getDeliveryItem().ItemType == "Document") {
            docSection.setVisibility(View.VISIBLE);
            doc.setText(item.getDeliveryItem().ItemWeight);
        }

        if(item.getDeliveryItem().ItemType == "Parcel") {
            parcelSec.setVisibility(View.VISIBLE);
            parcel.setText(item.getDeliveryItem().ItemWeight);
        }
    }

    private void initView(){
        description = findViewById(R.id.delivery_description);
        sendTo = findViewById(R.id.delivery_send_to_location);
        sendFrom = findViewById(R.id.delivery_send_from_location);
        price = findViewById(R.id.delivery_offered_price);
        senderName = findViewById(R.id.delivery_sender_name);
        senderContact = findViewById(R.id.delivery_sender_contact);
        receiverName = findViewById(R.id.delivery_receiver_name);
        receiverContact = findViewById(R.id.delivery_receiver_contact);

        //Item Section
        luggageSec = findViewById(R.id.luggage_quantity_section);
        luggage = findViewById(R.id.delivery_luggage);
        luggageWeightSec = findViewById(R.id.luggage_weight_section);
        luggageWeight = findViewById(R.id.delivery_weight);

        parcelSec = findViewById(R.id.parcel_quantity_section);
        parcel = findViewById(R.id.delivery_parcel);
        parcelWeightSec = findViewById(R.id.parcel_weight_section);
        parcelWeight = findViewById(R.id.parcel_weight);

        docSection = findViewById(R.id.document_quantity_section);
        doc = findViewById(R.id.document_luggage);
        docWeightSection = findViewById(R.id.document_weight_section);
        docWeight = findViewById(R.id.document_weight);

        btnAccept = findViewById(R.id.button_accept);
    }
}
