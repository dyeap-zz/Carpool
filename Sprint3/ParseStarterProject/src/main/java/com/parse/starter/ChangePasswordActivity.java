package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ChangePasswordActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

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
    public void Update_Password(View view)
    {

        final String username = DataHolder.getInstance().getUsername();
        final EditText new_password = (EditText) findViewById(R.id.change_password);
        final EditText confirm_password = (EditText) findViewById(R.id.confirm_password);
        String np = new_password.getText().toString();
        String confirmPassword = confirm_password.getText().toString();
        if((np.length()==0) || (confirmPassword.length() == 0)){
            int duration = Toast.LENGTH_LONG;
            final Toast toast = Toast.makeText(this, "both fields cannot be empty", duration);
            toast.show();
            return;
        }
        if(np.equals(confirmPassword)==false){
            int duration = Toast.LENGTH_LONG;
            final Toast toast = Toast.makeText(this, "passwords do not match", duration);
            toast.show();
            return;
        }
        ParseUser currentUser = ParseUser.getCurrentUser();
        currentUser.setPassword(np);
        currentUser.saveInBackground();


        /*
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("username", username);



        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null)//That means there's something in the query
                {
                    String userCheck = object.get(0).getString("username");
                    System.out.printf("username = %s\n",userCheck);
                    //object.get(0).put("password",new_password);
                    //object.get(0).saveInBackground();

                } else {
                    System.out.printf("user not found");
                }
            }
        });
*/
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
