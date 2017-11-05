package com.wong.joanne.deliveryapp.CalculatorHelper;

import android.util.Xml;
import android.widget.Toast;

import com.wong.joanne.deliveryapp.Utility.Delivery;
import com.wong.joanne.deliveryapp.Utility.VendorPriceRate;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 10/31/2017.
 */

public class DeliveryAppPrice {

    private List<DeliveryAppPriceRateModel> priceRateModelList;

    private boolean isSameCity;
    private String itemType;
    private double weight;

    public DeliveryAppPrice (boolean isSameCity, String itemType, double weight){
        this.isSameCity = isSameCity;
        this.itemType = itemType;
        this.weight = weight;
    }

    //calculate after read XML data
    public VendorPriceRate calculate()
    {
        if(priceRateModelList.size() > 0)
        {
            //change these function
            //assign priceRateModelList price into Vendor Price Rate Model

            for(DeliveryAppPriceRateModel price : priceRateModelList)
            {
                if((price.isSameCity.equals("yes") && this.isSameCity) || (price.isSameCity.equals("no") && !this.isSameCity)){
                    double itemWeight = this.weight;
                    double priceRateWeight = Double.parseDouble(price.weight);
                    double priceRate = Double.parseDouble(price.priceRate);

                    VendorPriceRate model = new VendorPriceRate();
                    model.name = "Delivery App";
                    model.price = String.valueOf(( itemWeight / priceRateWeight ) * priceRate);


                    return model;
                }
            }
        }
        return new VendorPriceRate();
    }

    //read XML data
    public void readXML(InputStream in)throws XmlPullParserException, IOException
    {
        try{
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);

            int eventType = parser.getEventType();

            //loop the XML until the end
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                    System.out.println("Start document");
                } else if(eventType == XmlPullParser.END_DOCUMENT) {
                    System.out.println("End document");
                    break;
                } else if(eventType == XmlPullParser.START_TAG) {
                    priceRateModelList = readPriceModel(parser);
                }
                eventType = parser.next();
            }
        }
        finally {
            in.close();
        }
    }

    private List<DeliveryAppPriceRateModel> readPriceModel(XmlPullParser parser) throws XmlPullParserException, IOException{
        int eventType = parser.getEventType();

        List<DeliveryAppPriceRateModel> priceList = new ArrayList<>();

        //loop the XML until found Price Model
        while(eventType != XmlPullParser.END_DOCUMENT){
            String name = parser.getName();
            if(eventType == XmlPullParser.START_TAG){
                if( name == null || name.isEmpty()) {
                    parser.next();
                    continue;
                }
                else {
                    if(name.equals("Price")){
                        DeliveryAppPriceRateModel temp = readPrice(parser);
                        if(temp.priceRate != null)
                            priceList.add(temp);
                    }
                }
            }
            else if(parser.getEventType() == XmlPullParser.END_TAG)
            {
                String endName = parser.getName();
                if(endName.equals("Resources"))
                {
                    return priceList;
                }
            }
            eventType = parser.next();
        }

        return priceList;
    }

    private DeliveryAppPriceRateModel readPrice(XmlPullParser parser)
            throws XmlPullParserException, IOException
    {
        int eventType = parser.getEventType();
        DeliveryAppPriceRateModel priceRateModel = new DeliveryAppPriceRateModel();

        while(eventType != XmlPullParser.END_DOCUMENT){
            String name = parser.getName();
            if(eventType == XmlPullParser.START_TAG){
                if( name == null || name.isEmpty()) {
                    parser.next();
                    continue;
                }
                else {
                    if(name.equals("pricerate")){
                        priceRateModel.priceRate = readValue(parser);
                    }
                    else  if(name.equals("city")){
                        priceRateModel.isSameCity = readValue(parser);
                    }
                    else if(name.equals("weight")){
                        priceRateModel.weight = readValue(parser);
                    }
                }
            }
            else if(parser.getEventType() == XmlPullParser.END_TAG)
            {
                String endName = parser.getName();
                if(endName.equals("Price"))
                {
                    return priceRateModel;
                }
            }
            eventType = parser.next();
        }

        return priceRateModel;
    }

    private String readValue(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.next();
        return parser.getText();
    }





}
