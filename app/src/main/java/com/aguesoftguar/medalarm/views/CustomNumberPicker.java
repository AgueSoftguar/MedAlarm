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

package com.aguesoftguar.medalarm.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.aguesoftguar.medalarm.R;

import java.lang.reflect.Field;

/**
 * Custom {@link NumberPicker} used to override style values of the main class using
 * styleable values.
 */
public class CustomNumberPicker extends NumberPicker {

   private final int textSize;

   public CustomNumberPicker(Context context) {
      this(context, null);
   }

   public CustomNumberPicker(Context context, AttributeSet attrs) {
      super(context, attrs);

      TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
         R.styleable.CustomNumberPicker, 0, 0);

      Resources r = context.getResources();

      int dividerDistance = typedArray.getDimensionPixelSize(R.styleable.CustomNumberPicker_dividerDistance, -1);
      int dividerColor = typedArray.getColor(R.styleable.CustomNumberPicker_dividerColor, -1);
      textSize = typedArray.getDimensionPixelSize(R.styleable.CustomNumberPicker_textSize, 0);

      typedArray.recycle();

      if (dividerDistance != -1) setSelectionDividersDistance(dividerDistance);
      if (dividerColor != -1) setSelectionDividerColor(dividerColor);
   }

   /**
    * Override the number picker dividers distance using reflection to find the proper field.
    *
    * @param dividerDistance Distance between number picker dividers.
    */
   private void setSelectionDividersDistance(int dividerDistance) {
      Class<?> numberPickerClass = null;
      try {
         numberPickerClass = Class.forName("android.widget.NumberPicker");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }

      Field selectionDividersDistance = null;

      try {
         selectionDividersDistance = numberPickerClass.getDeclaredField("mSelectionDividersDistance");
      } catch (NoSuchFieldException e) {
         e.printStackTrace();
      }

      selectionDividersDistance.setAccessible(true);

      try {
         selectionDividersDistance.set(this, dividerDistance);
      } catch (IllegalAccessException | IllegalArgumentException | Resources.NotFoundException e) {
         e.printStackTrace();
      }
   }

   /**
    * Override the number picker dividers color using reflection to find the proper field.
    *
    * @param color Color to set in the dividers.
    */
   private void setSelectionDividerColor(int color) {
      Class<?> numberPickerClass = null;
      try {
         numberPickerClass = Class.forName("android.widget.NumberPicker");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }

      Field selectionDivider = null;

      try {
         selectionDivider = numberPickerClass.getDeclaredField("mSelectionDivider");
      } catch (NoSuchFieldException e) {
         e.printStackTrace();
      }

      selectionDivider.setAccessible(true);

      try {
         ColorDrawable colorDrawable = new ColorDrawable(color);
         selectionDivider.set(this, colorDrawable);
      } catch (IllegalAccessException | IllegalArgumentException | Resources.NotFoundException e) {
         e.printStackTrace();
      }
   }

   @Override
   public void addView(View child) {
      super.addView(child);
      updateView(child);
   }

   @Override
   public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
      super.addView(child, index, params);
      updateView(child);
   }

   @Override
   public void addView(View child, android.view.ViewGroup.LayoutParams params) {
      super.addView(child, params);
      updateView(child);
   }

   private void updateView(View view) {
      if (view instanceof EditText && textSize != 0) {
         ((EditText) view).setTextSize(textSize);
      }
   }

}