package com.open.iconics.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.AppCompatImageView;

import com.open.iconics.IconicsDrawable;
import com.open.iconics.internal.IconicsView;
import com.open.iconics.internal.IconicsViewsAttrsApplier;
import com.open.iconics.internal.IconicsViewsUtils;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

public class IconicsImageView extends AppCompatImageView implements IconicsView {

    public IconicsImageView(Context context) {
        this(context, null);
    }

    public IconicsImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconicsImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            initialize(context, attrs, defStyle);
        }
    }

    @Override
    @RestrictTo(LIBRARY_GROUP)
    public void initialize(Context context, AttributeSet attrs, int defStyle) {
        //set the scale type for this view
        setScaleType(ScaleType.CENTER_INSIDE);

        applyAttr(context, attrs, defStyle);
    }

    @Override
    @RestrictTo(LIBRARY_GROUP)
    public void applyAttr(Context context, AttributeSet attrs, int defStyle) {
        setIcon(IconicsViewsAttrsApplier.getIconicsImageViewDrawable(context, attrs));
    }

    public void setIcon(@Nullable IconicsDrawable icon) {
        setImageDrawable(IconicsViewsUtils.checkAnimation(icon, this));
    }

    @Nullable
    public IconicsDrawable getIcon() {
        if (getDrawable() instanceof IconicsDrawable) {
            return ((IconicsDrawable) getDrawable());
        } else {
            return null;
        }
    }
}