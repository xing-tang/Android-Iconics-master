package com.open.iconics.context;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.open.iconics.IconicsDrawable;
import com.open.iconics.R;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

@RestrictTo(LIBRARY_GROUP)
public class IconicsAttrsApplier {

    @Nullable
    public static IconicsDrawable getIconicsDrawable(@NonNull Context ctx,
                                                     @Nullable AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.Iconics);
        try {
            return new IconicsAttrsExtractor(ctx, a)
                    .iconId(R.styleable.Iconics_ico_icon)
                    .colorsId(R.styleable.Iconics_ico_color)
                    .sizeId(R.styleable.Iconics_ico_size)
                    .paddingId(R.styleable.Iconics_ico_padding)
                    .offsetXId(R.styleable.Iconics_ico_offset_x)
                    .offsetYId(R.styleable.Iconics_ico_offset_y)
                    .contourColorId(R.styleable.Iconics_ico_contour_color)
                    .contourWidthId(R.styleable.Iconics_ico_contour_width)
                    .backgroundColorId(R.styleable.Iconics_ico_background_color)
                    .cornerRadiusId(R.styleable.Iconics_ico_corner_radius)
                    .backgroundContourColorId(R.styleable.Iconics_ico_background_contour_color)
                    .backgroundContourWidthId(R.styleable.Iconics_ico_background_contour_width)
                    .shadowRadiusId(R.styleable.Iconics_ico_shadow_radius)
                    .shadowDxId(R.styleable.Iconics_ico_shadow_dx)
                    .shadowDyId(R.styleable.Iconics_ico_shadow_dy)
                    .shadowColorId(R.styleable.Iconics_ico_shadow_color)
                    .animationsId(R.styleable.Iconics_ico_animations)
                    .extractWithOffsets();
        } finally {
            a.recycle();
        }
    }
}
