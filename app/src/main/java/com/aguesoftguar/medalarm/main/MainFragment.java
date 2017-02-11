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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aguesoftguar.medalarm.R;
import com.aguesoftguar.medalarm.main.addeditmedicine.AddEditMedicineActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The main {@link Fragment} of the application.
 */
public class MainFragment extends Fragment implements MainContract.View {

   private MainContract.Presenter mPresenter;

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
   public void setPresenter(@NonNull MainContract.Presenter presenter) {
      mPresenter = checkNotNull(presenter);
   }

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View root = inflater.inflate(R.layout.fragment_main, container, false);

      FloatingActionButton fab =
         (FloatingActionButton) getActivity().findViewById(R.id.add_medicine_fab);
      fab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            mPresenter.addNewMedicine();
         }
      });

      setHasOptionsMenu(true);
      return root;
   }

   @Override public void showAddMedicine() {
      Intent intent = new Intent(getContext(), AddEditMedicineActivity.class);
      startActivity(intent);
   }
}
