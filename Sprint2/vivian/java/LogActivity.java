package com.parse.starter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class LogActivity extends ActionBarActivity {

    private Context context = this;

    /* Declaring table*/
    //ParseObject drivingTable = DataHolder.getInstance().getData();

    /* Number of columns to display for the log */
    int cols = 3;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        //Set random values into the table
        //drivingTable.put("username", "Yoshi");
        //drivingTable.saveInBackground();

        /* Create Table for view log */
        final TableLayout table_layout = (TableLayout) findViewById(R.id.tableLayout);

        // Query database
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DrivingTable");
        query.whereExists("username");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    int i = 0;
                    /* Iterate through each row in database */
                    while(i < objects.size()) {
                        /* Create row for table for each friends */
                        TableRow row = new TableRow(context);
                        row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                                LayoutParams.WRAP_CONTENT));
                        for(int j = 0; j < cols; j++) {
                            /* Create column for each row (information for each row) */
                            TextView tv = new TextView(context);
                            tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                                    LayoutParams.WRAP_CONTENT));
                            tv.setPadding(25, 25, 25, 25);
                            if(j == 0) {
                                /* Number each entry */
                                tv.setText(Integer.toString(i));
                                row.addView(tv);
                            }else if(j == 1) {
                                /* Username of friend */
                                String username = objects.get(i).getString("username");
                                tv.setText(username);
                                row.addView(tv);
                            } else if (j == 2){
                                /* Time owed */
                                int time = objects.get(i).getInt("time");
                                tv.setText(Integer.toString(time));
                                row.addView(tv);
                            }
                        }
                        i++;
                        table_layout.addView(row);
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
