package com.wong.joanne.deliveryapp.Utility;

import java.io.Serializable;

/**
 * Created by Sam on 10/26/2017.
 */

public class DeliveryFirebaseModel implements Serializable{
    public String Key;
    public DeliveryItem DeliveryItem;
    public ReceiverInformation Receiver;
    public ReceiverInformation Sender;
    public String Driver;
    public String OTP;
    public String Status;
}
