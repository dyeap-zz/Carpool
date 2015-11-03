package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class LogActivity extends ActionBarActivity {

    ParseObject drivingTable = DataHolder.getInstance().getData();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        /*
        //Set random values into the table
        drivingTable.put("username", "Mario");
        drivingTable.saveInBackground();
        drivingTable.put("username", "Luigi");
        drivingTable.saveInBackground();
        */

        //Display table from Parse
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DrivingTable");
        query.whereExists("username");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    int i = 0;
                    while(i < objects.size()) {
                        String username = objects.get(i++).getString("username");
                        System.out.println("username: " + username);
                        final TextView textElement = (TextView) findViewById(R.id.modify_text);
                        textElement.setText(username);
                    }
                }
            }
         });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log, menu);
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
