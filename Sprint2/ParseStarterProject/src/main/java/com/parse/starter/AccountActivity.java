package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseUser;


public class AccountActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        /* Displays username */
        String username = DataHolder.getInstance().getUsername();
        TextView textElement = (TextView) findViewById(R.id.username);
        textElement.setText(username);
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

    /* Called when user clicks "Create an Event" button */
    public void ViewEvent(View view){
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }

    /* Called when user clicks "View Events" button */
    public void ViewViewEvents(View view){
        Intent intent = new Intent(this, ViewEventActivity.class);
        startActivity(intent);
    }

    /* Called when user clicks "View Log" button */
    public void ViewLog(View view){
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
    }

    /* Called when user clicks "Add Friend" button */
    public void AddFriend(View view){
        Intent intent = new Intent(this, FriendActivity.class);
        startActivity(intent);
    }

    /* Called when user clicks "Sign Out" button */
    public void SignOut(View view){
        Intent intent = new Intent(this, MainActivity.class);
        ParseUser.logOut();
        startActivity(intent);
    }

    //Doesn't allow the user to go back, we use a signout button
    @Override
    public void onBackPressed(){}
}
