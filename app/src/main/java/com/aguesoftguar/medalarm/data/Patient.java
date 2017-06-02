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

package com.aguesoftguar.medalarm.data;

import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Immutable model class for a patient.
 */
public class Patient {

   @Nullable
   private String name;

   @Nullable
   private String photo;

   public Patient() {
   }

   /**
    * Use this constructor to specify a Patient.
    *
    * @param name  name of the patient
    * @param photo photo of the patient
    */
   public Patient(@Nullable String name, @Nullable String photo) {
      this.name = name;
      this.photo = photo;
   }

   @Nullable
   public String getName() {
      return name;
   }

   @Nullable
   public String getPhoto() {
      return photo;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Patient patient = (Patient) o;
      return Objects.equal(name, patient.name) &&
         Objects.equal(photo, patient.photo);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(name, photo);
   }

   @Override
   public String toString() {
      return "Patient with name " + name;
   }

   /**
    * Returns a {@link Map} with all instance variables.
    *
    * @return A {@link Map} with all instance variables.
    */
   @Exclude
   public Map<String, Object> toMap() {
      HashMap<String, Object> result = new HashMap<>();
      result.put("name", name);
      result.put("photo", photo);

      return result;
   }
}