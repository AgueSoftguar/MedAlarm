/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aguesoftguar.medalarm.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aguesoftguar.medalarm.ActivityUtils;
import com.aguesoftguar.medalarm.R;
import com.aguesoftguar.medalarm.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Main {@link android.app.Activity} of the MedAlarm application.
 */
public class MainActivity extends AppCompatActivity {

   private static final String TAG = "MainActivity";

   private FirebaseAnalytics firebaseAnalytics;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      Log.i(TAG, "Begin");

      // Firebase initialization
      firebaseAnalytics = FirebaseAnalytics.getInstance(this);

      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               .setAction("Action", null).show();
         }
      });

      MainFragment tasksFragment =
         (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
      if (tasksFragment == null) {
         // Create the fragment
         tasksFragment = MainFragment.newInstance();
         ActivityUtils.addFragmentToActivity(
            getSupportFragmentManager(), tasksFragment, R.id.contentFrame);
      }

      Log.i(TAG, "Return");
   }

   @Override
   protected void onResume() {
      super.onResume();

      Log.i(TAG, "Begin");

      // Send firebase open application event
      firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, new Bundle());

      Log.i(TAG, "Return");
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
}
