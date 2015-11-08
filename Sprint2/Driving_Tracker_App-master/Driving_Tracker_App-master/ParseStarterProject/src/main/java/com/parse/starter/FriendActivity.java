package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class FriendActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        /* Displays username */
        String username = DataHolder.getInstance().getUsername();
        TextView textElement = (TextView) findViewById(R.id.username);
        textElement.setText(username);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend, menu);
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

    public void AddFriend(View view){
        final Intent intent = new Intent(this, AccountActivity.class);

        /* Retrieve user's username */
        final String user = DataHolder.getInstance().getUsername();
        /* Retrieve username of friend */
        EditText text_friend = (EditText) findViewById(R.id.enter_friend);
        final String friend = text_friend.getText().toString();

        /* Query Friend */
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", friend);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // Create an object for the relationship in DrivingTable
                    ParseObject buddy_object = DataHolder.getInstance().getData();
                    buddy_object.put("user1", user);
                    buddy_object.put("user2", friend);
                    buddy_object.put("time", 0);
                    // Add ACL -- Both users can access
                    ParseACL groupACL = new ParseACL();
                    groupACL.setReadAccess(ParseUser.getCurrentUser(), true);
                    groupACL.setWriteAccess(ParseUser.getCurrentUser(), true);
                    groupACL.setReadAccess(objects.get(0).getObjectId(), true);
                    groupACL.setWriteAccess(objects.get(0).getObjectId(), true);
                    buddy_object.setACL(groupACL);
                    buddy_object.saveInBackground();
                    // Display message -- Friend Added
                    TextView textElement = (TextView) findViewById(R.id.finding_status);
                    textElement.setText("Friend Added -- " + friend);
                    // Return to Account page
                    startActivity(intent);
                } else {
                    System.out.println("NOT FOUND");
                    // Display message -- Unable to find friend
                    TextView textElement = (TextView) findViewById(R.id.finding_status);
                    textElement.setText("Unable to find friend -- " + friend);
                }
            }
        });

    }
}
