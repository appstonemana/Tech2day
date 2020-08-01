package com.andycraft.tech2day.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Manabendu on 2019-07-22
 */
public class App extends Application {

    private static App mInstance;
    private Context mContext;

    public App(Context context) {
        mContext = context;
    }

    public static synchronized App getInstance(Context context) {
        if (mInstance == null)
            mInstance = new App(context);
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
