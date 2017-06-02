package com.aguesoftguar.medalarm.views.colorpicker;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aguesoftguar.medalarm.R;

/**
 * Create an oval shape of a specified color. When the item is checked, the item is marked
 * with a check mark.
 */
public class ColorPickerItemView extends FrameLayout {

   private int color;
   private ImageView shapeImage;
   private ImageView checkMarkImage;

   public ColorPickerItemView(Context context, int color, boolean checked) {
      super(context);
      this.color = color;

      LayoutInflater.from(context).inflate(R.layout.item_color_picker, this);
      shapeImage = (ImageView) findViewById(R.id.color_picker_item);
      checkMarkImage = (ImageView) findViewById(R.id.color_picker_checkmark);
      setColor(color);
      setChecked(checked);
   }

   /**
    * Set the color of the {@link ColorPickerItemView} using a {@link ColorStateDrawable} to use
    * different colors for the view states.
    *
    * @param color Hexadecimal color.
    */
   protected void setColor(int color) {
      Drawable[] colorDrawable = new Drawable[]
         {getContext().getResources().getDrawable(R.drawable.shape_color_picker_item, null)};
      shapeImage.setImageDrawable(new ColorStateDrawable(colorDrawable, color));
   }

   /**
    * Get the current {@link ColorPickerItemView} color.
    *
    * @return The current color.
    */
   protected int getColor() {
      return color;
   }

   /**
    * Check / uncheck the view. When the view is checked, a check mark is shown.
    *
    * @param checked True if the view will be checked. False otherwise.
    */
   protected void setChecked(boolean checked) {
      checkMarkImage.setVisibility(checked ? View.VISIBLE : View.GONE);
   }
}
