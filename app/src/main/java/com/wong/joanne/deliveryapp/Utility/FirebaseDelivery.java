package com.wong.joanne.deliveryapp.Utility;

import java.io.Serializable;

/**
 * Created by Sam on 11/5/2017.
 */

public class FirebaseDelivery implements Serializable{
    public DeliveryItem DeliveryItem;
    public ReceiverInformation Receiver;
    public ReceiverInformation Sender;
    public String Driver;
    public String OTP;
    public String Status;
}
