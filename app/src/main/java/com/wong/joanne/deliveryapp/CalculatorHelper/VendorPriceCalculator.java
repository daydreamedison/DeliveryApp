package com.wong.joanne.deliveryapp.CalculatorHelper;

import android.content.Context;

import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.CalculatePriceModel;
import com.wong.joanne.deliveryapp.Utility.VendorPriceRate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 11/4/2017.
 */

public class VendorPriceCalculator {

    protected String[] vendorList = new String[] { "deliveryapp", "poslaju", "gdex" };
    private boolean isSameCity;
    private double weight;
    private String itemType;
    Context context;

    public VendorPriceCalculator(String fromLocation, String toLocation, String itemType, String weight){
        this.isSameCity = checkSameCity(fromLocation, toLocation);
        this.itemType = itemType;
        this.weight = Double.parseDouble(weight);
    }

    public void setXMLPath(Context context){
        this.context = context;
    }

    private boolean checkSameCity(String fromLocation, String toLocation){
        return fromLocation.toLowerCase().equals(toLocation.toLowerCase());
    }

    public List<VendorPriceRate> calculateAllVendor(){

        List<VendorPriceRate> allVendorPriceList = new ArrayList<>();
        for(String vendor : vendorList){
            switch(vendor.toLowerCase())
            {
                case "poslaju":
                    try {
                        //intialise
                        InputStream plInput = context.getResources().openRawResource(R.raw.pos_laju_price_rate);
                        PosLajuPrice posLajuPrice = new PosLajuPrice(isSameCity, itemType, weight);

                        //functions
                        posLajuPrice.readXML(plInput);
                        VendorPriceRate price = posLajuPrice.calculate();

                        //add into vendors list
                        price.itemType = this.itemType;
                        allVendorPriceList.add(price);
                    }
                    catch(Exception ex) { System.out.println(ex); }
                    break;
                case "deliveryapp":
                    try {
                        //intialise
                        InputStream deliveryAppInput = context.getResources().openRawResource(R.raw.delivery_app_price_rate);
                        DeliveryAppPrice deliveryAppPrice = new DeliveryAppPrice(isSameCity, itemType, weight);

                        //functions
                        deliveryAppPrice.readXML(deliveryAppInput);
                        VendorPriceRate price = deliveryAppPrice.calculate();

                        //add into vendors list
                        price.itemType = this.itemType;
                        allVendorPriceList.add(price);
                    }
                    catch(Exception ex) {
                        System.out.println(ex);
                    }
                    break;
                case "gdex":
                    break;
            }
        }

        return allVendorPriceList;
    }

}
