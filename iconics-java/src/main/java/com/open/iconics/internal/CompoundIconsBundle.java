package com.open.iconics.internal;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.widget.TextViewCompat;

import com.open.iconics.IconicsDrawable;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

@RestrictTo(LIBRARY_GROUP)
public class CompoundIconsBundle {
    @Nullable
    public IconicsDrawable mStartIcon;
    @Nullable
    public IconicsDrawable mTopIcon;
    @Nullable
    public IconicsDrawable mEndIcon;
    @Nullable
    public IconicsDrawable mBottomIcon;

    public void setIcons(TextView textView) {
        Drawable[] drawables = TextViewCompat.getCompoundDrawablesRelative(textView);

        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(textView,
                mStartIcon != null ? mStartIcon : drawables[0],
                mTopIcon != null ? mTopIcon : drawables[1],
                mEndIcon != null ? mEndIcon : drawables[2],
                mBottomIcon != null ? mBottomIcon : drawables[3]
        );
    }
}
