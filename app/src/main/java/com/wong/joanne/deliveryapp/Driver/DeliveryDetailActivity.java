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
import com.wong.joanne.deliveryapp.Utility.FirebaseDelivery;

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
    TextView deliveryType;
    TextView deliveryWeight;

    Button btnAccept;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_delivery_detail_layout);

        Intent intent = this.getIntent();
        FirebaseDelivery item = (FirebaseDelivery) intent.getSerializableExtra("item");
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

    private void getDeliveryDetail(FirebaseDelivery item){
        description.setText(item.DeliveryItem.ItemDescription);
        sendTo.setText(item.Receiver.Address);
        sendFrom.setText(item.Sender.Address);
        price.setText(item.DeliveryItem.Price);
        senderName.setText(item.Sender.Name);
        senderContact.setText(item.Receiver.ContactNumber);
        receiverName.setText(item.Receiver.Name);
        receiverContact.setText(item.Receiver.ContactNumber);
        deliveryType.setText(item.DeliveryItem.ItemType);
        deliveryWeight.setText(item.DeliveryItem.ItemWeight);
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
        deliveryType = findViewById(R.id.delivery_type);
        deliveryWeight = findViewById(R.id.delivery_weight);


        btnAccept = findViewById(R.id.button_accept);
    }
}
