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

package com.aguesoftguar.medalarm.views.colorpicker;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.aguesoftguar.medalarm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A color picker custom view which creates a grid of {@link ColorPickerItemView}s. The number of
 * view per row (and the padding between the views) is determined by the user.
 */
public class ColorPickerPaletteView extends TableLayout {

   public static final int SIZE_LARGE = 1;
   public static final int SIZE_SMALL = 2;

   private String description;
   private String descriptionSelected;

   private int itemLength;
   private int marginSize;
   private int numColumns;

   private int selectedColor;

   private List<ColorPickerItemView> items;

   public ColorPickerPaletteView(Context context, AttributeSet attrs) {
      super(context, attrs);
   }

   public ColorPickerPaletteView(Context context) {
      super(context);
   }

   /**
    * Initialize the size, columns, and listener. Size should be a pre-defined size ({@link #SIZE_LARGE}
    * or {@link #SIZE_SMALL}).
    *
    * @param size    Size of the {@link ColorPickerItemView}.
    * @param columns Number of columns for the grid.
    */
   public void init(int size, int columns) {
      numColumns = columns;
      Resources res = getResources();
      if (size == SIZE_LARGE) {
         itemLength = res.getDimensionPixelSize(R.dimen.color_item_large);
         marginSize = res.getDimensionPixelSize(R.dimen.color_item_margins_large);
      } else {
         itemLength = res.getDimensionPixelSize(R.dimen.color_item_small);
         marginSize = res.getDimensionPixelSize(R.dimen.color_item_margins_small);
      }

      description = res.getString(R.string.color_item_description);
      descriptionSelected = res.getString(R.string.color_item_description_selected);
   }

   public int getSelectedColor() {
      return selectedColor;
   }

   /**
    * Create a layout that arranges its children horizontally.
    *
    * @return New {@link TableRow}.
    */
   private TableRow createTableRow() {
      TableRow row = new TableRow(getContext());
      ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
         LayoutParams.WRAP_CONTENT);
      row.setLayoutParams(params);
      return row;
   }

   /**
    * Call {@link #drawPalette(int[], int, String[])} without a list of content descriptions.
    */
   public void drawPalette(int[] colors, int selectedColor) {
      drawPalette(colors, selectedColor, null);
   }

   /**
    * Add {@link ColorPickerItemView}s to the table view taking into account the number of
    * columns defined by the user.
    *
    * @param colors                   Array with the color the palette should contains.
    * @param selectedColor            The current selected color. This will change when the user
    *                                 click on other color item.
    * @param colorContentDescriptions String descriptions for all the {@link ColorPickerItemView}s.
    */
   public void drawPalette(int[] colors, int selectedColor, String[] colorContentDescriptions) {
      if (colors == null) return;

      this.items = new ArrayList<>();
      this.removeAllViews();
      int tableElements = 0;
      int rowElements = 0;

      // Fill the table with items based on the array of colors.
      TableRow row = createTableRow();
      for (int color : colors) {
         ColorPickerItemView colorItem = createColorItem(color, selectedColor);
         setItemDescription(tableElements, color == selectedColor, colorItem, colorContentDescriptions);
         items.add(colorItem);
         addItemToRow(row, colorItem);

         tableElements++;
         rowElements++;
         if (rowElements == numColumns) {
            addView(row);
            row = createTableRow();
            rowElements = 0;
         }
      }

      // Create blank views to fill the row if the last row has not been filled.
      if (rowElements > 0) {
         while (rowElements != numColumns) {
            addItemToRow(row, createBlankSpace());
            rowElements++;
         }
         addView(row);
      }
   }

   /**
    * Select a item based on its color.
    *
    * @param selectedColor Color of the item to be selected.
    */
   private void selectItem(int selectedColor) {
      this.selectedColor = selectedColor;
      for (ColorPickerItemView item : items) {
         item.setChecked(item.getColor() == selectedColor);
      }
   }

   /**
    * Append a color item to the end of the row.
    */
   private static void addItemToRow(TableRow row, View item) {
      row.addView(item);
   }

   /**
    * Add a content description to the specified item view.
    *
    * @param index               Position of the item.
    * @param selected            True if current item is selected. False otherwise.
    * @param item                A {@link ColorPickerItemView}.
    * @param contentDescriptions An array of content descriptions. This can be null.
    */
   private void setItemDescription(int index, boolean selected, View item, String[] contentDescriptions) {
      String description;
      if (contentDescriptions != null && contentDescriptions.length > index) {
         description = contentDescriptions[index];
      } else {
         int accessibilityIndex = index + 1;

         if (selected) {
            description = String.format(this.descriptionSelected, accessibilityIndex);
         } else {
            description = String.format(this.description, accessibilityIndex);
         }
      }
      item.setContentDescription(description);
   }

   /**
    * Create a blank space to fill the row.
    *
    * @return A blank {@link ImageView}.
    */
   private ImageView createBlankSpace() {
      ImageView view = new ImageView(getContext());
      TableRow.LayoutParams params = new TableRow.LayoutParams(itemLength, itemLength);
      params.setMargins(marginSize, marginSize, marginSize, marginSize);
      view.setLayoutParams(params);
      return view;
   }

   /**
    * Create a {@link ColorPickerItemView} for a given color.
    *
    * @param color         Color for the item view.
    * @param selectedColor Current selected color for the palette.
    * @return A new {@link ColorPickerItemView} for a given color.
    */
   private ColorPickerItemView createColorItem(final int color, int selectedColor) {
      ColorPickerItemView view = new ColorPickerItemView(getContext(), color,
         color == selectedColor);
      view.setOnClickListener(new OnClickListener() {
         @Override public void onClick(View v) {
            selectItem(color);
         }
      });
      TableRow.LayoutParams params = new TableRow.LayoutParams(itemLength, itemLength);
      params.setMargins(marginSize, marginSize, marginSize, marginSize);
      view.setLayoutParams(params);
      return view;
   }
}
