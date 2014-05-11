package com.tspoon.stylight.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Settings {

    private static final String APP_SHARED_PREFS = "APP_SHARED_PREFS";
    private static final String KEY_SECURE_COOKIE = "KEY_SECURE_COOKIE";

    private static Settings sInstance = null;

    private SharedPreferences mPreferences;
    private Editor mEditor;

    private Settings(Context context) {
        mPreferences = context.getApplicationContext().getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static Settings getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Settings(context);
        }
        return sInstance;
    }

    public void setSecureCookie(String cookie) {
        mEditor.putString(KEY_SECURE_COOKIE, cookie).commit();
    }

    public String getSecureCookie() {
        return mPreferences.getString(KEY_SECURE_COOKIE, null);
    }

}
