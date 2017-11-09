package com.wong.joanne.deliveryapp.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.Delivery;
import com.wong.joanne.deliveryapp.Utility.DeliveryItem;
import com.wong.joanne.deliveryapp.Utility.DeliveryItemDetail;
import com.wong.joanne.deliveryapp.Utility.LoginUser;
import com.wong.joanne.deliveryapp.Utility.XML;

/**
 * Created by Sam on 10/20/2017.
 */

public class OrderDeliveryFragment extends Fragment {

    private EditText orderDescription;
    private EditText itemWeight;
    private Button documentItemBtn;
    private Button parcelItemBtn;
    private Button nextBtn;
    String DeliveryType;
    String itemWeightText;

    Spinner itemQuantitySpinner;

    LoginUser currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentUser = (LoginUser) getArguments().getSerializable("user");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_create_delivery_layout, container, false);

        itemWeightText = "0";
        itemQuantitySpinner = (Spinner) view.findViewById(R.id.spinner_quantity);
        documentItemBtn = (Button) view.findViewById(R.id.button_document);
        parcelItemBtn = (Button) view.findViewById(R.id.button_parcel);
        orderDescription = (EditText) view.findViewById(R.id.edit_text_order_description);

        //onClick functions
        documentItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliveryType = XML.DocumentType;
            }
        });

        parcelItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliveryType = XML.ParcelType;
            }
        });

        //Next button click function
        nextBtn = (Button) view.findViewById(R.id.btn_next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateForm()){
                    DeliveryItem Item = new DeliveryItem();
                    Item.ItemDescription = orderDescription.getText().toString();
                    Item.ItemWeight = itemWeightText;
                    Item.ItemType = DeliveryType;

                    gotoDeliveryInformationForm(Item, currentUser);
                }

            }
        });

        //Spinner
        final ArrayAdapter<CharSequence> itemQuantityAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.quantity, R.layout.spinner_item_quantity);
        itemQuantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemQuantitySpinner.setAdapter(itemQuantityAdapter);
        itemQuantitySpinner.setSelection(0);
        itemQuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                itemWeightText = itemQuantitySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                itemWeightText = "0";
            }
        });
        return view;
    }

    private void gotoDeliveryInformationForm(DeliveryItem item, LoginUser currentUser)
    {
        Intent intent = new Intent(OrderDeliveryFragment.this.getActivity(), DeliveryFormActivity.class);
        intent.putExtra("itemDescription", item.ItemDescription);
        intent.putExtra("itemWeight", item.ItemWeight);
        intent.putExtra("itemType", item.ItemType);
        intent.putExtra("user", currentUser);
        OrderDeliveryFragment.this.startActivity(intent);
    }

    private boolean validateForm()
    {
        if(DeliveryType == null || DeliveryType == ""){
            Toast.makeText(getActivity().getApplicationContext(), "Please select item type.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(orderDescription.getText().toString() == "" || orderDescription.getText().toString() == null){
            Toast.makeText(getActivity().getApplicationContext(), "Please describe your item.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(orderDescription.getText().length() < 10 ){
            Toast.makeText(getActivity().getApplicationContext(), "Please describe your item at least 10 characters.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
