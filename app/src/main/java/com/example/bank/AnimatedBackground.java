package com.example.bank;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

/**
 * \brief класс реализует градиентный фон, который изменяется от времени суток
 *
 */
public class AnimatedBackground {
    /**
     * Функция которая задает градиентный фон
     *
     */
    public static GradientDrawable createGradient() {
        GradientDrawable gradient=new GradientDrawable();
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        Log.i("hour",Integer.toString(hour));
        if (hour >= 0 && hour < 7) {

            int[] colors = {Color.parseColor("#262e5f"),Color.parseColor("#558aaa"),Color.WHITE,Color.WHITE};
            gradient = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
            gradient.setCornerRadius(0f);

        } else if(hour >= 7 && hour < 15) {

            int[] colors = {Color.parseColor("#1aaa6f"),Color.parseColor("#44ccb6"),Color.WHITE,Color.WHITE};
            gradient = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
            gradient.setCornerRadius(0f);

        }else if (hour >= 15 && hour < 20){

            int[] colors = {Color.parseColor("#156a89"),Color.parseColor("#4eb6ad"),Color.WHITE,Color.WHITE};
            gradient = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
            gradient.setCornerRadius(0f);

        }else{
            int[] colors = {Color.parseColor("#262e5f"),Color.parseColor("#558aaa"),Color.WHITE,Color.WHITE};
            gradient = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
            gradient.setCornerRadius(0f);
        }

        return gradient;
    }
}
