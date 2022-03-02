package com.open.iconics.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.open.iconics.Iconics;
import com.open.iconics.IconicsDrawable;
import com.open.iconics.internal.CheckableIconBundle;
import com.open.iconics.internal.IconicsView;
import com.open.iconics.internal.IconicsViewsAttrsApplier;
import com.open.iconics.internal.IconicsViewsUtils;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

public class IconicsCompoundButton extends CompoundButton implements IconicsView {
    private final CheckableIconBundle mIconsBundle = new CheckableIconBundle();

    public IconicsCompoundButton(Context context) {
        super(context);
    }

    public IconicsCompoundButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconicsCompoundButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            initialize(context, attrs, defStyle);
        }
    }

    @Override
    @RestrictTo(LIBRARY_GROUP)
    public void initialize(Context context, AttributeSet attrs, int defStyle) {
        mIconsBundle.createIcons(context);
        applyAttr(context, attrs, defStyle);
        IconicsViewsUtils.checkAnimation(mIconsBundle.mCheckedIcon, this);
        IconicsViewsUtils.checkAnimation(mIconsBundle.mUncheckedIcon, this);
        setButtonDrawable(mIconsBundle.createStates(context));
    }

    @Override
    @RestrictTo(LIBRARY_GROUP)
    public void applyAttr(Context context, AttributeSet attrs, int defStyle) {
        IconicsViewsAttrsApplier.readIconicsCompoundButton(context, attrs, mIconsBundle);
        mIconsBundle.mAnimateChanges = IconicsViewsAttrsApplier.isIconicsAnimateChanges(context, attrs);
    }

    public void setCheckedIcon(@Nullable IconicsDrawable icon) {
        mIconsBundle.mCheckedIcon = IconicsViewsUtils.checkAnimation(icon, this);
        setButtonDrawable(mIconsBundle.createStates(getContext()));
    }

    public void setUncheckedIcon(@Nullable IconicsDrawable icon) {
        mIconsBundle.mUncheckedIcon = IconicsViewsUtils.checkAnimation(icon, this);
        setButtonDrawable(mIconsBundle.createStates(getContext()));
    }

    @Nullable
    public IconicsDrawable getCheckedIcon() {
        return mIconsBundle.mCheckedIcon;
    }

    @Nullable
    public IconicsDrawable getUncheckedIcon() {
        return mIconsBundle.mUncheckedIcon;
    }

    @Override
    public void setText(@Nullable CharSequence text, @NonNull BufferType type) {
        if (!isInEditMode()) {
            super.setText(
                    new Iconics.IconicsBuilder()
                            .ctx(getContext())
                            .on(text == null ? "" : text)
                            .build(),
                    type);
        } else {
            super.setText(text, type);
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return IconicsCompoundButton.class.getName();
    }
}