package com.parse.starter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class ViewEventActivity extends ActionBarActivity {
    List<String> organizers = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        // Get username
        String username = DataHolder.getInstance().getUsername();
        // Display username
        TextView textElement = (TextView) findViewById(R.id.username);
        textElement.setText(username);

        // Create Table for activity.view_event.xml
        final TableLayout table_layout = (TableLayout) findViewById(R.id.tableLayout);

        // Query eventTable and find matches of the user under
        // organizer and guest columns
        ParseQuery<ParseObject> queryEvents = ParseQuery.getQuery("EventsTable");
        //queryEvents.whereEqualTo("organizer", "peas");
        queryEvents.whereEqualTo("guest", username);
        //queryEvents.whereEqualTo("attendance", false);
        queryEvents.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {

                for(int i = 0; i < objects.size(); i++){
                    // Creates the row in the table
                    TableRow row = new TableRow(ViewEventActivity.this);
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));

                    // Create content for the row
                    // First, name of organizer
                    TextView tv = new TextView(ViewEventActivity.this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setPadding(25, 25, 25, 25);
                    String organizer = objects.get(i).getString("organizer");
                    organizers.add(i, organizer);
                    tv.setText(organizer);
                    tv.setTextColor(Color.WHITE);
                    row.addView(tv);
                    // attending?
                    Boolean attending = objects.get(i).getBoolean("attendance");
                    // Second, accept button
                    Button accept = new Button(ViewEventActivity.this);
                    accept.setText("Accept");
                    accept.setId(i);
                    accept.setOnClickListener(acceptHandler);
                    row.addView(accept);
                    // Third, deny button
                    Button deny = new Button(ViewEventActivity.this);
                    deny.setText("Deny");
                    deny.setId(i+50);
                    row.addView(deny);
                    deny.setOnClickListener(denyHandler);

                    if(attending){
                        accept.setTextColor(Color.GREEN);
                    } else {
                        deny.setTextColor(Color.RED);
                    }

                    table_layout.addView(row);
                }
            }
        });


    }

    View.OnClickListener acceptHandler = new OnClickListener() {
        @Override
        public void onClick(View view) {
            // Set button color to green
            Button clickHere = (Button) findViewById(organizers.size() - 1);
            clickHere.setTextColor(Color.GREEN);
            Button notClicked = (Button) findViewById(organizers.size() - 1 + 50);
            notClicked.setTextColor(Color.BLACK);

            String organizer = organizers.get(organizers.size() - 1);

            // Query find the organizer of the event
            ParseQuery<ParseObject> eventQuery = ParseQuery.getQuery("EventsTable");
            eventQuery.whereEqualTo("organizer", organizer);
            eventQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    for(int i = 0; i < objects.size(); i++ ){
                        // Change attending to true
                        objects.get(i).put("attending", true);
                        objects.get(i).saveInBackground();
                    }
                }
            });
        }
    };

    View.OnClickListener denyHandler = new OnClickListener() {
        @Override
        public void onClick(View view) {
            // Set button color to red
            Button clickHere = (Button) findViewById(organizers.size() - 1 + 50);
            clickHere.setTextColor(Color.RED);
            Button notClicked = (Button) findViewById(organizers.size() - 1);
            notClicked.setTextColor(Color.BLACK);

            String organizer = organizers.get(organizers.size() - 1);

            // Query find the organizer of the event
            ParseQuery<ParseObject> eventQuery = ParseQuery.getQuery("EventsTable");
            eventQuery.whereEqualTo("organizer", organizer);
            eventQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    for(int i = 0; i < objects.size(); i++){
                        // Update attending to false
                        objects.get(i).put("attending", false);
                        objects.get(i).saveInBackground();
                    }
                }
            });

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_event, menu);
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
}
