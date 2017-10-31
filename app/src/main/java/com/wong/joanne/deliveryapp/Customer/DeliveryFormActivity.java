package com.wong.joanne.deliveryapp.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wong.joanne.deliveryapp.CalculatorHelper.DeliveryAppPrice;
import com.wong.joanne.deliveryapp.CalculatorHelper.PosLajuPriceRateModel;
import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.DeliveryItem;
import com.wong.joanne.deliveryapp.Utility.ReceiverInformation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 10/20/2017.
 */

public class DeliveryFormActivity extends AppCompatActivity {

    private Button buttonNext;
    private EditText street;
    private EditText city;
    private EditText receiverName;
    private EditText receiverPhonenumber;
    private EditText receiverStreet;
    private EditText receiverCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_delivery_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get previous item information
        Intent intent = this.getIntent();
        final String itemDescription = intent.getStringExtra("itemDescription");
        final String itemWeight = intent.getStringExtra("itemWeight");
        final String itemType = intent.getStringExtra("itemType");



        //Sender Information
        street = (EditText) findViewById(R.id.edit_text_street);
        city = (EditText) findViewById(R.id.edit_text_city);

        //Receiver Information
        receiverName = (EditText) findViewById(R.id.edit_text_receiver_name);
        receiverPhonenumber = (EditText) findViewById(R.id.edit_text_receiver_phonenumber);
        receiverStreet = (EditText) findViewById(R.id.edit_text_receiver_street);
        receiverCity = (EditText) findViewById(R.id.edit_text_receiver_city);

        buttonNext = (Button) findViewById(R.id.btn_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()){

                    ReceiverInformation sender = new ReceiverInformation();
                    sender.Name = "sam";
                    sender.ContactNumber = "111";
                    sender.City = city.getText().toString();
                    sender.Address = street.getText().toString() + " " + city.getText().toString();

                    ReceiverInformation receiver = new ReceiverInformation();
                    receiver.Name = receiverName.getText().toString();
                    receiver.Address = receiverStreet.getText().toString() + "" + receiverCity.getText().toString();
                    receiver.ContactNumber = receiverPhonenumber.getText().toString();
                    receiver.City = receiverCity.getText().toString();

                    DeliveryItem deliveryItem = new DeliveryItem();
                    deliveryItem.ItemDescription = itemDescription;
                    deliveryItem.ItemWeight = itemWeight;
                    deliveryItem.ItemType = itemType;
                    //calculate own apps delivery price rate
                    deliveryItem.Price = calculatePrice(city.getText().toString(), receiverCity.getText().toString(), itemWeight);

                    //calculate others vendors
                    goVendorPriceListAcivity(sender, receiver, deliveryItem);
                }

            }
        });
    }

    private boolean validate(){
        return true;
    }

    private String calculatePrice(String CityFrom, String CityTo, String weight){

        try
        {
            InputStream in = getResources().openRawResource(R.raw.delivery_app_price_rate);
            DeliveryAppPrice itemPrice = new DeliveryAppPrice(CityFrom, CityTo, weight);
            itemPrice.readXML(in);

            double price = itemPrice.calculate();
            return Double.toString(price);
        }
        catch(Exception ex){
        }

        return "0.00";
    }

    private void goVendorPriceListAcivity(ReceiverInformation sender, ReceiverInformation receiver, DeliveryItem deliveryItem)
    {
        Intent intent = new Intent(DeliveryFormActivity.this, VendorPriceListAcivity.class);
        intent.putExtra("sender", sender);
        intent.putExtra("receiver", receiver);
        intent.putExtra("deliveryItem", deliveryItem);
        DeliveryFormActivity.this.startActivity(intent);
    }




}