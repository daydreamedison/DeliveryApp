package com.wong.joanne.deliveryapp.Utility;

import java.io.Serializable;

/**
 * Created by Sam on 10/21/2017.
 */

public class Delivery {

    private ReceiverInformation receiverInformation;
    private ReceiverInformation senderInformation;
    private String OTP;
    private String Status;
    private String DriverKey;
    private DeliveryItem DeliveryItem;

    public Delivery(){

    }

    public void setStatus(String status){
        this.Status = status;
    }

    public String getStatus(){
        return this.Status;
    }


    public ReceiverInformation getReceiverInformation(){
        return this.receiverInformation;
    }

    public void setReceiverInformation(ReceiverInformation receiverInformation)
    {
        this.receiverInformation = receiverInformation;
    }

    public ReceiverInformation getSenderInformaiton(){
        return this.senderInformation;
    }

    public void setSenderInformation(ReceiverInformation senderInformation)
    {
        this.senderInformation = senderInformation;
    }

    public String getOTP()
    {
        return this.OTP;
    }

    public void setOTP(String OTP)
    {
        this.OTP = OTP;
    }

    public void generateOTP(){

    }

    public void validateOTP(String OTP){

    }

    public String getDriverKey(){
        return this.DriverKey;
    }

    public void setDriverKey(String driverKey){
        this.DriverKey = driverKey;
    }

    public DeliveryItem getDeliveryItem(){
        return this.DeliveryItem;
    }

    public void setDeliveryItem(DeliveryItem deliveryItem){
        this.DeliveryItem = deliveryItem;
    }

    public String getTotalPrice(){
        return "0.00";
    }

    public String getLocation(){
        return this.receiverInformation.Address + " "
                + this.receiverInformation.City + " "
                + this.receiverInformation.State;
    }
}
