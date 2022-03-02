package com.open.iconics.context;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleableRes;

import com.open.iconics.Iconics;
import com.open.iconics.IconicsDrawable;
import com.open.iconics.animation.IconicsAnimationProcessor;

import java.util.ArrayList;
import java.util.List;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;

@RestrictTo(LIBRARY_GROUP)
public class IconicsAttrsExtractor {
    private final static int DEF_COLOR = Integer.MIN_VALUE;
    private final static int DEF_SIZE = -1;

    @NonNull
    private final Context mContext;
    @NonNull
    private final TypedArray mTypedArray;

    @StyleableRes
    private int mIconId;
    @StyleableRes
    private int mSizeId;
    @StyleableRes
    private int mColorsId;
    @StyleableRes
    private int mPaddingId;
    @StyleableRes
    private int mOffsetXId;
    @StyleableRes
    private int mOffsetYId;

    @StyleableRes
    private int mContourColorId;
    @StyleableRes
    private int mContourWidthId;

    @StyleableRes
    private int mBackgroundColorId;
    @StyleableRes
    private int mCornerRadiusId;

    @StyleableRes
    private int mBackgroundContourColorId;
    @StyleableRes
    private int mBackgroundContourWidthId;

    @StyleableRes
    private int mShadowRadiusId;
    @StyleableRes
    private int mShadowDxId;
    @StyleableRes
    private int mShadowDyId;
    @StyleableRes
    private int mShadowColorId;

    @StyleableRes
    private int mAnimationsId;


    public IconicsAttrsExtractor(@NonNull Context context, @NonNull TypedArray typedArray) {
        mContext = context;
        mTypedArray = typedArray;
    }

