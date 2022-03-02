package com.open.iconics.internal;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.RestrictTo;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

@RestrictTo(LIBRARY_GROUP)
public interface IconicsView {

    @RestrictTo(LIBRARY_GROUP)
    void initialize(Context context, AttributeSet attrs, int defStyle);

    @RestrictTo(LIBRARY_GROUP)
    void applyAttr(Context context, AttributeSet attrs, int defStyle);
}