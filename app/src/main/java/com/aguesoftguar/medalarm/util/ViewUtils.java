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

package com.aguesoftguar.medalarm.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;

/**
 * Some animation and view utilities.
 */
public class ViewUtils {

   /**
    * Animate visible with alpha. Duration will be 0 if view is already visible.
    */
   public static ObjectAnimator createToVisibleAnim(final View view, int duration) {
      if (view.getVisibility() == View.VISIBLE) duration = 0;
      view.setVisibility(View.VISIBLE);
      view.setAlpha(0);
      ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(view, View.ALPHA, 1);
      alphaAnim.setInterpolator(new FastOutSlowInInterpolator());
      alphaAnim.setDuration(duration);
      return alphaAnim;
   }

   /**
    * Animate invisible / gone. Duration will be 0 if view is already invisible.
    */
   public static ObjectAnimator createToInvisibleAnim(final View view, int duration, final int endVisibility) {
      if (view.getVisibility() == View.GONE || view.getVisibility() == View.INVISIBLE) duration = 0;
      view.setEnabled(false);
      final float startingAlpha = view.getAlpha();
      ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(view, View.ALPHA, startingAlpha, 0);
      alphaAnim.addListener(new AnimatorListenerAdapter() {
         @Override
         public void onAnimationEnd(Animator animation) {
            view.setVisibility(endVisibility);
            view.setAlpha(startingAlpha);
            view.setEnabled(true);
         }
      });
      alphaAnim.setInterpolator(new FastOutSlowInInterpolator());
      alphaAnim.setDuration(duration);
      return alphaAnim;
   }
}
