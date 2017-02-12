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

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.aguesoftguar.medalarm.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
public class AddEditMedicineFragment extends Fragment implements AddEditMedicineContract.View {

   public static final String ARGUMENT_EDIT_MEDICINE_ID = "EDIT_MEDICINE_ID";

   @BindView(R.id.add_patient_textview) TextView addPatientTextView;
   @BindView(R.id.initial_date_day_textview) TextView initialDateDayTextView;
   @BindView(R.id.initial_date_hour_textview) TextView initialDateHourTextView;
   @BindView(R.id.add_doses_interval_textview) TextView addDosesIntervalTextView;
   @BindView(R.id.chronic_medicine_switch) Switch chronicMedicineSwitch;
   @BindView(R.id.final_date_day_textview) TextView finalDateDayTextView;
   @BindView(R.id.final_date_hour_textview) TextView finalDateHourTextView;
   @BindView(R.id.select_alarm_radiobutton) RadioButton selectAlarmRadioButton;
   @BindView(R.id.select_notification_radiobutton) RadioButton selectNotificationRadioButton;
   @BindView(R.id.select_color_radiobutton) RadioButton selectColorRadioButton;
   @BindView(R.id.select_image_radiobutton) RadioButton selectImageRadioButton;

   private AddEditMedicineContract.Presenter presenter;

   /**
    *
    */
   public AddEditMedicineFragment() {
      // Required empty public constructor
   }

   /**
    * Create a new instance of the {@link AddEditMedicineFragment}.
    *
    * @return New {@link AddEditMedicineFragment} object.
    */
   public static AddEditMedicineFragment newInstance() {
      return new AddEditMedicineFragment();
   }

   @Override public void onResume() {
      super.onResume();
      presenter.start();
   }

   @Override public void setPresenter(@NonNull AddEditMedicineContract.Presenter presenter) {
      this.presenter = checkNotNull(presenter);
   }

   @Nullable
   @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_add_and_edit_medicine, container, false);
      ButterKnife.bind(this, rootView);

      // Get the current date
      Date date = new Date();
      final Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);

      // Initialize date and hour formats using for the texts
      final DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault());
      final DateFormat hourFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

      // Set the current date in the initial date text and show a date picker dialog when clicking it
      initialDateDayTextView.setText(dateFormat.format(date));
      initialDateDayTextView.setOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View v) {
            DatePickerDialog dialog = new DatePickerDialog(AddEditMedicineFragment.this.getContext(),
               new DatePickerDialog.OnDateSetListener() {
                  @Override
                  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                     Calendar newCalendar = Calendar.getInstance();
                     newCalendar.set(year, month, dayOfMonth);
                     initialDateDayTextView.setText(dateFormat.format(newCalendar.getTime()));
                  }
               }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
         }
      });

      // Set the current hour in the initial hour text and show a date picker dialog when clicking it
      initialDateHourTextView.setText(hourFormat.format(date));
      initialDateHourTextView.setOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View v) {
            TimePickerDialog dialog = new TimePickerDialog(AddEditMedicineFragment.this.getContext(),
               new TimePickerDialog.OnTimeSetListener() {
                  @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                     Calendar newCalendar = Calendar.getInstance();
                     newCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                     newCalendar.set(Calendar.MINUTE, minute);
                     initialDateHourTextView.setText(hourFormat.format(newCalendar.getTime()));
                  }
               }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true); // True for 24 hour time
            dialog.show();
         }
      });

      // Disable final date and hour when checking the chronic switch
      chronicMedicineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ((View) finalDateDayTextView.getParent()).setVisibility(isChecked ? View.GONE : View.VISIBLE);
         }
      });

      // Set the current date in the final date text and show a date picker dialog when clicking it
      finalDateDayTextView.setText(dateFormat.format(date));
      finalDateDayTextView.setOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View v) {
            DatePickerDialog dialog = new DatePickerDialog(AddEditMedicineFragment.this.getContext(),
               new DatePickerDialog.OnDateSetListener() {
                  @Override
                  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                     Calendar newCalendar = Calendar.getInstance();
                     newCalendar.set(year, month, dayOfMonth);
                     finalDateDayTextView.setText(dateFormat.format(newCalendar.getTime()));
                  }
               }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
         }
      });

      // Set the current hour in the final hour text and show a date picker dialog when clicking it
      finalDateHourTextView.setText(hourFormat.format(date));
      finalDateHourTextView.setOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View v) {
            TimePickerDialog dialog = new TimePickerDialog(AddEditMedicineFragment.this.getContext(),
               new TimePickerDialog.OnTimeSetListener() {
                  @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                     Calendar newCalendar = Calendar.getInstance();
                     newCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                     newCalendar.set(Calendar.MINUTE, minute);
                     finalDateHourTextView.setText(hourFormat.format(newCalendar.getTime()));
                  }
               }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true); // True for 24 hour time
            dialog.show();
         }
      });

      // Implement alarm/notification radio group behavior
      selectAlarmRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            selectNotificationRadioButton.setChecked(!isChecked);
         }
      });

      selectNotificationRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            selectAlarmRadioButton.setChecked(!isChecked);
         }
      });

      // Implement color/image radio group behavior
      selectColorRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            selectImageRadioButton.setChecked(!isChecked);
         }
      });

      selectImageRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            selectColorRadioButton.setChecked(!isChecked);
         }
      });

      setHasOptionsMenu(true);
      return rootView;
   }

   @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      inflater.inflate(R.menu.add_and_edit_medicine_settings, menu);
   }

   @Override public boolean isActive() {
      return isAdded();
   }

}