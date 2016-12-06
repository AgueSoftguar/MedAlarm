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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * The main {@link Fragment} of the application.
 */
public class MainFragment extends Fragment {

   public MainFragment() {
      // Requires empty public constructor
   }

   /**
    * Create a new instance of the {@link MainFragment}.
    *
    * @return New {@link MainFragment} object.
    */
   public static MainFragment newInstance() {
      return new MainFragment();
   }

   @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
   }

   @Override
   public void onResume() {
      super.onResume();
   }

}
