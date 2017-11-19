package com.wong.joanne.deliveryapp.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import com.wong.joanne.deliveryapp.Utility.DeliveryFirebaseModel;
import com.wong.joanne.deliveryapp.Utility.FirebaseDelivery;
import com.wong.joanne.deliveryapp.Utility.LoginUser;

import java.util.ArrayList;

/**
 * Created by Sam on 11/19/2017.
 */

public class PendingDeliveryFragment extends Fragment {

    private ListView listView;
    private ArrayList<DeliveryFirebaseModel> firebaseDeliveryList = new ArrayList<>();
    ArrayList<DeliveryFirebaseModel> histriesList = new ArrayList<>();
    public PendingDeliveryListViewAdapter adapter;
    private DatabaseReference mDatabase;

    LoginUser currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        currentUser = (LoginUser) getArguments().getSerializable("user");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_order_history_layout, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) getActivity().findViewById(R.id.order_histories);
        try{
            getOrderHistories();

            adapter = new PendingDeliveryListViewAdapter(this.getActivity().getBaseContext(),
                    firebaseDeliveryList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    DeliveryFirebaseModel item = adapter.getItem(i);
                    Intent intent = new Intent(PendingDeliveryFragment.this.getActivity(),
                            OrderDeliveryDetailActivity.class);
                    intent.putExtra("item", item);
                    startActivity(intent);
                }
            });
        }
        catch(Exception ex) {System.out.println(ex);}
    }

    public void getOrderHistories(){
        DatabaseReference deliveryRef = FirebaseDatabase.getInstance().getReference().child("PendingDeliveryList");
        deliveryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    histriesList.clear();
                    firebaseDeliveryList.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        FirebaseDelivery delivery = data.getValue(FirebaseDelivery.class);
                        DeliveryFirebaseModel temp = new DeliveryFirebaseModel();
                        if (delivery.Sender.Name != null) {
                            if (delivery.Sender.Name.toLowerCase().equals(currentUser.name.toLowerCase())) {
                                if(!delivery.Status.toLowerCase().equals("completed"))
                                {
                                    temp.Key = data.getKey();
                                    temp.DeliveryItem = delivery.DeliveryItem;
                                    temp.OTP = delivery.OTP;
                                    temp.Driver = delivery.Driver;
                                    temp.Sender = delivery.Sender;
                                    temp.Receiver = delivery.Receiver;
                                    temp.Status = delivery.Status;
                                    histriesList.add(temp);
                                }
                            }
                        }
                    }

                    firebaseDeliveryList.addAll(histriesList);
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
