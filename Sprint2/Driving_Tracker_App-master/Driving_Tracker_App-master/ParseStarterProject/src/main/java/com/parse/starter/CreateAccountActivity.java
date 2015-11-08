package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class CreateAccountActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_account, menu);
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

    /* Called when user clicks "Create Account" button
 * Creates a new account for user */
    public void CreateAccount(View view){
        final Intent intent = new Intent(this, MainActivity.class);

        /* Retrieve username and password for creating a new account */
        EditText text_username = (EditText) findViewById(R.id.enter_newuser);
        EditText text_password = (EditText) findViewById(R.id.enter_newpassword);
        final String username = text_username.getText().toString();
        String password = text_password.getText().toString();

        /* Create Account on Parse*/
        ParseUser user = new ParseUser();
        user.setUsername(username);
        System.out.println("Username: " + username);
        user.setPassword(password);
        System.out.println("Password: " + password);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    System.out.println("Account has been created for " + username);
                    startActivity(intent);
                } else {
                    System.out.println("Unable to create account for " + username);
                    System.out.println("Exception: " + e);
                }
            }
        });
    }
}
