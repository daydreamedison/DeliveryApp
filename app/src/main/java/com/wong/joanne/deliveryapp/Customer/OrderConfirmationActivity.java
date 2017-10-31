package com.wong.joanne.deliveryapp.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.Delivery;
import com.wong.joanne.deliveryapp.Utility.DeliveryItem;
import com.wong.joanne.deliveryapp.Utility.ReceiverInformation;

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

    Button btnAccept;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_order_confirmation_layout);

        Intent intent = this.getIntent();
        DeliveryItem item = (DeliveryItem) intent.getSerializableExtra("item");
        ReceiverInformation receiver = (ReceiverInformation) intent.getSerializableExtra("receiver");
        ReceiverInformation sender = (ReceiverInformation) intent.getSerializableExtra("sender");

        initView();

        getDeliveryDetail(sender, receiver, item);
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

    private void getDeliveryDetail(ReceiverInformation sender, ReceiverInformation receiver, DeliveryItem item){
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
                gotoSuccessPage();
            }
        });
    }

    private void gotoSuccessPage(){
        startActivity(new Intent(OrderConfirmationActivity.this, OrderSuccessPage.class));
        finish();
    }

}
