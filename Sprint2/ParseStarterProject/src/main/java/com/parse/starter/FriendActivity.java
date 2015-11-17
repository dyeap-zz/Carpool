package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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


    boolean new_friend = false;
    public void AddFriend(View view) {


        /* Retrieve user's username */
        final String user = DataHolder.getInstance().getUsername();
        /* Retrieve username of friend */
        EditText text_friend = (EditText) findViewById(R.id.enter_friend);
        final String friend = text_friend.getText().toString();


        if (!user.equals(friend)) {
            // Query if friend relationship already exists
            ParseQuery<ParseObject> queryUser1 = ParseQuery.getQuery("DrivingTable");
            queryUser1.whereEqualTo("user1", user);
            queryUser1.whereEqualTo("user2", friend);

            ParseQuery<ParseObject> queryUser2 = ParseQuery.getQuery("DrivingTable");
            queryUser2.whereEqualTo("user1", friend);
            queryUser2.whereEqualTo("user2", user);

            List<ParseQuery<ParseObject>> queries =
                    new ArrayList<ParseQuery<ParseObject>>();
            queries.add(queryUser1);
            queries.add(queryUser2);

            ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
            mainQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (objects.size() == 0) {
                        // Query if the friend has an account
                        ParseQuery<ParseUser> query = ParseUser.getQuery();
                        query.whereEqualTo("username", friend);
                        query.findInBackground(new FindCallback<ParseUser>() {
                            @Override
                            public void done(List<ParseUser> objects, ParseException e) {
                                if (objects.size() > 0) {
                                    ParseObject buddy = new ParseObject("DrivingTable");
                                    buddy.put("user1", user);
                                    buddy.put("user2", friend);
                                    buddy.put("time", 0);
                                    // Add ACL -- Both users can access
                                    ParseACL groupACL = new ParseACL();
                                    groupACL.setReadAccess(ParseUser.getCurrentUser(), true);
                                    groupACL.setWriteAccess(ParseUser.getCurrentUser(), true);
                                    groupACL.setReadAccess(objects.get(0).getObjectId(), true);
                                    groupACL.setWriteAccess(objects.get(0).getObjectId(), true);
                                    buddy.setACL(groupACL);
                                    buddy.saveInBackground();

                                    // Message when successfully added as a friend
                                    CharSequence textSuccess = friend + " is added as a friend";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toastSuccess = Toast.makeText(FriendActivity.this,
                                            textSuccess, duration);
                                    toastSuccess.show();

                                    // Return to Account page
                                    Intent intent = new Intent(FriendActivity.this,
                                            AccountActivity.class);
                                    startActivity(intent);
                                } else {
                                    // Message when failed to add as friend
                                    CharSequence textFail = "Unable to find " + friend +
                                            " in our database. Invite your friend " +
                                            "to join Driving Tracker!";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toastFail = Toast.makeText(FriendActivity.this, textFail,
                                            duration);
                                    toastFail.show();
                                }
                            }
                        });

                    } else {
                        // Message when failed to add a friend
                        CharSequence textFail = friend +
                                " has already been added as a friend.";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toastFail = Toast.makeText(FriendActivity.this,
                                textFail, duration);
                        toastFail.show();

                        // Return to Account page
                        Intent intent = new Intent(FriendActivity.this,
                                LogActivity.class);
                        startActivity(intent);
                    }
                }
            });
        } else {
            // Message when failed to add friend
            CharSequence textFail = "Cannot add yourself as friend";
            int duration = Toast.LENGTH_SHORT;
            Toast toastFail = Toast.makeText(FriendActivity.this,
                    textFail, duration);
            toastFail.show();

            // Return to Account page
            Intent intent = new Intent(FriendActivity.this,
                    AccountActivity.class);
            startActivity(intent);
        }


    }
}
