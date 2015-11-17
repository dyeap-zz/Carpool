package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class EventActivity extends ActionBarActivity {
    public int increment;
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

          /* Displays username */
        String username = DataHolder.getInstance().getUsername();
        TextView textElement = (TextView) findViewById(R.id.username);
        textElement.setText(username);
        updateTable(null);
        

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    boolean friend_found = false;
    public void AddPassenger(View view){
        Intent intent = new Intent(this, AccountActivity.class);
        final TableLayout table_layout = (TableLayout) findViewById(R.id.tableLayout);
        final String username = DataHolder.getInstance().getUsername();

        // Passenger's name
        EditText text_passenger = (EditText) findViewById(R.id.enter_passenger);
        final String passenger_username = text_passenger.getText().toString();

        final ParseObject eventsTable = new ParseObject("EventsTable");

        // Query if passenger is a friend
        ParseQuery<ParseObject> queryFriend1 = ParseQuery.getQuery("DrivingTable");
        queryFriend1.whereEqualTo("user1", passenger_username);
        ParseQuery<ParseObject> queryFriend2 = ParseQuery.getQuery("DrivingTable");
        queryFriend2.whereEqualTo("user2", passenger_username);

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(queryFriend1);
        queries.add(queryFriend2);

        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
        mainQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0) {
                    eventsTable.put("organizer", username);
                    eventsTable.put("guest", passenger_username);
                    eventsTable.put("attendance", false);
                    eventsTable.saveInBackground();
                    ParseACL groupACL = new ParseACL();
                    groupACL.setReadAccess(ParseUser.getCurrentUser(), true);
                    groupACL.setWriteAccess(ParseUser.getCurrentUser(), true);
                    groupACL.setReadAccess(objects.get(0).getObjectId(), true);
                    groupACL.setWriteAccess(objects.get(0).getObjectId(), true);
                    eventsTable.setACL(groupACL);
                    eventsTable.saveInBackground();
                    friend_found = true;
                } else {
                    CharSequence textFail = "Please add " + passenger_username +
                            " before sending invite .";

                    int duration = Toast.LENGTH_SHORT;
                    Toast toastFail = Toast.makeText(EventActivity.this, textFail, duration);
                    toastFail.show();
                }
            }
        });

        // Set ACL
        if(friend_found) {
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("username", passenger_username);
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        // Add ACL -- Both users can access
                        ParseACL groupACL = new ParseACL();
                        groupACL.setReadAccess(ParseUser.getCurrentUser(), true);
                        groupACL.setWriteAccess(ParseUser.getCurrentUser(), true);
                        groupACL.setReadAccess(objects.get(0).getObjectId(), true);
                        groupACL.setWriteAccess(objects.get(0).getObjectId(), true);
                        eventsTable.setACL(groupACL);
                        eventsTable.saveInBackground();
                    } else {
                        System.out.println("NOT FOUND");
                    }
                }
            });
            // Update table
            table_layout.removeAllViews();
            updateTable(null);
        }



        // Clear EditText after name of passenger is entered
        text_passenger.getText().clear();
    }

    /* Called when user clicks "Update" button */
    public int flag;
    public void Update(View view) {

        Intent intent = new Intent(this, AccountActivity.class);

        final String username = DataHolder.getInstance().getUsername();

        EditText text_passenger = (EditText) findViewById(R.id.enter_passenger);
        String passenger_username = text_passenger.getText().toString();

        EditText text_time = (EditText) findViewById(R.id.enter_time);
        final int time = Integer.parseInt(text_time.getText().toString());
        final ArrayList<String> passengers = new ArrayList<String>();
        ParseQuery<ParseObject> passengerQuery = ParseQuery.getQuery("EventsTable");
        passengerQuery.whereEqualTo("organizer", username);
        passengerQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                try{
                    for (int i = 0; i < object.size(); i++) {
                        if (object.get(i).getBoolean("attendance") == true){
                            passengers.add(object.get(i).getString("guest"));
                            String test = object.get(i).getObjectId();
                        }
                        else
                            passengers.add(null);
                    }
                    for (int i =0 ; i < passengers.size(); i++) {
                        if(passengers.get(i) == null){
                            continue;
                        }
                        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("DrivingTable");
                        query1.whereEqualTo("user1", username);
                        query1.whereEqualTo("user2", passengers.get(i));
                        // user1 = b && user2 = a
                        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("DrivingTable");
                        query2.whereEqualTo("user2", username);
                        query2.whereEqualTo("user1", passengers.get(i));
                        // Store first two queries in a list
                        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
                        queries.add(query1);
                        queries.add(query2);
                        // Perform multiple queries
                        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
                        mainQuery.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> object, ParseException e) {
                                if (e == null)//That means there's something in the query
                                {
                                    String userCheck = object.get(0).getString("user1");
                                    if (userCheck.equals(username)) {
                                        //Driver is user1
                                        int prev_time = object.get(0).getInt("time");
                                        object.get(0).put("time", prev_time + time);
                                        object.get(0).saveInBackground();
                                    } else {
                                        int prev_time = object.get(0).getInt("time");
                                        object.get(0).put("time", prev_time - time);
                                        object.get(0).saveInBackground();
                                    }
                                } else {
                                    System.out.printf("user not found");
                                }
                            }
                        });
                    }
                }
                catch(Exception l){
                    System.out.println("noo");
                }
            }
        });

        // Query database
        // user1 = a && user2 = b


        startActivity(intent);
    }
    public void updateTable(View view){
        final String username = DataHolder.getInstance().getUsername();
        final TableLayout table_layout = (TableLayout) findViewById(R.id.tableLayout);
        ParseQuery<ParseObject> queryGuests = ParseQuery.getQuery("EventsTable");
        queryGuests.whereEqualTo("organizer", username);
        queryGuests.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    int i = 0;
                    int cols = 3;
                    while (i < objects.size()) {
                        TableRow row = new TableRow(context);
                        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        for (int j = 0; j < cols; j++) {
                            /* Create column for each row (information for each row) */
                            TextView tv = new TextView(context);
                            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                    TableRow.LayoutParams.WRAP_CONTENT));
                            tv.setPadding(25, 25, 25, 25);
                            if (j == 0) {
                                /* Number each entry */
                                tv.setText(Integer.toString(i + 1));
                                tv.setTextColor(Color.WHITE);
                                row.addView(tv);
                            } else if (j == 1) {
                                /* Username of friend */
                                String friend_username = objects.get(i).getString("guest");
                                tv.setText(friend_username);
                                tv.setTextColor(Color.WHITE);
                                row.addView(tv);
                            } else if (j == 2) {
                                /* Time owed */
                                Boolean isAttend = objects.get(i).getBoolean("attendance");
                                if (isAttend == true)
                                    tv.setText("Attending");
                                else
                                    tv.setText("Not Attending");
                                tv.setTextColor(Color.WHITE);
                                row.addView(tv);
                            }
                        }
                        final Button removeButton = new Button(context);
                        removeButton.setText("uninvite");
                        removeButton.setContentDescription(objects.get(i).getObjectId());
                        removeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    ParseObject.createWithoutData("EventsTable", (String) removeButton.getContentDescription()).delete();
                                } catch (Exception e) {
                                    System.err.println("cant find");
                                }
                                table_layout.removeAllViews();
                                updateTable(null);
                            }
                        });
                        i++;
                        increment = i;


                        row.addView(removeButton);
                        table_layout.addView(row);
                    }
                } else {
                    //System.out.printf("TOO BAD\n");
                }
            }
        });
    }

}

