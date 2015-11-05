package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.prase.starter.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        /* Retrieve username and password */
        EditText text_username = (EditText) findViewById(R.id.enter_user);
        EditText text_password = (EditText) findViewById(R.id.enter_password);
        final String username = text_username.getText().toString();
        final String password = text_password.getText().toString();
        System.out.println("username: " + username);
        System.out.println("password: " + password);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("DrivingTable");
        query.whereEqualTo("username", username);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    System.out.println("User not found");
                } else {
                    String pw = (String) object.get("password");
                    if (pw.equals(password)) {
                        startActivity(intent);
                        intent.putExtra(EXTRA_MESSAGE, username);
                        System.out.println("sending username: " + username);
                    } else {
                        System.out.println("Incorrect Password");
                        System.out.println("password in database: " + pw);
                    }

                }
            }
        });
    }

    /* Called when user clicks "Create Account" button */
    public void CreateAcc(View view){
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
    }
}
