package com.open.iconics.animation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.open.iconics.IconicsBrush;

public class BlinkAlphaProcessor extends IconicsAnimationProcessor {
    /**
     * Duration used for all instances of this processor by default. 500 ms by default.
     */
    public static int defaultDuration = 500;

    @IntRange(from = 0, to = 255)
    private int mMinimumAlpha = 0;
    @IntRange(from = 0, to = 255)
    private int mMaximumAlpha = 255;

    {
        mRepeatMode = REVERSE;
        mDuration = defaultDuration;
    }

    /**
     * @param minimumAlpha The alpha which will be used as minimal available value.
     */
    @NonNull
    public BlinkAlphaProcessor minimumAlpha(@IntRange(from = 0, to = 255) int minimumAlpha) {
        mMinimumAlpha = minimumAlpha;
        return this;
    }

    /**
     * @param maximumAlpha The alpha which will be used as maximal available value.
     */
    @NonNull
    public BlinkAlphaProcessor maximumAlpha(@IntRange(from = 0, to = 255) int maximumAlpha) {
        mMaximumAlpha = maximumAlpha;
        return this;
    }

    /**
     * @return The minimal available alpha.
     */
    @IntRange(from = 0, to = 255)
    public int getMinimumAlpha() {
        return mMinimumAlpha;
    }

    /**
     * @return The maximal available alpha.
     */
    @IntRange(from = 0, to = 255)
    public int getMaximumAlpha() {
        return mMaximumAlpha;
    }

    @Override
    @NonNull
    public String animationTag() {
        return "blink_alpha";
    }

    @Override
    protected void processPreDraw(
            @NonNull Canvas canvas,
            @NonNull IconicsBrush<TextPaint> iconBrush,
            @NonNull IconicsBrush<Paint> iconContourBrush,
            @NonNull IconicsBrush<Paint> backgroundBrush,
            @NonNull IconicsBrush<Paint> backgroundContourBrush) {

        int alphaByPercent = (mMaximumAlpha - mMinimumAlpha) / 100;

        int alpha = (int) (getAnimatedPercent() * alphaByPercent);

        iconBrush.setAlpha(alpha);
        iconContourBrush.setAlpha(alpha);
        backgroundBrush.setAlpha(alpha);
        backgroundContourBrush.setAlpha(alpha);
    }
}
