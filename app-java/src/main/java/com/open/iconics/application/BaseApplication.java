package com.open.iconics.application;

import android.app.Application;
import android.content.Context;

import com.open.iconics.Iconics;
import com.open.iconics.iconfont.ChangbaIconFont;

public class BaseApplication extends Application {

    private static BaseApplication instance = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // register custom fonts like this (or also provide a font definition file)
        Iconics.registerFont(new ChangbaIconFont());
    }
}
