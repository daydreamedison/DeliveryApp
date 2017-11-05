package com.wong.joanne.deliveryapp.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wong.joanne.deliveryapp.LoginActivity;
import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.VendorPriceRate;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Sam on 11/1/2017.
 */

public class OrderSuccessPage extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_order_success_layout);

        Intent intent = this.getIntent();
        String OTP = (String)getIntent().getStringExtra("OTP");
        TextView otp = (TextView) findViewById(R.id.txt_OTP);
        otp.setText(OTP);

        Button btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMainPage();
            }
        });

    }

    private String genOTP(){
        Random rand = new Random();
        int n = rand.nextInt(99999) + 10000;

        return String.valueOf(n);
    }

    private void backToMainPage(){
        startActivity(new Intent(OrderSuccessPage.this, CustomerMainActivity.class));
        finish();
    }


}
