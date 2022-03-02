package com.open.iconics.internal;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.open.iconics.IconicsDrawable;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

@RestrictTo(LIBRARY_GROUP)
public interface CheckedCompoundIconicsDrawables {

    @Nullable
    IconicsDrawable getCheckedIconicsDrawableStart();

    @Nullable
    IconicsDrawable getCheckedIconicsDrawableTop();

    @Nullable
    IconicsDrawable getCheckedIconicsDrawableEnd();

    @Nullable
    IconicsDrawable getCheckedIconicsDrawableBottom();

    void setCheckedDrawableStart(@Nullable IconicsDrawable drawable);

    void setCheckedDrawableTop(@Nullable IconicsDrawable drawable);

    void setCheckedDrawableEnd(@Nullable IconicsDrawable drawable);

    void setCheckedDrawableBottom(@Nullable IconicsDrawable drawable);

    void setCheckedDrawableForAll(@Nullable IconicsDrawable drawable);
}
