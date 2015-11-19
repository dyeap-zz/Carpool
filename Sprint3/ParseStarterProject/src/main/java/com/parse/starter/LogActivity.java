package com.parse.starter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class LogActivity extends ActionBarActivity {
    private Context context = this;

    /* Columns to display for the table */
    int cols = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        /* Displays username*/
        final String username = DataHolder.getInstance().getUsername();
        TextView textElement = (TextView) findViewById(R.id.username);
        textElement.setText(username);

        /* Create Table for view log */
        final TableLayout table_layout = (TableLayout) findViewById(R.id.tableLayout);

        // Query database
        // Compares user1 with username
        ParseQuery<ParseObject> user1_query = ParseQuery.getQuery("DrivingTable");
        user1_query.whereEqualTo("user1", username);
        // Compares user2 with username
        ParseQuery<ParseObject> user2_query = ParseQuery.getQuery("DrivingTable");
        user2_query.whereEqualTo("user2", username);
        // Store first two queries in a list
        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(user1_query);
        queries.add(user2_query);
        // Perform multiple queries
        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
        mainQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    int i = 0;
                    /* Iterate through each row in database */
                    while (i < objects.size()) {
                        /* Create row for table for each friends */
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
                                String friend_username = objects.get(i).getString("user2");
                                if(friend_username.equals(username)){
                                    friend_username = objects.get(i).getString("user1");
                                }
                                tv.setText(friend_username);
                                tv.setTextColor(Color.WHITE);
                                row.addView(tv);
                            } else if (j == 2) {
                                /* Time owed */
                                int time = objects.get(i).getInt("time");
                                tv.setText(Integer.toString(time));
                                tv.setTextColor(Color.WHITE);
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
