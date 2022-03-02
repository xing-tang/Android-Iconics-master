package com.open.iconics.context;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;

public class IconicsLayoutInflater2 implements android.view.LayoutInflater.Factory2 {
    private AppCompatDelegate appCompatDelegate;
    private final IconicsFactory mIconicsFactory;

    public IconicsLayoutInflater2(AppCompatDelegate appCompatDelegate) {
        this.appCompatDelegate = appCompatDelegate;
        this.mIconicsFactory = new IconicsFactory();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View result = appCompatDelegate.createView(parent, name, context, attrs);
        return mIconicsFactory.onViewCreated(result, context, attrs);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View result = appCompatDelegate.createView(null, name, context, attrs);
        return mIconicsFactory.onViewCreated(result, context, attrs);
    }
}
