package com.wong.joanne.deliveryapp.Driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.DeliveryFirebaseModel;
import com.wong.joanne.deliveryapp.Utility.FirebaseDelivery;

/**
 * Created by Sam on 10/21/2017.
 */

public class ItemPickUpActivity extends AppCompatActivity{

    FirebaseDelivery item;
    String key;
    TextView sendername;
    TextView senderContactNumber;
    TextView senderAddress;
    Button pickedUpButton;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_sender_address_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = this.getIntent();
        item = (FirebaseDelivery) intent.getSerializableExtra("item");
        key = (String) intent.getStringExtra("key");

        initView();
    }

    private void initView(){
        sendername = (TextView) findViewById(R.id.sender_name);
        senderAddress = (TextView) findViewById(R.id.sender_address);
        senderContactNumber = (TextView) findViewById(R.id.sender_contact_number);
        pickedUpButton = (Button) findViewById(R.id.btn_item_pickup);
        pickedUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickedUp();
            }
        });

        sendername.setText(item.Sender.Name);
        senderContactNumber.setText(item.Sender.ContactNumber);
        senderAddress.setText(item.Sender.Address);
    }

    private void pickedUp(){
        try {
            FirebaseDelivery fbItem = new FirebaseDelivery();
            fbItem.DeliveryItem = item.DeliveryItem;
            fbItem.Receiver = item.Receiver;
            fbItem.Sender = item.Sender;
            fbItem.OTP = item.OTP;
            fbItem.Driver = item.Driver;
            fbItem.Status = "Delivering";
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PendingDeliveryList");
            databaseReference.child(key).setValue(fbItem);

            gotoDeliveryPage(item, key);

            Toast.makeText(getBaseContext(), "Success!", Toast.LENGTH_LONG).show();
        }
        catch(Exception ex){
            Toast.makeText(getBaseContext(), "Opps! Something went wrong!", Toast.LENGTH_LONG).show();
        }
    }

    private void gotoDeliveryPage(FirebaseDelivery fbItem, String key){
        Intent intent = new Intent(ItemPickUpActivity.this, DeliveringActivity.class);
        intent.putExtra("item", fbItem);
        intent.putExtra("key", key);
        startActivity(intent);
    }
}
