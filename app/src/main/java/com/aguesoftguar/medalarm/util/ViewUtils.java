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
