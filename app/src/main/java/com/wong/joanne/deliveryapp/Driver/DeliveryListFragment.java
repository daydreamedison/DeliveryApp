package com.wong.joanne.deliveryapp.Driver;

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
        //fireDummyData();
        try {
            getPendingDeliveryList();

            adapter = new DeliveryListViewAdapter(this.getActivity().getBaseContext(), firebaseDeliveryList);
            listView.setAdapter(adapter);
        }
        catch(Exception ex){}


    }
    public void getPendingDeliveryList()
    {
        DatabaseReference deliveryRef = FirebaseDatabase.getInstance().getReference().child("PendingDeliveryList");
        deliveryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                deliveryList.clear();
                firebaseDeliveryList.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    DeliveryFirebaseModel delivery = data.getValue(DeliveryFirebaseModel.class);
                    deliveryList.add(delivery);
                }
                firebaseDeliveryList.addAll(deliveryList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void fireDummyData()
    {
        /*DeliveryFirebaseModel delivery = new DeliveryFirebaseModel();
        delivery.Status = "pending";
        delivery.OTP = "asdsadasdasd";

        DeliveryItem deliveryItem = new DeliveryItem();
        deliveryItem._itemDescription = "helloo56+oo";

        DeliveryItemDetail item = new DeliveryItemDetail();
        item.Price = 1;
        item.Quantity = 1;
        item.Type = 1;

        deliveryItem._documentItem = item;
        deliveryItem._parcelItem = item;
        deliveryItem._lugaggeItem = item;

        delivery.DeliveryItem = deliveryItem;

        ReceiverInformation receiverInformation = new ReceiverInformation();
        receiverInformation.Address = "sad";
        receiverInformation.State = "asd";
        receiverInformation.City = "456das";
        receiverInformation.ContactNumber = "asd";
        receiverInformation.Name = "asd";

        delivery.receiverInformation = receiverInformation;
        delivery.senderInformation = receiverInformation;

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference db = mDatabase.child("PendingDeliveryList");
        String key = mDatabase.child("PendingDeliveryList").push().getKey();
        db.child(key).setValue(delivery);*/
    }


}
