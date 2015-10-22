package com.cmps115.www.mainscreen;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;



import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.sql.*;
import java.util.concurrent.ExecutionException;

public class ViewLog extends AppCompatActivity{
    private TextView text;
    private String hi;
    private JSONObject jobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hi = null;
        try {
            String res = new loadInfoOnline().execute().get();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(hi);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TableLayout table = (TableLayout)findViewById(R.id.tableLog);
        TableRow row = new TableRow(this);

        row = new TableRow(this);
        text = new TextView(this);
        text.setText(hi);
        row.addView(text);
        table.addView(row);
        getSupportActionBar().setTitle(hi);
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }
    class loadInfoOnline extends AsyncTask<String, String, String>{
        protected String doInBackground(String... args){
            try {
                URL myURL = new URL("http://drivetrack.freetzi.com/index1.php");
                HttpURLConnection urlConnect = (HttpURLConnection) myURL.openConnection();
                urlConnect.setRequestMethod("GET");
                urlConnect.setRequestProperty("Content-length","0");
                urlConnect.setUseCaches(false);
                urlConnect.connect();
                int status = urlConnect.getResponseCode();
                InputStream is = urlConnect.getInputStream();
                if(is!=null){
                    StringBuilder sb = new StringBuilder();
                    String line;
                    try {
                        BufferedReader r1 = new BufferedReader(new InputStreamReader(
                                is));
                        while ((line = r1.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                    } finally {
                        is.close();
                    }
                    hi = sb.toString();
                    try {
                        jobj = new JSONObject(hi);
                    }catch (JSONException j){
                        System.out.println(j.getMessage());
                    }
                }
                //int responseCode = urlConnect.getResponseCode();
            }
            catch (MalformedURLException e){

            }
            catch(IOException e){

            }

            return null;
        }
        protected void onPostExecute(String result) {
            text.setText(result);
        }
        protected void onPreExecute(){

        }
        protected void onProgressUpdate(Void ... values){

        }
    }
}
