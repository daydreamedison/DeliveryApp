package com.wong.joanne.deliveryapp.Driver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.Delivery;

import java.util.ArrayList;

/**
 * Created by Sam on 10/22/2017.
 */

public class test extends AppCompatActivity {

    ArrayList<String> deliveryList = new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_delivery_list);

        ListView listView = (ListView) this.findViewById(R.id.all_delivery_list);

        deliveryList.clear();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, deliveryList);
        listView.setAdapter(adapter);
        getPendingDeliveryList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    public void getPendingDeliveryList()
    {
        DatabaseReference deliveryRef = FirebaseDatabase.getInstance().getReference("PendingDeliveryList");
        deliveryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Delivery value = dataSnapshot.getValue(Delivery.class);
                deliveryList.add(value.getOTP());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
