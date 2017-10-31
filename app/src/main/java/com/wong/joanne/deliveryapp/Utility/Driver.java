package com.wong.joanne.deliveryapp.Utility;

/**
 * Created by Sam on 10/20/2017.
 */

public class Driver {
    private String username;
    private String password;
    private String email;
    private String contactNumber;
    private String address;
    private String carModel;
    private String carPlateNumber;

    public Driver(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setContactNumber(String contactNumber)
    {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber()
    {
        return this.contactNumber;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return this.address;
    }

    public void setCarInformation(String carModel, String carPlateNumber)
    {
        this.carModel = carModel;
        this.carPlateNumber = carPlateNumber;
    }

    public String getCarModel()
    {
        return this.carModel;
    }

    public String getCarPlateNumber()
    {
        return this.getCarPlateNumber();
    }
}
