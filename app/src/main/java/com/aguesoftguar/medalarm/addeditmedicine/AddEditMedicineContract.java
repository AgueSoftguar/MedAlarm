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

import com.aguesoftguar.medalarm.BasePresenter;
import com.aguesoftguar.medalarm.BaseView;
import com.aguesoftguar.medalarm.data.Medicine;
import com.aguesoftguar.medalarm.data.Patient;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
@SuppressWarnings("javadoctype")
public interface AddEditMedicineContract {

   interface View extends BaseView<Presenter> {

      boolean isActive();

      void loadPatients(List<String> keys, List<Patient> patients);

      void addPatient(String key, Patient patient);

      void addMedicine();

   }

   interface Presenter extends BasePresenter {

      void saveMedicine(Medicine medicine);

      void savePatient(String name, String photo);
   }
}