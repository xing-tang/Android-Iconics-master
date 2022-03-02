package com.open.iconics.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class IconicsImageButton extends IconicsImageView {

    public IconicsImageButton(Context context) {
        this(context, null);
    }

    public IconicsImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.buttonStyle);
    }

    public IconicsImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFocusable(true);
    }

    @Override
    protected boolean onSetAlpha(int alpha) {
        return false;
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ImageButton.class.getName();
    }
}
