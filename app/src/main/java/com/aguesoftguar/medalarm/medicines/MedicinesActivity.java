/*
 * Copyright 2016, Agüesoftgüar
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

package com.aguesoftguar.medalarm.medicines;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aguesoftguar.medalarm.R;
import com.aguesoftguar.medalarm.util.ActivityUtils;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Main {@link android.app.Activity} of the MedAlarm application.
 */
public class MedicinesActivity extends AppCompatActivity {

   private static final String TAG = "MedicinesActivity";

   private FirebaseAnalytics firebaseAnalytics;

   private MedicinesPresenter medicinesPresenter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      // Firebase initialization
      firebaseAnalytics = FirebaseAnalytics.getInstance(this);

      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      MedicinesFragment medicinesFragment =
         (MedicinesFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
      if (medicinesFragment == null) {
         // Create the fragment
         medicinesFragment = MedicinesFragment.newInstance();
         ActivityUtils.addFragmentToActivity(
            getSupportFragmentManager(), medicinesFragment, R.id.content_frame);
      }

      // Create the presenter
      medicinesPresenter = new MedicinesPresenter(medicinesFragment);
   }

   @Override
   protected void onResume() {
      super.onResume();

      // Send firebase open application event
      firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, new Bundle());
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menu_main, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case R.id.action_about:
            // TODO Go to the about activity
            return true;
      }
      return super.onOptionsItemSelected(item);
   }
}
