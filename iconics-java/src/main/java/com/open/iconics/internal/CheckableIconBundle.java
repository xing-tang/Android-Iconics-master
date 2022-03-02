package com.open.iconics.internal;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.open.iconics.IconicsDrawable;
import com.open.iconics.utils.Utils;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

@RestrictTo(LIBRARY_GROUP)
public class CheckableIconBundle {
    public boolean mAnimateChanges;
    @Nullable
    public IconicsDrawable mCheckedIcon;
    @Nullable
    public IconicsDrawable mUncheckedIcon;

    public void createIcons(Context ctx) {
        mCheckedIcon = new IconicsDrawable(ctx);
        mUncheckedIcon = new IconicsDrawable(ctx);
    }

    public StateListDrawable createStates(Context ctx) {
        return Utils.getCheckableIconStateList(ctx, mUncheckedIcon, mCheckedIcon, mAnimateChanges);
    }
}
