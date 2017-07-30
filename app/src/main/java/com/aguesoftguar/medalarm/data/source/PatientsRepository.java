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

package com.aguesoftguar.medalarm.data.source;

import com.aguesoftguar.medalarm.data.Medicine;
import com.aguesoftguar.medalarm.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.support.annotation.NonNull;

import com.aguesoftguar.medalarm.data.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation to load patients from
 * {@link  <a href="https://firebase.google.com/docs/database/?hl=es">firebase</a>}.
 */
public class PatientsRepository implements PatientsDataSource {

   private static final String TAG = PatientsRepository.class.getName();

   private static PatientsRepository INSTANCE = null;

   private DatabaseReference database;

   private final String patientsNode = "patients";
   private final String medicinesNode = "medicines";
   private final String patientMedicinesNode = "patient-medicines";

   // Prevent direct instantiation.
   private PatientsRepository() {
      database = FirebaseDatabase.getInstance().getReference();
   }

   /**
    * Returns the single instance of this class, creating it if necessary.
    *
    * @return the {@link PatientsRepository} instance
    */
   public static PatientsRepository getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new PatientsRepository();
      }
      return INSTANCE;
   }

   @Override
   public void getPatients(@NonNull final LoadPatientsCallback callback) {
      checkNotNull(callback);

      database.child(patientsNode).addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            List<Patient> patients = new ArrayList<>();
            List<String> keys = new ArrayList<>();

            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
               patients.add(postSnapshot.getValue(Patient.class));
               keys.add(postSnapshot.getKey().toString());
            }
            callback.onPatientsLoaded(keys, patients);
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

            callback.onDataNotAvailable(databaseError.getMessage());
         }
      });
   }

   @Override
   public void savePatient(@NonNull final Patient patient,
                             @NonNull final CreatePatientCallback callback) {
      checkNotNull(patient);
      checkNotNull(callback);

      final String key = database.child(patientsNode).push().getKey();
      Map<String, Object> childUpdates = new HashMap<>();
      childUpdates.put("/" + patientsNode + "/" + key, patient.toMap());
      database.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
         @Override
         public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
            if (databaseError == null) {
               callback.onPatientSaved(key, patient);
            } else {
               callback.onSaveFailed(databaseError.getMessage());
            }
         }
      });
   }

      @Override
      public void saveMedicine(@NonNull String patientId, @NonNull final Medicine medicine,
      @NonNull final CreateMedicineCallback callback) {

         checkNotNull(medicine);
         checkNotNull(callback);

         String key = database.child(medicinesNode).push().getKey();
         Map<String, Object> childUpdates = new HashMap<>();
         childUpdates.put("/" + patientMedicinesNode + "/" + patientId + "/" + key, medicine.toMap());
         database.updateChildren(childUpdates);
         childUpdates.clear();
         childUpdates.put("/" + medicinesNode + "/" + key, medicine.toMap());
         database.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
               if (databaseError == null) {
                  callback.onMedicineSaved(medicine);
               } else {
                  callback.onSaveFailed(databaseError.getMessage());
               }
            }
         });
   }

}
