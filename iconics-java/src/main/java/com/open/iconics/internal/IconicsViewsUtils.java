package com.open.iconics.internal;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.open.iconics.IconicsDrawable;
import com.open.iconics.animation.IconicsAnimatedDrawable;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

@RestrictTo(LIBRARY_GROUP)
public class IconicsViewsUtils {

    @Nullable
    @RestrictTo(LIBRARY_GROUP)
    public static IconicsDrawable checkAnimation(@Nullable IconicsDrawable drawable,
                                                 @NonNull View view) {
        if (drawable == null) return null;
        if (drawable instanceof IconicsAnimatedDrawable) {
            ((IconicsAnimatedDrawable) drawable).animateIn(view);
        }
        return drawable;
    }
}