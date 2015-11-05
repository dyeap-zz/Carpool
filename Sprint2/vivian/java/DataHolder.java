package com.parse.starter;

import com.parse.ParseObject;

/**
 * Created by VivianTong on 11/2/15.
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

    /* Creating class to access ParseObject gloabelly*/
    private static final DataHolder holder = new DataHolder();
    /* DataHolder Accessor*/
    public static DataHolder getInstance(){
        return holder;
    }
}
