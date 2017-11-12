package com.wong.joanne.deliveryapp.CalculatorHelper;

import com.wong.joanne.deliveryapp.Utility.VendorPriceRate;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.vendor;

/**
 * Created by Sam on 11/1/2017.
 */

public class ConvertToVendorPriceRateModel {

    private Boolean IsSameCity;
    private double Weight;
    private String ItemType;

    public ConvertToVendorPriceRateModel(Boolean IsSameCity, String WEight, String Itemtype){
        this.Weight = Double.parseDouble(WEight);
        this.ItemType = Itemtype;
        this.IsSameCity =IsSameCity;
    }
}
