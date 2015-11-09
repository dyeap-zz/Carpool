package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    //Back button that will return you to the main activity
    public void back(View v){
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /* Called when user clicks "Create Account" button
 * Creates a new account for user */
    public void CreateAccount(View view){
        final Intent intent = new Intent(this, MainActivity.class);

        /* Retrieve username and password for creating a new account */
        EditText text_username = (EditText) findViewById(R.id.enter_newuser);
        EditText text_password = (EditText) findViewById(R.id.enter_newpassword);
        String username = text_username.getText().toString();
        String password = text_password.getText().toString();

        /* Create Account on Parse*/
        ParseUser user = new ParseUser();
        user.setUsername(username);
        System.out.println("Username: " + username);
        user.setPassword(password);
        System.out.println("Password: " + password);
        CharSequence textfail = "";
        CharSequence text = "Account Created for " + username;

        //If username or password is empty
        if(username.length() == 0 || password.length() == 0){
            textfail = "Please fill in a Username and Password!";
        }
        //username taken
        else {
            textfail = "Username " + username + " " + "already taken!";
        }
        int duration = Toast.LENGTH_SHORT;
        final Toast toastsuccess = Toast.makeText(this, text, duration);
        final Toast toastfail = Toast.makeText(this, textfail, duration);


        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {

                if (e == null) {
                    toastsuccess.show();
                    startActivity(intent);
                } else {
                    toastfail.show();
                }
            }
        });
    }
}
