package com.wong.joanne.deliveryapp.CalculatorHelper;

import android.util.Xml;
import android.widget.Toast;

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

    private String CityFrom, CityTo, weight;
    private List<DeliveryAppPriceRateModel> priceRateModelList;

    public DeliveryAppPrice( String CityFrom, String CityTo, String weight)
    {
        this.CityFrom = CityFrom;
        this.CityTo = CityTo;
        this.weight = weight;
        this.priceRateModelList = new ArrayList<>();
    }

    //calculate after read XML data
    public double calculate()
    {
        if(priceRateModelList.size() > 0)
        {
            boolean isSameCity = false;
            if(CityTo.equals(CityFrom)){
                isSameCity = true;
            }
            else
                isSameCity = false;

            for(DeliveryAppPriceRateModel price : priceRateModelList)
            {
                if((price.isSameCity.equals("yes") && isSameCity) || (price.isSameCity.equals("no") && !isSameCity)){
                    double itemWeight = Double.parseDouble(weight);
                    double priceRateWeight = Double.parseDouble(price.weight);
                    double priceRate = Double.parseDouble(price.priceRate);

                    return ( itemWeight / priceRateWeight ) * priceRate;
                }
            }
        }

        return 0.00;
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
