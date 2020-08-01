package com.andycraft.tech2day.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.andycraft.tech2day.R;

/**
 * Created by Manabendu on 2019-07-20
 */
@SuppressLint("AppCompatCustomView")
public class LatoLight extends TextView {

    public LatoLight(Context context) {
        super(context);
    }

    public LatoLight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public LatoLight(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.LatoLight);
        String customFont = a.getString(R.styleable.LatoLight_LatoLightCustomFont);
        setCustomFont(context, customFont);
        a.recycle();
    }

    @SuppressWarnings("UnusedReturnValue")
    private boolean setCustomFont(Context ctx, String asset) {
        try {
            Typeface tf;
            tf = Typeface.createFromAsset(ctx.getAssets(), "fonts/Lato-Light.ttf");

            if (tf != null) {
                setTypeface(tf);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("", "Could not get typeface: " + e.getMessage());
            return false;
        }

        return true;
    }
}
