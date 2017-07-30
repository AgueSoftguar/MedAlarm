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

import com.google.common.base.Objects;
import com.google.firebase.database.Exclude;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Immutable model class for a medicine.
 */
public class Medicine {

   // Required parameters
   private String name;

   private String patientUuid;

   private String initialDateDay;

   private String initialDateHour;

   private String dosesInterval;

   @Nullable
   private String finalDateDay;

   @Nullable
   private String finalDateHour;

   private int reminder;

   private int color;

   private boolean photo;

   /**
    * We have to use the Builder pattern.
    * Instead of making the desired object directly, the client calls
    * a constructor (or static factory) with all of the required parameters
    * and gets a builder object. Then the client calls setter-like methods on
    * the builder object to set each optional parameter of interest.
    * Finally, the client calls a parameterless build method to generate the object,
    * which is immutable. The builder is a static member class of the class it builds.
    */
   public static class MedicineBuilder {
      // Required parameters
      private String name;

      private String patientUuid;

      private String initialDateDay;

      private String initialDateHour;

      private String dosesInterval;

      // Optional parameters - initialized to default values
      @Nullable
      private String finalDateDay;

      @Nullable
      private String finalDateHour;

      private int reminder = -1;

      private int color = -1;

      private boolean photo = false;

      /**
       * Set the field name.
       *
       * @param name name of the medication
       * @return {@link MedicineBuilder}
       */
      public MedicineBuilder name(String name) {
         this.name = name;
         return this;
      }

      /**
       * Set the field patientUuid.
       *
       * @param patientUuid
       * @return {@link MedicineBuilder}
       */
      public MedicineBuilder patientUuid(String patientUuid) {
         this.patientUuid = patientUuid;
         return this;
      }
      /**
       * Set the field initialDateDay.
       *
       * @param initialDateDay Initial Date of the medication
       * @return {@link MedicineBuilder}
       */
      public MedicineBuilder initialDateDay(String initialDateDay) {
         this.initialDateDay = initialDateDay;
         return this;
      }
      /**
       * Set the field initialDateHour.
       *
       * @param initialDateHour Initial hour of the first medication
       * @return {@link MedicineBuilder}
       */
      public MedicineBuilder initialDateHour(String initialDateHour) {
         this.initialDateHour = initialDateHour;
         return this;
      }
      /**
       * Set the field dosesInterval.
       *
       * @param dosesInterval Final Date of the medication
       * @return {@link MedicineBuilder}
       */
      public MedicineBuilder dosesInterval(String dosesInterval) {
         this.dosesInterval = dosesInterval;
         return this;
      }

      /**
       * Set the field finalDateDay.
       * If it's empty, medication is chronic.
       *
       * @param finalDateDay Final Date of the medication
       * @return {@link MedicineBuilder}
       */
      public MedicineBuilder finalDateDay(String finalDateDay) {
         this.finalDateDay = finalDateDay;
         return this;
      }

      /**
       * Set the field finalDateHour.
       * If it's empty, medication is chronic.
       *
       * @param finalDateHour Final Hour of the last medication
       * @return {@link MedicineBuilder}
       */
      public MedicineBuilder finalDateHour(String finalDateHour) {
         this.finalDateHour = finalDateHour;
         return this;
      }

      /**
       * Set the field reminder.
       * If it's 0, notification.
       * if it's 1, alarm.
       *
       * @param reminder Type of reminder(alarm or notification)
       * @return {@link MedicineBuilder}
       */
      public MedicineBuilder reminder(int reminder) {
         this.reminder = reminder;
         return this;
      }

      /**
       * Set the field color.
       *
       * @param color of the medicine
       * @return {@link MedicineBuilder}
       */
      public MedicineBuilder color(int color) {
         this.color = color;
         return this;
      }

      /**
       * Set the field photo.
       *
       * @param photo of the medicine
       * @return {@link MedicineBuilder}
       */
      public MedicineBuilder photo(boolean photo) {
         this.photo = photo;
         return this;
      }

      /**
       * Generate the object Medicine.
       *
       * @return {@link Medicine}
       */
      public Medicine build() {
         return new Medicine(this);
      }
   }

   public Medicine(MedicineBuilder builder) {
      this.name = builder.name;
      this.patientUuid = builder.patientUuid;
      this.initialDateDay = builder.initialDateDay;
      this.initialDateHour = builder.initialDateHour;
      this.dosesInterval = builder.dosesInterval;
      this.finalDateDay = builder.finalDateDay;
      this.finalDateHour = builder.finalDateHour;
      this.reminder = builder.reminder;
      this.color = builder.color;
      this.photo = builder.photo;
   }

   public String getName() {
      return name;
   }

   public String getPatientUuid() {
      return patientUuid;
   }

   public String getInitialDateDay() {
      return initialDateDay;
   }

   public String getInitialDateHour() {
      return initialDateHour;
   }

   public String getDosesInterval() {
      return dosesInterval;
   }

   @Nullable
   public String getFinalDateDay() {
      return finalDateDay;
   }

   @Nullable
   public String getFinalDateHour() {
      return finalDateHour;
   }

   public int getReminder() {
      return reminder;
   }

   public int getColor() {
      return color;
   }

   public boolean getPhoto() {
      return photo;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Medicine patient = (Medicine) o;
      return Objects.equal(name, patient.name) &&
         Objects.equal(patientUuid, patient.patientUuid) &&
         Objects.equal(initialDateDay, patient.initialDateDay) &&
         Objects.equal(initialDateHour, patient.initialDateHour) &&
         Objects.equal(dosesInterval, patient.dosesInterval) &&
         Objects.equal(finalDateDay, patient.finalDateDay) &&
         Objects.equal(finalDateHour, patient.finalDateHour) &&
         Objects.equal(reminder, patient.reminder) &&
         Objects.equal(color, patient.color) &&
         Objects.equal(photo, patient.photo);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(name, patientUuid, initialDateDay, initialDateHour,
         dosesInterval, finalDateDay, finalDateHour, reminder, color, photo);
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
      result.put("patientUuid", patientUuid);
      result.put("initialDateDay", initialDateDay);
      result.put("initialDateHour", initialDateHour);
      result.put("dosesInterval", dosesInterval);
      result.put("finalDateDay", finalDateDay);
      result.put("finalDateHour", finalDateHour);
      result.put("reminder", reminder);
      result.put("color", color);
      result.put("photo", photo);

      return result;
   }
}
