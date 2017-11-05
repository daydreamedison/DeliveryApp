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

    public void convert(String vendor){
/*        if(vendor.toLowerCase().equals("poslaju")){
            //PosLajuPrice();
            return dummyPrice(ItemType);
        }
        else
            return new VendorPriceRate();*/
    }

    private void dummyPrice(String ItemType){
/*        VendorPriceRate x = new VendorPriceRate();
        x.name = "poslaju";
        if(ItemType.toLowerCase().equals("document")) {
            x.documentPrice = "45.0";
            x.parcelPrice = "";
        }else
        {
            x.documentPrice = "";
            x.parcelPrice = "45.0";
        }
        x.workingDaysWithNotSameCity = "4 - 5 working days";
        x.workingDaysWithSameCity = "1 - 3 working days";

        return x;*/
    }

    private void dummyData(){
/*        PosLajuPriceRateModel documentSameRegion = new PosLajuPriceRateModel();
        documentSameRegion.subWeight = "0.25";
        documentSameRegion.subPriceRate = "0.8";
        documentSameRegion.startedFixPriceRate = "4.9";
        documentSameRegion.startedFixWeight = "0.5";
        documentSameRegion.itemType = "document";
        documentSameRegion.regionType = "same";

        PosLajuPriceRateModel documentNotSameRegion = new PosLajuPriceRateModel();
        documentNotSameRegion.subWeight = "0.25";
        documentNotSameRegion.subPriceRate = "1";
        documentNotSameRegion.startedFixPriceRate = "5.4";
        documentNotSameRegion.startedFixWeight = "0.5";
        documentNotSameRegion.itemType = "document";
        documentNotSameRegion.regionType = "notsame";

        PosLajuPriceRateModel parcelSameRegion = new PosLajuPriceRateModel();
        parcelSameRegion.subWeight = "0.5";
        parcelSameRegion.subPriceRate = "0.5";
        parcelSameRegion.startedFixPriceRate = "10.5";
        parcelSameRegion.startedFixWeight = "2";
        parcelSameRegion.itemType = "parcel";
        parcelSameRegion.regionType = "same";

        PosLajuPriceRateModel parcelNotSameRegion = new PosLajuPriceRateModel();
        parcelNotSameRegion.subWeight = "0.5";
        parcelNotSameRegion.subPriceRate = "2";
        parcelNotSameRegion.startedFixPriceRate = "16";
        parcelNotSameRegion.startedFixWeight = "2";
        parcelNotSameRegion.itemType = "parcel";
        parcelNotSameRegion.regionType = "notsame";

        List<PosLajuPriceRateModel> list = new ArrayList<>();
        list.add(documentSameRegion);
        list.add(documentNotSameRegion);
        list.add(parcelSameRegion);
        list.add(parcelNotSameRegion);

        return list;*/
    }
}
