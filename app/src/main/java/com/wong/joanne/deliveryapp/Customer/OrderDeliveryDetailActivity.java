package com.wong.joanne.deliveryapp.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.DeliveryFirebaseModel;
import com.wong.joanne.deliveryapp.Utility.LoginUser;

/**
 * Created by Sam on 11/20/2017.
 */

public class OrderDeliveryDetailActivity extends AppCompatActivity {

    TextView description;
    TextView sendTo;
    TextView sendFrom ;
    TextView price;
    TextView driverName;
    ImageView driverImage;

    //Item Section
    TextView deliveryType;
    TextView deliveryWeight;

    DeliveryFirebaseModel item;
    DatabaseReference userRef;
    private ValueEventListener valueEventListener;
    LoginUser driverProfile;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_order_history_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        item = (DeliveryFirebaseModel) intent.getSerializableExtra("item");

        initView();
        getDeliveryDetail(item);
    }

    private void initView(){
        description = findViewById(R.id.delivery_description);
        sendTo = findViewById(R.id.delivery_send_to_location);
        sendFrom = findViewById(R.id.delivery_send_from_location);
        price = findViewById(R.id.delivery_offered_price);
        driverName = findViewById(R.id.delivery_driver_name);
        driverImage = findViewById(R.id.delivery_driver_image);

        //Item Section
        deliveryType = findViewById(R.id.delivery_type);
        deliveryWeight = findViewById(R.id.delivery_weight);


    }

    private void getDeliveryDetail(final DeliveryFirebaseModel item){
        description.setText(item.DeliveryItem.ItemDescription);
        sendTo.setText(item.Receiver.Address);
        sendFrom.setText(item.Sender.Address);
        price.setText("RM " + item.DeliveryItem.Price);
        driverName.setText(item.Driver);
        deliveryType.setText(item.DeliveryItem.ItemType);
        deliveryWeight.setText(item.DeliveryItem.ItemWeight + " KG");


        if(item.Driver.toLowerCase().equals("joanne")){
            driverImage.setImageDrawable(getResources().getDrawable(R.drawable.joanne));
        }
        else if(item.Driver.toLowerCase().equals("ben")){
            driverImage.setImageDrawable(getResources().getDrawable(R.drawable.ben));
        }
        else if(item.Driver.toLowerCase().equals("nikki")){
            driverImage.setImageDrawable(getResources().getDrawable(R.drawable.nikki));
        }
        else
        {
            driverImage.setImageDrawable(getResources().getDrawable(R.drawable.blank_driver));
        }

        driverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.Driver != ""){
                    getDriverProfile(item.Driver);

                }
            }
        });

    }

    private void getDriverProfile(final String driverName){
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        userRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    LoginUser loginUser = snapshot.getValue(LoginUser.class);
                    if(loginUser.name.toLowerCase().equals(driverName.toLowerCase())){
                        driverProfile = loginUser;

                        Intent intent = new Intent(OrderDeliveryDetailActivity.this,
                                DriverProfile.class);
                        intent.putExtra("driver", driverProfile);
                        startActivity(intent);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
