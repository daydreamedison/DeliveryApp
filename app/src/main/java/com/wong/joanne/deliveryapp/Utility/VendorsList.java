package com.wong.joanne.deliveryapp.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sam on 11/1/2017.
 */

public class VendorsList {
    private HashMap<String, String> vendorList;

    public VendorsList(){
         vendorList = new HashMap<String, String>();
    }

    public HashMap<String, String> getVendorLis(){
        vendorList.put("PosLaju", "pos_laju_price_rate");

        return vendorList;
    }
}
