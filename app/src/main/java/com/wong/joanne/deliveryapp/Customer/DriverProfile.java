package com.wong.joanne.deliveryapp.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.LoginUser;

/**
 * Created by Sam on 11/19/2017.
 */

public class DriverProfile extends AppCompatActivity {

    ImageView driverImage;
    TextView driverNameTxt;
    TextView driverContactNumberTxt;
    TextView driverEmailTxt;

    DatabaseReference userRef;
    private ValueEventListener valueEventListener;
    LoginUser driverProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_delivery_driver_detail_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        driverProfile = (LoginUser) intent.getSerializableExtra("driver");

        driverImage = (ImageView) findViewById(R.id.driver_image);
        driverNameTxt = (TextView) findViewById(R.id.driver_name);
        driverContactNumberTxt = (TextView) findViewById(R.id.driver_contact_number);
        driverEmailTxt = (TextView) findViewById(R.id.driver_email);


        driverNameTxt.setText(driverProfile.name);
        driverEmailTxt.setText(driverProfile.email);
        driverContactNumberTxt.setText(driverProfile.contactnumber);

        if(driverProfile.name.toLowerCase().equals("joanne")){
            driverImage.setImageDrawable(getResources().getDrawable(R.drawable.joanne));
        }
        else if(driverProfile.name.toLowerCase().equals("ben")){
            driverImage.setImageDrawable(getResources().getDrawable(R.drawable.ben));
        }
        else if(driverProfile.name.toLowerCase().equals("nikki")){
            driverImage.setImageDrawable(getResources().getDrawable(R.drawable.nikki));
        }
        else
        {
            driverImage.setImageDrawable(getResources().getDrawable(R.drawable.blank_driver));
        }
    }

}
