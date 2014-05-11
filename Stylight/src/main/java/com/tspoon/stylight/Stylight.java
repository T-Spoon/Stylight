package com.tspoon.stylight;

import android.app.Application;

import com.tspoon.stylight.utils.Settings;

public class Stylight extends Application {

    private static Settings sSettings;

    @Override
    public void onCreate() {
        super.onCreate();

        sSettings = Settings.getInstance(this);
    }

    public static Settings getSettings() {
        return sSettings;
    }
}
