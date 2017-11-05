package com.wong.joanne.deliveryapp.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.Delivery;
import com.wong.joanne.deliveryapp.Utility.DeliveryItem;
import com.wong.joanne.deliveryapp.Utility.FirebaseConstants;
import com.wong.joanne.deliveryapp.Utility.FirebaseDelivery;
import com.wong.joanne.deliveryapp.Utility.ReceiverInformation;

import java.util.Random;

/**
 * Created by Sam on 11/1/2017.
 */

public class OrderConfirmationActivity extends AppCompatActivity {

    TextView description;
    TextView sendTo;
    TextView sendFrom ;
    TextView price;
    TextView senderName;
    TextView senderContact;
    TextView receiverName;
    TextView receiverContact;

    //Item Section
    TextView itemType;
    TextView itemWeight;

    DeliveryItem item;
    ReceiverInformation receiver;
    ReceiverInformation sender;
    FirebaseDelivery fb;

    private DatabaseReference databaseReference;

    Button btnAccept;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_order_confirmation_layout);

        Intent intent = this.getIntent();
        item = (DeliveryItem) intent.getSerializableExtra("item");
        receiver = (ReceiverInformation) intent.getSerializableExtra("receiver");
        sender = (ReceiverInformation) intent.getSerializableExtra("sender");

        initView();

        getDeliveryDetail();
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
        btnAccept = findViewById(R.id.button_order_accept);

        itemWeight = findViewById(R.id.item_weight);
        itemType = findViewById(R.id.item_type);
    }

    private void getDeliveryDetail(){
        description.setText(item.ItemDescription);
        sendTo.setText(receiver.Address);
        sendFrom.setText(sender.Address);
        price.setText(item.Price);
        senderName.setText(sender.Name);
        senderContact.setText(sender.ContactNumber);
        receiverName.setText(receiver.Name);
        receiverContact.setText(receiver.ContactNumber);

        itemType.setText(item.ItemType);
        itemWeight.setText(item.ItemWeight);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(orderDeliveryNow()) {
                    gotoSuccessPage();
                }
                else{
                    backToMainPage();
                }
            }
        });
    }

    private boolean orderDeliveryNow(){
        try {
            fb = new FirebaseDelivery();
            fb.DeliveryItem = this.item;
            fb.Receiver = this.receiver;
            fb.Sender = this.sender;
            fb.Driver = "";
            fb.Status = FirebaseConstants.PendingStatus;
            fb.OTP = genOTP();

            databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference db = databaseReference.child("PendingDeliveryList");
            String key = databaseReference.child("PendingDeliveryList").push().getKey();
            db.child(key).setValue(fb);

            return true;
        }
        catch(Exception ex)
        {
            Toast.makeText(getBaseContext(), "Opps! Something went wrong.", Toast.LENGTH_LONG).show();
            System.out.println(ex);
            return false;
        }
    }

    private String genOTP(){
        Random rand = new Random();
        int n = rand.nextInt(99999) + 10000;

        return String.valueOf(n);
    }

    private void gotoSuccessPage(){
        Intent intent = new Intent(OrderConfirmationActivity.this, OrderSuccessPage.class);
        intent.putExtra("OTP", fb.OTP);
        OrderConfirmationActivity.this.startActivity(intent);
        finish();
    }

    private void backToMainPage(){
        startActivity(new Intent(OrderConfirmationActivity.this, CustomerMainActivity.class));
        finish();
    }

}
