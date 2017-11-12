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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.FirebaseDelivery;

/**
 * Created by Sam on 11/5/2017.
 */

public class DeliveringActivity extends AppCompatActivity {

    FirebaseDelivery item;
    String key;
    TextView receivername;
    TextView receiverContactNumber;
    TextView receiverAddress;
    Button pickedUpButton;
    EditText OTPtxt;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_receiver_address_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        item = (FirebaseDelivery) intent.getSerializableExtra("item");
        key = (String) intent.getStringExtra("key");

        initView();
    }

    private void initView(){
        receivername = (TextView) findViewById(R.id.receiver_name);
        receiverContactNumber = (TextView) findViewById(R.id.receiver_address);
        receiverAddress = (TextView) findViewById(R.id.receiver_contact_number);
        OTPtxt = (EditText) findViewById(R.id.edit_OTP);

        pickedUpButton = (Button) findViewById(R.id.btn_item_delivered);
        pickedUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyOTP();
            }
        });

        receivername.setText(item.Receiver.Name);
        receiverContactNumber.setText(item.Receiver.ContactNumber);
        receiverAddress.setText(item.Receiver.Address);
    }

    private void verifyOTP(){
        String OTP = OTPtxt.getText().toString().trim();
        String itemOTP = item.OTP.trim();

        if(OTP.equals(itemOTP)){
            delivered();
        }
        else{
            Toast.makeText(getBaseContext(), "Wrong OTP code! Try Again.", Toast.LENGTH_LONG).show();
        }
    }

    private void delivered(){
        try{
            FirebaseDelivery fbItem = new FirebaseDelivery();
            fbItem.DeliveryItem = item.DeliveryItem;
            fbItem.Receiver = item.Receiver;
            fbItem.Sender = item.Sender;
            fbItem.OTP = item.OTP;
            fbItem.Driver = item.Driver;
            fbItem.Status = "Completed";
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PendingDeliveryList");
            databaseReference.child(key).setValue(fbItem);

            backMainActivity();
        }
        catch(Exception ex){
            Toast.makeText(getBaseContext(), "Opps! Something went wrong!", Toast.LENGTH_LONG).show();
        }
    }

    private void backMainActivity(){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch(i){
                    case DialogInterface.BUTTON_POSITIVE:
                        startActivity(new Intent(DeliveringActivity.this, DriverMainActivity.class));
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        builder.setMessage("You just completed a delivery service!")
                .setPositiveButton("Done", dialogClickListener).show();

    }
}
