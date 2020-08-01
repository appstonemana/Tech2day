package com.andycraft.tech2day.utils;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.andycraft.tech2day.R;

/**
 * Created by Manabendu on 2019-07-23
 */
public class ThemeColorizer {

    public static void seViewTextColor(Context context, TextView view, boolean isDarkMode){
        if(isDarkMode){
            view.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.white, null));
        }else{
            view.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.black, null));
        }
    }

    public static void setViewEditTextColor(Context context, EditText view, boolean isDarkMode){
        if(isDarkMode){
            view.setTextColor(context.getResources().getColor(R.color.white));
        }else{
            view.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    public static void setViewBackgroundColor(Context context, View view, boolean isDarkMode){
        if(isDarkMode){
            view.setBackgroundColor(context.getResources().getColor(R.color.grey_black));
        }else{
            view.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    public static void setSpeekerBackgroundColor(Context context, ImageView view, boolean isDarkMode){
        if(isDarkMode){
            view.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_volume_up_white_24dp));
        }else{
            view.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_volume_up_black_24dp));
        }
    }
}
