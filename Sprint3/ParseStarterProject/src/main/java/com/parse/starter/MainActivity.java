/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /* Called when user clicks "Sign In" button */
    public void SignIn(View view){
        final Intent intent = new Intent(this, AccountActivity.class);

        //display error message if password is incorrect
        CharSequence text = "The username or password is incorrect!";
        int duration = Toast.LENGTH_LONG;
        final Toast toast = Toast.makeText(this, text, duration);

        /* Retrieve username and password */
        EditText text_username = (EditText) findViewById(R.id.enter_user);
        EditText text_password = (EditText) findViewById(R.id.enter_password);
        final String username = text_username.getText().toString();
        String password = text_password.getText().toString();

        //Ensures there are no special characters in username, to protect from sql injection.
        for(int i =0; i<username.length(); i++){
            if(!((username.charAt(i) >= '0' && username.charAt(i) <= '9')
                    || (username.charAt(i) >= 'a' && username.charAt(i) <= 'z')) ){
                CharSequence error = "Please enter the correct username.";
                final Toast message = Toast.makeText(this, error, duration);
                message.show();
                return;
            }
        }

        //Ensures the same for password
        for(int i =0; i<password.length(); i++){
            if(!((password.charAt(i) >= '0' && password.charAt(i) <= '9')
                    || (password.charAt(i) >= 'a' && password.charAt(i) <= 'z')) ){
                CharSequence error = "Please enter the correct password.";
                final Toast message = Toast.makeText(this, error, duration);
                message.show();
                return;
            }
        }

        /* Checks Parse User table for sign in */
        ParseUser.logInInBackground(username, password, new LogInCallback() {

            @Override
            public void done(ParseUser user, ParseException e) {

                if (user != null) {
                    DataHolder.getInstance().setUsername(username);
                    startActivity(intent);
                } else {
                    toast.show();
                }
            }
        });
    }

    /* Called when user clicks "Create Account" button */
    public void ToCreateAcc(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    int count = 0;
    //Click twice to exit the application.
    @Override
    public void onBackPressed(){
         count++;
         //displays if user wants to exit app
        CharSequence text = "To exit click once more.";
        int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(this, text, duration);
        //first time click
        if(count % 2 == 1){
            toast.show();
        }
        //second time click
        else if(count % 2 == 0) {
                finish();
                System.exit(0);
        }
        else{
            throw new RuntimeException("System Error");
        }
    }
}
