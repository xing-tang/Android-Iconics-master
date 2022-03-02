package com.open.iconics.internal;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.open.iconics.IconicsDrawable;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

@RestrictTo(LIBRARY_GROUP)
public interface CompoundIconicsDrawables {

    @Nullable
    IconicsDrawable getIconicsDrawableStart();

    @Nullable
    IconicsDrawable getIconicsDrawableTop();

    @Nullable
    IconicsDrawable getIconicsDrawableEnd();

    @Nullable
    IconicsDrawable getIconicsDrawableBottom();

    void setDrawableStart(@Nullable IconicsDrawable drawable);

    void setDrawableTop(@Nullable IconicsDrawable drawable);

    void setDrawableEnd(@Nullable IconicsDrawable drawable);

    void setDrawableBottom(@Nullable IconicsDrawable drawable);

    void setDrawableForAll(@Nullable IconicsDrawable drawable);
}
