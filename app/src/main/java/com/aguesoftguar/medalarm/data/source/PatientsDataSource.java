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

import android.support.annotation.NonNull;

import com.aguesoftguar.medalarm.data.Medicine;
import com.aguesoftguar.medalarm.data.Patient;

import java.util.List;

/**
 * Main entry point for accessing patients data.
 */
public interface PatientsDataSource {

   /**
    * Callbacks to load patients.
    */
   interface LoadPatientsCallback {

      void onPatientsLoaded(List<String> keys, List<Patient> patients);

      void onDataNotAvailable(String errorDescription);
   }

   /**
    * Callbacks to save a patient.
    */
   interface CreatePatientCallback {

      void onPatientSaved(String key, Patient patient);

      void onSaveFailed(String errorDescription);
   }

   /**
    * Callbacks to save a medicine.
    */
   interface CreateMedicineCallback {

      void onMedicineSaved(Medicine medicine);

      void onSaveFailed(String errorDescription);
   }

   /**
    * Gets all patients from
    * {@link  <a href="https://firebase.google.com/docs/database/?hl=es">the realtime database</a>}.
    * <p>
    * Note: {@link LoadPatientsCallback#onDataNotAvailable(String errorDescription)}
    * is fired if data source fail to get the data.
    */
   void getPatients(@NonNull LoadPatientsCallback callback);

   /**
    * Save a patient into
    * {@link  <a href="https://firebase.google.com/docs/database/?hl=es">the realtime database</a>}.
    * @param patient {@link Patient} to save.
    * <p>
    * Note: {@link CreatePatientCallback#onSaveFailed(String errorDescription)}
    *                is fired if fail to save the patient.
    */
   void savePatient(@NonNull Patient patient, @NonNull CreatePatientCallback callback);

   /**
    * Save a medicine into
    * {@link  <a href="https://firebase.google.com/docs/database/?hl=es">the realtime database</a>}.
    * @param medicine {@link Medicine} to save.
    * <p>
    * Note: {@link CreateMedicineCallback#onSaveFailed(String errorDescription)}
    *                is fired if fail to save the medicine.
    */
   void saveMedicine(@NonNull String patientId, @NonNull Medicine medicine,
                     @NonNull CreateMedicineCallback callback);

}
