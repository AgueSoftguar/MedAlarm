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

package com.aguesoftguar.medalarm.addeditmedicine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.aguesoftguar.medalarm.R;
import com.aguesoftguar.medalarm.data.source.PatientsRepository;
import com.aguesoftguar.medalarm.util.ActivityUtils;

/**
 * Displays an add or edit medicines screen.
 */
public class AddEditMedicineActivity extends AppCompatActivity {

   public static final String SHOULD_LOAD_DATA_FROM_REPO_KEY = "SHOULD_LOAD_DATA_FROM_REPO_KEY";

   private AddEditMedicinePresenter addEditMedicinePresenter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_add_and_edit_medicine);

      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close_white_24dp, getTheme()));
      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View v) {
            onBackPressed();
         }
      });
      getSupportActionBar().setDisplayShowTitleEnabled(false);

      AddEditMedicineFragment addEditMedicineFragment =
         (AddEditMedicineFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);

      String medicineId = getIntent().getStringExtra(AddEditMedicineFragment.ARGUMENT_EDIT_MEDICINE_ID);

      if (addEditMedicineFragment == null) {
         addEditMedicineFragment = AddEditMedicineFragment.newInstance();

         if (getIntent().hasExtra(AddEditMedicineFragment.ARGUMENT_EDIT_MEDICINE_ID)) {
            Bundle bundle = new Bundle();
            bundle.putString(AddEditMedicineFragment.ARGUMENT_EDIT_MEDICINE_ID, medicineId);
            addEditMedicineFragment.setArguments(bundle);
         }

         ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
            addEditMedicineFragment, R.id.content_frame);
      }

      boolean shouldLoadDataFromRepo = true;

      // Prevent the presenter from loading data from the repository if this is a config change.
      if (savedInstanceState != null) {
         // Data might not have loaded when the config change happen, so we saved the state.
         shouldLoadDataFromRepo = savedInstanceState.getBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY);
      }

      // Create the presenter
      addEditMedicinePresenter = new AddEditMedicinePresenter(
         medicineId,
         addEditMedicineFragment,
         PatientsRepository.getInstance(),
         shouldLoadDataFromRepo);

      addEditMedicineFragment.setPresenter(addEditMedicinePresenter);
   }

   @Override
   public boolean onSupportNavigateUp() {
      onBackPressed();
      return true;
   }
}
