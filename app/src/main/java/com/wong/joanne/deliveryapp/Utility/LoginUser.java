package com.wong.joanne.deliveryapp.Utility;

import java.io.Serializable;

/**
 * Created by Sam on 10/20/2017.
 */

public class LoginUser implements Serializable{
    public int account_type;
    public String email;
    public Long id;
    public String name;
    public String password;
    public Boolean status;
    public String contactnumber;
}
