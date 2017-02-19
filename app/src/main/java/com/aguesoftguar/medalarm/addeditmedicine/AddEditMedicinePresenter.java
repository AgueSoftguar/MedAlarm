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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aguesoftguar.medalarm.data.Medicine;
import com.aguesoftguar.medalarm.data.Patient;
import com.aguesoftguar.medalarm.data.source.PatientsDataSource;
import com.aguesoftguar.medalarm.data.source.PatientsRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link AddEditMedicineFragment}), retrieves the data and updates
 * the UI as required.
 */
public class AddEditMedicinePresenter implements AddEditMedicineContract.Presenter {

   private static final String TAG = AddEditMedicinePresenter.class.getName();

   @NonNull
   private final AddEditMedicineContract.View addMedicineView;

   @Nullable
   private String medicineId;

   @NonNull
   private final PatientsRepository patientsRepository;

   /**
    * Creates a presenter for the add/edit view.
    *
    * @param medicineId             ID of the medicine to edit or null for a new medicine.
    * @param addMedicineView        The add/edit view.
    * @param patientsRepository     a repository of data for patients.
    * @param shouldLoadDataFromRepo Whether data needs to be loaded or not (for config changes).
    */
   public AddEditMedicinePresenter(@Nullable String medicineId,
                                   @NonNull AddEditMedicineContract.View addMedicineView,
                                   @NonNull PatientsRepository patientsRepository,
                                   boolean shouldLoadDataFromRepo) {
      this.medicineId = medicineId;
      this.addMedicineView = checkNotNull(addMedicineView);
      this.patientsRepository = patientsRepository;

      // TODO Handle shouldLoadDataFromRepo variable
   }

   @Override
   public void start() {
      loadPatients();
   }

   @Override
   public void saveMedicine(Medicine medicine) {
      if (isNewMedicine()) {
         createMedicine(medicine);
      } else {
         updateMedicine();
      }
   }

   private boolean isNewMedicine() {
      return medicineId == null;
   }

   private void createMedicine(Medicine medicine) {
      patientsRepository.saveMedicine(medicine.getPatientUuid(), medicine,
         new PatientsDataSource.CreateMedicineCallback() {
         @Override
         public void onMedicineSaved(Medicine medicine) {
            Log.d(TAG, "onMedicineSaved");
         }

         @Override
         public void onSaveFailed(String errorDescription) {
            Log.e(TAG, errorDescription);
         }
      });
   }

   private void updateMedicine() {
      // TODO Handle medicine fields errors or update medicine in Firebase database
   }

   /**
    * Save a patient.
    *
    * @param name  name of the patient.
    * @param photo photo of the patient.
    */
   public void savePatient(String name, String photo) {
      Patient patient = new Patient(name, photo);
      patientsRepository.savePatient(patient, new PatientsDataSource.CreatePatientCallback() {
         @Override
         public void onPatientSaved(String key, Patient patient) {
            addMedicineView.addPatient(key, patient);
         }

         @Override
         public void onSaveFailed(String errorDescription) {
            Log.e(TAG, errorDescription);
         }
      });
   }

   /**
    * Load all patients.
    */
   public void loadPatients() {
      patientsRepository.getPatients(new PatientsDataSource.LoadPatientsCallback() {
         @Override
         public void onPatientsLoaded(List<String> keys, List<Patient> patients) {
            addMedicineView.loadPatients(keys, patients);
         }

         @Override
         public void onDataNotAvailable(String errorDescription) {
            Log.e(TAG, errorDescription);
         }
      });
   }

}
