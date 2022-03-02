package com.open.iconics.context;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterFactory;

public class IconicsLayoutInflater implements LayoutInflaterFactory {
    private AppCompatDelegate appCompatDelegate;
    private final IconicsFactory mIconicsFactory;

    public IconicsLayoutInflater(AppCompatDelegate appCompatDelegate) {
        this.appCompatDelegate = appCompatDelegate;
        this.mIconicsFactory = new IconicsFactory();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View result = appCompatDelegate.createView(parent, name, context, attrs);
        return mIconicsFactory.onViewCreated(result, context, attrs);
    }
}
