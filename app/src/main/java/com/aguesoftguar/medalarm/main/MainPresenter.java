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

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link MainFragment}), retrieves the data and updates the
 * UI as required.
 */
public class MainPresenter implements MainContract.Presenter {

   private final MainContract.View medicinesView;

   public MainPresenter(@NonNull MainContract.View medicinesView) {
      this.medicinesView = checkNotNull(medicinesView, "tasksView cannot be null!");
      this.medicinesView.setPresenter(this);
   }

   @Override
   public void start() {
      // TODO Load medicines list from Firebase database
   }

   @Override
   public void addNewMedicine() {
      medicinesView.showAddMedicine();
   }
}
