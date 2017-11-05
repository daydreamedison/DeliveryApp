package com.wong.joanne.deliveryapp.Driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.Delivery;
import com.wong.joanne.deliveryapp.Utility.DeliveryFirebaseModel;
import com.wong.joanne.deliveryapp.Utility.DeliveryItem;
import com.wong.joanne.deliveryapp.Utility.DeliveryItemDetail;
import com.wong.joanne.deliveryapp.Utility.FirebaseDelivery;
import com.wong.joanne.deliveryapp.Utility.ReceiverInformation;

import java.util.ArrayList;

/**
 * Created by Sam on 10/20/2017.
 */

public class DeliveryListFragment extends Fragment {

    private ListView listView;
    private ArrayList<DeliveryFirebaseModel> firebaseDeliveryList = new ArrayList<>();
    ArrayList<DeliveryFirebaseModel> deliveryList = new ArrayList<>();
    public DeliveryListViewAdapter adapter;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.driver_delivery_list, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) getActivity().findViewById(R.id.all_delivery_list);
        try {
            getPendingDeliveryList();

            adapter = new DeliveryListViewAdapter(this.getActivity().getBaseContext(), firebaseDeliveryList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    DeliveryFirebaseModel item = adapter.getItem(i);
                    Intent intent = new Intent(DeliveryListFragment.this.getActivity(),
                            DeliveryDetailActivity.class);
                    intent.putExtra("item", item);
                    startActivity(intent);
                }
            });
        }
        catch(Exception ex){ System.out.println(ex);}


    }
    public void getPendingDeliveryList()
    {
        DatabaseReference deliveryRef = FirebaseDatabase.getInstance().getReference().child("PendingDeliveryList");
        deliveryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    deliveryList.clear();
                    firebaseDeliveryList.clear();
                    for(DataSnapshot data: dataSnapshot.getChildren())
                    {
                        FirebaseDelivery delivery = data.getValue(FirebaseDelivery.class);
                        DeliveryFirebaseModel temp = new DeliveryFirebaseModel();
                        temp.Key = data.getKey();
                        temp.DeliveryItem = delivery.DeliveryItem;
                        temp.OTP = delivery.OTP;
                        temp.Driver = delivery.Driver;
                        temp.Sender = delivery.Sender;
                        temp.Receiver = delivery.Receiver;
                        temp.Status = delivery.Status;
                        deliveryList.add(temp);
                    }
                    firebaseDeliveryList.addAll(deliveryList);
                    adapter.notifyDataSetChanged();
                }
                catch(Exception ex){
                    System.out.println(ex);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

}
