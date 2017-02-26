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

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.aguesoftguar.medalarm.R;
import com.aguesoftguar.medalarm.data.Patient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

   private PatientsAdapter patientsAdapter;
   private AlertDialog selectPatientDialog;

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

   @Override public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      patientsAdapter = new PatientsAdapter(new ArrayList<Patient>(0),
         new PatientsAdapter.PatientItemListener() {
            @Override public void onPatientClick(Patient clickedPatient) {
               addPatientTextView.setText(clickedPatient.getName());
               selectPatientDialog.dismiss();
            }
         });
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

      // Show a dialog to select the patient when clicking the proper text view
      addPatientTextView.setOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View v) {
            showSelectPatientDialog();
         }
      });

      // Show a dialog to select the doses interval when clicking the proper text view
      addDosesIntervalTextView.setOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View v) {
            showSelectDosesIntervalDialog();
         }
      });

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

   /**
    * Create a custom {@link AlertDialog} to select a patient and show it.
    */
   public void showSelectPatientDialog() {

      LayoutInflater inflater = LayoutInflater.from(AddEditMedicineFragment.this.getContext());
      View contentView = inflater.inflate(R.layout.dialog_add_patient, null);
      View footerView = inflater.inflate(R.layout.dialog_add_patient_footer, null);

      final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddEditMedicineFragment.this.getContext());
      dialogBuilder.setTitle(getString(R.string.add_patient_title));
      dialogBuilder.setView(contentView);

      selectPatientDialog = dialogBuilder.create();

      ListView patientsList = (ListView) contentView.findViewById(R.id.patients_listview);
      patientsList.addFooterView(footerView);
      patientsList.setAdapter(patientsAdapter);
      footerView.setOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View v) {
            showAddNewPatientDialog();
            selectPatientDialog.dismiss();
         }
      });

      selectPatientDialog.show();
   }

   /**
    * Create a custom {@link AlertDialog} to add a new patient and show it.
    */
   public void showAddNewPatientDialog() {
      LayoutInflater inflater = LayoutInflater.from(AddEditMedicineFragment.this.getContext());
      View contentView = inflater.inflate(R.layout.dialog_add_new_patient, null);
      final TextInputEditText patientNameEditText = (TextInputEditText) contentView.findViewById(R.id.new_patient_name_edittext);
      final TextInputLayout patientNameInputLayout = (TextInputLayout) contentView.findViewById(R.id.new_patient_name_inputlayout);

      AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddEditMedicineFragment.this.getContext());
      dialogBuilder.setTitle(getString(R.string.add_patient_title));
      dialogBuilder.setView(contentView);
      dialogBuilder.setPositiveButton(R.string.action_accept, null);
      dialogBuilder.setNegativeButton(R.string.action_cancel, null);

      final AlertDialog alertDialog = dialogBuilder.create();
      alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

         @Override
         public void onShow(final DialogInterface dialog) {

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View view) {
                  if (TextUtils.isEmpty(patientNameEditText.getText())) {
                     patientNameInputLayout.setErrorEnabled(true);
                     patientNameInputLayout.setError(getResources().getString(R.string.add_patient_name_empty_error));
                  } else {
                     patientNameInputLayout.setErrorEnabled(false);
                     presenter.savePatient(patientNameEditText.getText().toString(), null);
                     addPatientTextView.setText(patientNameEditText.getText().toString());
                     dialog.dismiss();
                  }
               }
            });

            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View view) {
                  dialog.dismiss();
               }
            });
         }
      });

      alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
      alertDialog.show();
   }

   /**
    * Create a custom {@link AlertDialog} to select a medicine doses interval.
    */
   public void showSelectDosesIntervalDialog() {

      LayoutInflater inflater = LayoutInflater.from(AddEditMedicineFragment.this.getContext());
      View contentView = inflater.inflate(R.layout.dialog_select_doses_interval, null);

      final NumberPicker firstValuePicker = (NumberPicker) contentView.findViewById(R.id.select_doses_interval_first_value);
      final NumberPicker secondValuePicker = (NumberPicker) contentView.findViewById(R.id.select_doses_interval_second_value);
      final NumberPicker thirdValuePicker = (NumberPicker) contentView.findViewById(R.id.select_doses_interval_third_value);
      final TextView hourSeparator = (TextView) contentView.findViewById(R.id.select_doses_interval_hour_separator);

      final Resources res = getResources();

      firstValuePicker.setMinValue(res.getInteger(R.integer.picker_min_hours));
      firstValuePicker.setMaxValue(res.getInteger(R.integer.picker_max_hours));

      secondValuePicker.setMinValue(res.getInteger(R.integer.picker_min_minutes));
      secondValuePicker.setMaxValue(res.getInteger(R.integer.picker_max_minutes));

      final String[] thirdValuePickerValues = {
         res.getQuantityString(R.plurals.select_doses_interval_hours, 2).toUpperCase(),
         res.getQuantityString(R.plurals.select_doses_interval_days, 2).toUpperCase(),
         res.getQuantityString(R.plurals.select_doses_interval_weeks, 2).toUpperCase(),
         res.getQuantityString(R.plurals.select_doses_interval_months, 2).toUpperCase()
      };
      thirdValuePicker.setMinValue(0);
      thirdValuePicker.setMaxValue(thirdValuePickerValues.length - 1);
      thirdValuePicker.setDisplayedValues(thirdValuePickerValues);

      thirdValuePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
         @Override
         public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            if (thirdValuePickerValues[newVal].equalsIgnoreCase(
               getResources().getQuantityString(R.plurals.select_doses_interval_hours, 2))) {
               // When HOURS option is selected, values go from 0 to 23
               firstValuePicker.setMinValue(res.getInteger(R.integer.picker_min_hours));
               firstValuePicker.setMaxValue(res.getInteger(R.integer.picker_max_hours));
               hourSeparator.setVisibility(View.VISIBLE);
               secondValuePicker.setVisibility(View.VISIBLE);
            } else if (thirdValuePickerValues[newVal].equalsIgnoreCase(
               getResources().getQuantityString(R.plurals.select_doses_interval_days, 2))) {
               // When DAYS option is selected, values go from 1 to 31
               firstValuePicker.setMinValue(res.getInteger(R.integer.picker_min_days));
               firstValuePicker.setMaxValue(res.getInteger(R.integer.picker_max_days));
               hourSeparator.setVisibility(View.GONE);
               secondValuePicker.setVisibility(View.GONE);
            } else if (thirdValuePickerValues[newVal].equalsIgnoreCase(
               getResources().getQuantityString(R.plurals.select_doses_interval_weeks, 2))) {
               // When WEEKS option is selected, values go from 1 to 20
               firstValuePicker.setMinValue(res.getInteger(R.integer.picker_min_weeks));
               firstValuePicker.setMaxValue(res.getInteger(R.integer.picker_max_weeks));
               hourSeparator.setVisibility(View.GONE);
               secondValuePicker.setVisibility(View.GONE);
            } else if (thirdValuePickerValues[newVal].equalsIgnoreCase(
               getResources().getQuantityString(R.plurals.select_doses_interval_months, 2))) {
               // When MONTH option is selected, values go from 1 to 12
               firstValuePicker.setMinValue(res.getInteger(R.integer.picker_min_months));
               firstValuePicker.setMaxValue(res.getInteger(R.integer.picker_max_months));
               hourSeparator.setVisibility(View.GONE);
               secondValuePicker.setVisibility(View.GONE);
            }
         }
      });

      final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddEditMedicineFragment.this.getContext());
      dialogBuilder.setTitle(getString(R.string.select_doses_interval_title));
      dialogBuilder.setView(contentView);
      dialogBuilder.setPositiveButton(R.string.action_accept, null);
      dialogBuilder.setNegativeButton(R.string.action_cancel, null);

      final AlertDialog alertDialog = dialogBuilder.create();
      alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

         @Override
         public void onShow(final DialogInterface dialog) {

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View view) {
                  int firstValue = firstValuePicker.getValue();
                  int secondValue = secondValuePicker.getValue();
                  String thirdValue = thirdValuePickerValues[thirdValuePicker.getValue()];

                  String textLabel = "";
                  if (thirdValue.equalsIgnoreCase(
                     res.getQuantityString(R.plurals.select_doses_interval_hours, 2))) {
                     textLabel = res.getQuantityString(R.plurals.add_medicine_add_interval_hours_text, firstValue,
                        firstValue + ":" + secondValue);
                  } else if (thirdValue.equalsIgnoreCase(
                     res.getQuantityString(R.plurals.select_doses_interval_days, 2))) {
                     textLabel = res.getQuantityString(R.plurals.add_medicine_add_interval_days_text, firstValue, firstValue);
                  } else if (thirdValue.equalsIgnoreCase(
                     res.getQuantityString(R.plurals.select_doses_interval_weeks, 2))) {
                     textLabel = res.getQuantityString(R.plurals.add_medicine_add_interval_weeks_text, firstValue, firstValue);
                  } else if (thirdValue.equalsIgnoreCase(
                     res.getQuantityString(R.plurals.select_doses_interval_months, 2))) {
                     textLabel = res.getQuantityString(R.plurals.add_medicine_add_interval_months_text, firstValue, firstValue);
                  }
                  addDosesIntervalTextView.setText(textLabel);
                  //TODO 24/02/2017 Save the value in the repository
                  dialog.dismiss();
               }
            });

            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View view) {
                  dialog.dismiss();
               }
            });
         }
      });

      alertDialog.show();
   }

   @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      inflater.inflate(R.menu.add_and_edit_medicine_settings, menu);
   }

   @Override public boolean isActive() {
      return isAdded();
   }

   @Override
   public void loadPatients(List<Patient> patients) {
      patientsAdapter.replaceData(patients);
   }

   @Override
   public void addPatient(Patient patient) {
      patientsAdapter.addItem(patient);
   }

}