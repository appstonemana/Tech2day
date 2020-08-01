package com.andycraft.tech2day.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "USER_EXITS";
    private static final String KEY_TOKEN = "token";

    private static SharedPrefManager mInstance = null;
    private SharedPreferences preferences;

    public SharedPrefManager(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null)
            mInstance = new SharedPrefManager(context);
        return mInstance;
    }

    public SharedPreferences.Editor getEditor() {
        return preferences.edit();
    }


    public void storeToken(String token) {
        setString(KEY_TOKEN, token);
    }

    public String getToken() {
        return getString(KEY_TOKEN, null);
    }

    public boolean getBoolean(String KEY, boolean defVal) {
        return preferences.getBoolean(KEY, defVal);
    }


    public void setBoolean(String key, boolean value) {
        getEditor().putBoolean(key, value).commit();

    }

    public void setString(String key, String value) {
        getEditor().putString(key, value).commit();
    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public void setDarkTheme(boolean isDark){
        getEditor().putBoolean("THEME",isDark);
    }

    public boolean getDarkTheme(){
        return preferences.getBoolean("THEME",false);
    }

}
