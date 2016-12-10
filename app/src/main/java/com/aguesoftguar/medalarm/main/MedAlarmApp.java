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

import android.app.Application;
import android.util.Log;

import com.aguesoftguar.medalarm.BuildConfig;
import com.google.firebase.crash.FirebaseCrash;

import timber.log.Timber;

/**
 * {@link Application} used for the initial configuration.
 */
public class MedAlarmApp extends Application {

   @Override
   public void onCreate() {
      super.onCreate();

      Timber.uprootAll();

      if (BuildConfig.DEBUG) {
         Timber.plant(new Timber.DebugTree());
      } else {
         //TODO: 08/12/2016 Create a Timber tree for saving all the INFO, WARN and ERROR logs in
         //a file
         Timber.plant(new CrashReportingTree());
      }
   }

   /**
    * A tree which logs important information for crash reporting.
    */
   private static class CrashReportingTree extends Timber.Tree {
      @Override
      protected void log(int priority, String tag, String message, Throwable t) {
         if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
         }

         FirebaseCrash.logcat(priority, tag, message);

         if (t != null) {
            if (priority == Log.ERROR || priority == Log.WARN) {
               FirebaseCrash.report(t);
            }
         }
      }
   }
}
