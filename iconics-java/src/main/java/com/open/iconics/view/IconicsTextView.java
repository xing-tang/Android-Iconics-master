package com.open.iconics.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.AppCompatTextView;

import com.open.iconics.Iconics;
import com.open.iconics.IconicsDrawable;
import com.open.iconics.internal.CompoundIconicsDrawables;
import com.open.iconics.internal.CompoundIconsBundle;
import com.open.iconics.internal.IconicsView;
import com.open.iconics.internal.IconicsViewsAttrsApplier;
import com.open.iconics.internal.IconicsViewsUtils;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

public class IconicsTextView extends AppCompatTextView implements CompoundIconicsDrawables, IconicsView {
    protected final CompoundIconsBundle mIconsBundle = new CompoundIconsBundle();

    public IconicsTextView(Context context) {
        this(context, null);
    }

    public IconicsTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public IconicsTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            initialize(context, attrs, defStyle);
        }
    }

    @Override
    @RestrictTo(LIBRARY_GROUP)
    public void initialize(Context context, AttributeSet attrs, int defStyle) {
        applyAttr(context, attrs, defStyle);
        IconicsViewsUtils.checkAnimation(mIconsBundle.mBottomIcon, this);
        IconicsViewsUtils.checkAnimation(mIconsBundle.mTopIcon, this);
        IconicsViewsUtils.checkAnimation(mIconsBundle.mEndIcon, this);
        IconicsViewsUtils.checkAnimation(mIconsBundle.mStartIcon, this);
        setIcons();
    }

    @Override
    @RestrictTo(LIBRARY_GROUP)
    public void applyAttr(Context context, AttributeSet attrs, int defStyle) {
        IconicsViewsAttrsApplier.readIconicsTextView(context, attrs, mIconsBundle);
    }

    private void setIcons() {
        mIconsBundle.setIcons(this);
    }

    //region CompoundIconicsDrawablesImpl
    @Nullable
    @Override
    public IconicsDrawable getIconicsDrawableStart() {
        return mIconsBundle.mStartIcon;
    }

    @Nullable
    @Override
    public IconicsDrawable getIconicsDrawableTop() {
        return mIconsBundle.mTopIcon;
    }

    @Nullable
    @Override
    public IconicsDrawable getIconicsDrawableEnd() {
        return mIconsBundle.mEndIcon;
    }

    @Nullable
    @Override
    public IconicsDrawable getIconicsDrawableBottom() {
        return mIconsBundle.mBottomIcon;
    }

    @Override
    public void setDrawableStart(@Nullable IconicsDrawable drawable) {
        mIconsBundle.mStartIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        setIcons();
    }

    @Override
    public void setDrawableTop(@Nullable IconicsDrawable drawable) {
        mIconsBundle.mTopIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        setIcons();
    }

    @Override
    public void setDrawableEnd(@Nullable IconicsDrawable drawable) {
        mIconsBundle.mEndIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        setIcons();
    }

    @Override
    public void setDrawableBottom(@Nullable IconicsDrawable drawable) {
        mIconsBundle.mBottomIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        setIcons();
    }

    @Override
    public void setDrawableForAll(@Nullable IconicsDrawable drawable) {
        mIconsBundle.mStartIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        mIconsBundle.mTopIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        mIconsBundle.mEndIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        mIconsBundle.mBottomIcon = IconicsViewsUtils.checkAnimation(drawable, this);
        setIcons();
    }
    //endregion

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!isInEditMode()) {
            super.setText(new Iconics.IconicsBuilder().ctx(getContext()).on(text).build(), type);
        } else {
            super.setText(text, type);
        }
    }
}