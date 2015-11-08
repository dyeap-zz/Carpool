package com.parse.starter;

import com.parse.ParseObject;

/**
 * Created by VivianTong on 11/5/15.
 */
public class DataHolder {
    /* Creating ParseObject for global use */
    ParseObject drivingTable = new ParseObject("DrivingTable");
    /* ParseObject Accessor */
    public ParseObject getData(){
        return drivingTable;
    }
    /* ParseObject Manipulator*/
    public void setData(ParseObject drivingTable){
        this.drivingTable = drivingTable;
    }

    /* Saves username */
    String username = new String("Fill in username");
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }


    /* Creating class to access ParseObject gloabelly*/
    private static final DataHolder holder = new DataHolder();
    /* DataHolder Accessor*/
    public static DataHolder getInstance(){
        return holder;
    }
}
