package com.parse.starter;

import com.parse.ParseObject;

/**
 * Created by VivianTong on 11/5/15.
 */
public class DataHolder {
    /* Creating ParseObject for global use */

    /* drivingTable stores the time owed between two drivers
     * Columns: user1, user2, time
     */
    ParseObject drivingTable = new ParseObject("DrivingTable");
    /* ParseObject Accessor */
    public ParseObject getData(){
        return drivingTable;
    }
    /* ParseObject Manipulator*/
    public void setData(ParseObject drivingTable){
        this.drivingTable = drivingTable;
    }

    /* eventTable stores the organizer, invitee, and attendance
     * Columns: organizer, invitee, attendance
     */
    ParseObject eventTable = new ParseObject("events");
    public ParseObject getEvent() { return eventTable; }
    public void setEvent(ParseObject eventTable) { this.eventTable = eventTable; }

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