    //region chain setters
    @NonNull
    public IconicsAttrsExtractor iconId(@StyleableRes int iconId) {
        mIconId = iconId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor sizeId(@StyleableRes int sizeId) {
        mSizeId = sizeId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor colorsId(@StyleableRes int colorsId) {
        mColorsId = colorsId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor paddingId(@StyleableRes int paddingId) {
        mPaddingId = paddingId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor offsetXId(@StyleableRes int offsetXId) {
        mOffsetXId = offsetXId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor offsetYId(@StyleableRes int offsetYId) {
        mOffsetYId = offsetYId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor contourColorId(@StyleableRes int contourColorId) {
        mContourColorId = contourColorId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor contourWidthId(@StyleableRes int contourWidthId) {
        mContourWidthId = contourWidthId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor backgroundColorId(@StyleableRes int backgroundColorId) {
        mBackgroundColorId = backgroundColorId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor cornerRadiusId(@StyleableRes int cornerRadiusId) {
        mCornerRadiusId = cornerRadiusId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor backgroundContourColorId(@StyleableRes int backgroundContourColorId) {
        mBackgroundContourColorId = backgroundContourColorId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor backgroundContourWidthId(@StyleableRes int backgroundContourWidthId) {
        mBackgroundContourWidthId = backgroundContourWidthId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor shadowRadiusId(@StyleableRes int shadowRadiusId) {
        mShadowRadiusId = shadowRadiusId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor shadowDxId(@StyleableRes int shadowDxId) {
        mShadowDxId = shadowDxId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor shadowDyId(@StyleableRes int shadowDyId) {
        mShadowDyId = shadowDyId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor shadowColorId(@StyleableRes int shadowColorId) {
        mShadowColorId = shadowColorId;
        return this;
    }

    @NonNull
    public IconicsAttrsExtractor animationsId(@StyleableRes int animationsId) {
        mAnimationsId = animationsId;
        return this;
    }
    //endregion

    @NonNull
    public IconicsDrawable extractNonNull() {
        return extract(null, false, true);
    }

    @Nullable
    public IconicsDrawable extract(@Nullable IconicsDrawable icon) {
        return extract(icon, false, false);
    }

    @Nullable
    public IconicsDrawable extract() {
        return extract(null, false, false);
    }

    @Nullable
    public IconicsDrawable extractWithOffsets() {
        return extract(null, true, false);
    }

    private IconicsDrawable extract(@Nullable IconicsDrawable icon,
                                    boolean extractOffsets,
                                    boolean nonNull) {

        icon = copyIfCan(icon);

        // region icon
        String i = mTypedArray.getString(mIconId);
        if (!TextUtils.isEmpty(i)) {
            icon = createIfNeeds(icon, mContext).icon(i);
        }
        ColorStateList colors = mTypedArray.getColorStateList(mColorsId);
        if (colors != null) {
            icon = createIfNeeds(icon, mContext).color(colors);
        }
        int size = mTypedArray.getDimensionPixelSize(mSizeId, DEF_SIZE);
        if (size != DEF_SIZE) {
            icon = createIfNeeds(icon, mContext).sizePx(size);
        }
        int padding = mTypedArray.getDimensionPixelSize(mPaddingId, DEF_SIZE);
        if (padding != DEF_SIZE) {
            icon = createIfNeeds(icon, mContext).paddingPx(padding);
        }
        if (extractOffsets) {
            int offsetY = mTypedArray.getDimensionPixelSize(mOffsetYId, DEF_SIZE);
            if (offsetY != DEF_SIZE) {
                icon = createIfNeeds(icon, mContext).iconOffsetYPx(offsetY);
            }
            int offsetX = mTypedArray.getDimensionPixelSize(mOffsetXId, DEF_SIZE);
            if (offsetX != DEF_SIZE) {
                icon = createIfNeeds(icon, mContext).iconOffsetXPx(offsetX);
            }
        }
        // endregion
        // region contour
        ColorStateList contourColor = mTypedArray.getColorStateList(mContourColorId);
        if (contourColor != null) {
            icon = createIfNeeds(icon, mContext).contourColor(contourColor);
        }
        int contourWidth = mTypedArray.getDimensionPixelSize(mContourWidthId, DEF_SIZE);
        if (contourWidth != DEF_SIZE) {
            icon = createIfNeeds(icon, mContext).contourWidthPx(contourWidth);
        }
        // endregion
        // region background
        ColorStateList backgroundColor = mTypedArray.getColorStateList(mBackgroundColorId);
        if (backgroundColor != null) {
            icon = createIfNeeds(icon, mContext).backgroundColor(backgroundColor);
        }
        int cornerRadius = mTypedArray.getDimensionPixelSize(mCornerRadiusId, DEF_SIZE);
        if (cornerRadius != DEF_SIZE) {
            icon = createIfNeeds(icon, mContext).roundedCornersPx(cornerRadius);
        }
        // endregion
        // region background contour
        ColorStateList backgroundContourColor = mTypedArray.getColorStateList(mBackgroundContourColorId);
        if (backgroundContourColor != null) {
            icon = createIfNeeds(icon, mContext).backgroundContourColor(backgroundContourColor);
        }
        int backgroundContourWidth = mTypedArray.getDimensionPixelSize(mBackgroundContourWidthId, DEF_SIZE);
        if (backgroundContourWidth != DEF_SIZE) {
            icon = createIfNeeds(icon, mContext).backgroundContourWidthPx(backgroundContourWidth);
        }
        // endregion
        // region shadow
        int shadowRadius = mTypedArray.getDimensionPixelSize(mShadowRadiusId, DEF_SIZE);
        int shadowDx = mTypedArray.getDimensionPixelSize(mShadowDxId, DEF_SIZE);
        int shadowDy = mTypedArray.getDimensionPixelSize(mShadowDyId, DEF_SIZE);
        int shadowColor = mTypedArray.getColor(mShadowColorId, DEF_COLOR);

        if (shadowRadius != DEF_SIZE
                && shadowDx != DEF_SIZE
                && shadowDy != DEF_SIZE
                && shadowColor != DEF_COLOR) {
            icon = createIfNeeds(icon, mContext).shadowPx(
                    shadowRadius,
                    shadowDx,
                    shadowDy,
                    shadowColor);
        }
        // endregion

        // animations should be applied at the end because here we transform the drawable,
        // therefore all properties must be processed before

        // region animations
        String animations = mTypedArray.getString(mAnimationsId);
        if (!TextUtils.isEmpty(animations)) {
            List<IconicsAnimationProcessor> processors = new ArrayList<>();
            String[] animationsList = animations.split("\\|");
            for (String animationTag : animationsList) {
                IconicsAnimationProcessor processor = Iconics.findProcessor(mContext, animationTag);
                if (processor != null) {
                    processors.add(processor);
                }
            }

            icon = createIfNeeds(icon, mContext)
                    .toAnimatedDrawable()
                    .processors(processors.toArray(new IconicsAnimationProcessor[0]));
        }
        // endregion

        if (nonNull) {
            icon = createIfNeeds(icon, mContext);
        }
        return icon;
    }

    @Nullable
    private static IconicsDrawable copyIfCan(@Nullable IconicsDrawable drawable) {
        if (drawable != null) {
            return drawable.clone();
        }
        return null;
    }

    @NonNull
    private static IconicsDrawable createIfNeeds(
            @Nullable IconicsDrawable drawable,
            @NonNull Context context) {
        if (drawable == null) {
            drawable = new IconicsDrawable(context);
        }
        return drawable;
    }
}
