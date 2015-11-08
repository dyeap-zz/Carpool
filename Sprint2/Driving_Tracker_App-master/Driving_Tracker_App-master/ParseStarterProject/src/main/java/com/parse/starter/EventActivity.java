package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class EventActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

          /* Displays username */
        String username = DataHolder.getInstance().getUsername();
        TextView textElement = (TextView) findViewById(R.id.username);
        textElement.setText(username);
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
    /* Called when user clicks "Update" button */
    public void Update(View view) {

        Intent intent = new Intent(this, AccountActivity.class);

        final String username = DataHolder.getInstance().getUsername();

        EditText text_passenger = (EditText) findViewById(R.id.enter_passenger);
        String passenger_username = text_passenger.getText().toString();

        EditText text_time = (EditText) findViewById(R.id.enter_time);
        final int time = Integer.parseInt(text_time.getText().toString());

        // Query database
        // user1 = a && user2 = b
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("DrivingTable");
        query1.whereEqualTo("user1", username);
        query1.whereEqualTo("user2", passenger_username);
        // user1 = b && user2 = a
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("DrivingTable");
        query2.whereEqualTo("user2", username);
        query2.whereEqualTo("user1", passenger_username);
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
                        object.get(0).put("time",prev_time - time);
                        object.get(0).saveInBackground();
                    }
                }
                else
                {
                    System.out.printf("user not found");
                }
            }
        });
        startActivity(intent);
    }

}

