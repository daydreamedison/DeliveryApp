package com.wong.joanne.deliveryapp.CalculatorHelper;

import android.util.Xml;

import com.wong.joanne.deliveryapp.R;
import com.wong.joanne.deliveryapp.Utility.CalculatePriceModel;
import com.wong.joanne.deliveryapp.Utility.Delivery;
import com.wong.joanne.deliveryapp.Utility.VendorPriceRate;
import com.wong.joanne.deliveryapp.Utility.XML;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;

/**
 * Created by Sam on 10/31/2017.
 */

public class PosLajuPrice {
    private boolean isSameCity;
    private String itemType;
    private double weight;

    private double finalPrice;

    private List<PosLajuPriceRateModel> priceRateModelList;

    public PosLajuPrice(boolean isSameCity, String itemType, double weight){
        this.isSameCity = isSameCity;
        this.itemType = itemType;
        this.weight = weight;
        this.priceRateModelList = new ArrayList<>();
        finalPrice = 0.0;
    }

    public void readXML(InputStream in)throws XmlPullParserException, IOException{
        //read XML
        try{
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);

            int eventType = parser.getEventType();

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
        catch(Exception ex){

        }
    }

    List<PosLajuPriceRateModel> readPriceModel(XmlPullParser parser) throws XmlPullParserException, IOException{
        int eventType = parser.getEventType();
        List<PosLajuPriceRateModel> priceList = new ArrayList<>();

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = parser.getName();
            if(eventType == XmlPullParser.START_TAG){
                if( name == null || name.isEmpty()){
                    parser.next();
                    continue;
                }
                else {
                    if(name.toLowerCase().equals(XML.Price)){
                        //continue read nested xml nodes
                        PosLajuPriceRateModel temp = readPrice(parser);

                        //check one of properties is null or not
                        if(temp.ItemType != null){
                            priceList.add(temp);
                        }
                    }
                }
            }
            else if(parser.getEventType() == XmlPullParser.END_TAG) {
                String endName = parser.getName();
                if(endName.toLowerCase().equals(XML.Resources)){
                    //if end of the XML, end this function
                    return priceList;
                }
            }
            //next XML node
            eventType = parser.next();
        }

        //default empty pricelist
        return priceList;
    }

    PosLajuPriceRateModel readPrice(XmlPullParser parser) throws XmlPullParserException, IOException{
        int eventType = parser.getEventType();
        PosLajuPriceRateModel priceRateModel = new PosLajuPriceRateModel();

        while(eventType != XmlPullParser.END_DOCUMENT) {
            String name = parser.getName();
            if(eventType == XmlPullParser.START_TAG){
                if(name == null || name.isEmpty()){
                    parser.next();
                    continue;
                }
                else{
                    //check the node is equal specify name tag
                    if(name.toLowerCase().equals((XML.Ciy))){
                        priceRateModel.City = readValue(parser);
                    }
                    else if(name.toLowerCase().equals(XML.PriceRateType)){
                        priceRateModel.PriceRateType = readValue(parser);
                    }
                    else if(name.toLowerCase().equals(XML.Weight)){
                        priceRateModel.Weight = readValue(parser);
                    }
                    else if(name.toLowerCase().equals(XML.PriceRate)){
                        priceRateModel.PriceRate = readValue(parser);
                    }
                    else if(name.toLowerCase().equals(XML.Item)){
                        priceRateModel.ItemType = readValue(parser);
                    }
                }
            }
            else if(parser.getEventType() == XmlPullParser.END_TAG)
            {
                String endName = parser.getName();
                if(endName.toLowerCase().equals(XML.Price))
                {
                    return priceRateModel;
                }
            }
            //next line / XML node
            eventType = parser.next();
        }

        return priceRateModel;
    }

    private String readValue(XmlPullParser parser) throws XmlPullParserException, IOException{
        //read the value
        parser.next();
        return parser.getText();
    }

    public VendorPriceRate calculate(){
        //calculate
        String city = "";
        String item = "";
        double startingWeight = 0.0;
        double startingPrice = 0.0;
        double subWeight = 0.0;
        double subPrice = 0.0;

        //set item type checking value
        if(this.itemType.toLowerCase().equals(XML.DocumentType))
            item = XML.DocumentType;
        else
            item = XML.ParcelType;

        //set city checking value
        if(isSameCity)
            city = "yes";
        else
            city = "no";

        for(PosLajuPriceRateModel model : priceRateModelList){
            //check whether is same city or not
            if(model.City.toLowerCase().equals(city)){
                //check item type
                if(model.ItemType.toLowerCase().equals(item)) {
                    if (model.PriceRateType.toLowerCase().equals("starting")) {
                        startingPrice = Double.parseDouble(model.PriceRate);
                        startingWeight = Double.parseDouble(model.Weight);
                    } else if (model.PriceRateType.toLowerCase().equals("subsequent")) {
                        subPrice = Double.parseDouble(model.PriceRate);
                        subWeight = Double.parseDouble(model.Weight);
                    }
                }
            }
        }

        //calculate the price
        if(weight <= startingWeight){
            finalPrice = startingPrice;
        }else{
            double tempWeight = weight/startingWeight;
            double tempWeight2 = tempWeight/subWeight;

            finalPrice = startingPrice + (tempWeight2 * subPrice);
        }

        VendorPriceRate model = new VendorPriceRate();
        model.name = "Pos Laju";
        model.price = String.valueOf(finalPrice);

        return model;
    }

    //get pos laju price
    public double getPrice(){
        return this.finalPrice;
    }
}
