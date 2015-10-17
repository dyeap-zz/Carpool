package com.example.danny.driving;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener()
        {
            public void onClick(View arg0)
            {
                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        login.class);
                startActivity(myIntent);
            }
        });
    }

}
