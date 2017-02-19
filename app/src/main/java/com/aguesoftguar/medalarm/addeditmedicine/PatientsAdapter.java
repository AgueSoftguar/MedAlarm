/*
 * Copyright 2016 Agüesoftgüar.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aguesoftguar.medalarm.addeditmedicine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aguesoftguar.medalarm.R;
import com.aguesoftguar.medalarm.data.Patient;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link BaseAdapter} used in the dialog used to show the patients list when
 * adding a new medicine.
 */
public class PatientsAdapter extends BaseAdapter {

   private List<Patient> patients;
   private PatientItemListener itemListener;

   public PatientsAdapter(List<Patient> patients, PatientItemListener patientItemListener) {
      setList(patients);
      itemListener = patientItemListener;
   }

   /**
    * Replace the current data by a new {@link Patient} list.
    *
    * @param patients New list of {@link Patient}.
    */
   public void replaceData(List<Patient> patients) {
      setList(patients);
      notifyDataSetChanged();
   }

   /**
    * Add new {@link Patient} into the current data.
    *
    * @param patient New {@link Patient}
    */
   public void addItem(Patient patient) {
      patients.add(patient);
      notifyDataSetChanged();
   }

   private void setList(List<Patient> patients) {
      this.patients = checkNotNull(patients);
   }

   @Override
   public int getCount() {
      return patients.size();
   }

   @Override
   public Patient getItem(int i) {
      return patients.get(i);
   }

   @Override
   public long getItemId(int i) {
      return i;
   }

   @Override
   public View getView(int i, View view, ViewGroup viewGroup) {
      View rowView = view;
      if (rowView == null) {
         LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
         rowView = inflater.inflate(R.layout.dialog_add_patient_row, viewGroup, false);
      }

      final Patient patient = getItem(i);

      TextView patientNameTextView = (TextView) rowView.findViewById(R.id.patient_name);
      patientNameTextView.setText(patient.getName());

      ImageView patientImageView = (ImageView) rowView.findViewById(R.id.patient_image);
      if (patient.getPhoto() != null) {
         //TODO 17/02/2017 Set the patient image
      }

      rowView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            itemListener.onPatientClick(patient);
         }
      });

      return rowView;
   }

   /**
    * Callback used to communicate with the class that handle the ListView.
    */
   public interface PatientItemListener {

      /**
       * Called when a patient of the list has been selected.
       *
       * @param clickedPatient {@link Patient} object related with the selected position.
       */
      void onPatientClick(Patient clickedPatient);
   }
}
