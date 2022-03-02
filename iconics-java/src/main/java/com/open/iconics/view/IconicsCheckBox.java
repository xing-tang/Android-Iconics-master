package com.open.iconics.view;

import android.content.Context;
import android.util.AttributeSet;

public class IconicsCheckBox extends IconicsCompoundButton {

    public IconicsCheckBox(Context context) {
        this(context, null);
    }

    public IconicsCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.checkboxStyle);
    }

    public IconicsCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return IconicsCheckBox.class.getName();
    }
}