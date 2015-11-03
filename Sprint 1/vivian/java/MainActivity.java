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

import com.parse.ParseAnalytics;


public class MainActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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
   /* Called when user clicks "View Event" button */
  public void ViewEvent(View view){
    Intent intent = new Intent(this, EventActivity.class);
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
     Intent intent = new Intent(this, SignOutActivity.class);
     startActivity(intent);
   }
}
