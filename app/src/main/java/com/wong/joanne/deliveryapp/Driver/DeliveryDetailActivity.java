package com.wong.joanne.deliveryapp.Driver;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wong.joanne.deliveryapp.Customer.CustomerMainActivity;
import com.wong.joanne.deliveryapp.Customer.OrderSuccessPage;
import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.Delivery;
import com.wong.joanne.deliveryapp.Utility.DeliveryFirebaseModel;
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
    DeliveryFirebaseModel item;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_delivery_detail_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        item = (DeliveryFirebaseModel) intent.getSerializableExtra("item");
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

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch(i){
                    case DialogInterface.BUTTON_POSITIVE:
                        updateFirebase();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        builder.setMessage("Are you sure?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void updateFirebase(){
        try {
            FirebaseDelivery fbItem = new FirebaseDelivery();
            fbItem.DeliveryItem = item.DeliveryItem;
            fbItem.Receiver = item.Receiver;
            fbItem.Sender = item.Sender;
            fbItem.OTP = item.OTP;
            fbItem.Driver = "Sam Driver";
            fbItem.Status = "Accepted by Driver";
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PendingDeliveryList");
            databaseReference.child(item.Key).setValue(fbItem);

            gotoItemPickupPage(fbItem, item.Key);
            Toast.makeText(getBaseContext(), "Success!", Toast.LENGTH_LONG).show();
        }
        catch(Exception ex){
            Toast.makeText(getBaseContext(), "Opps! Something went wrong!", Toast.LENGTH_LONG).show();
            backtoMainPage();
        }
    }

    private void gotoItemPickupPage(FirebaseDelivery fbItem, String key){
        Intent intent = new Intent(DeliveryDetailActivity.this, ItemPickUpActivity.class);
        intent.putExtra("item", fbItem);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    private void backtoMainPage(){
        startActivity(new Intent(DeliveryDetailActivity.this, DriverMainActivity.class));
        finish();
    }

    private void getDeliveryDetail(DeliveryFirebaseModel item){
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
