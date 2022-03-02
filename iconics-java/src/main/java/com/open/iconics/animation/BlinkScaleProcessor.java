package com.open.iconics.animation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;

import com.open.iconics.IconicsBrush;

public class BlinkScaleProcessor extends IconicsAnimationProcessor {
    /**
     * Duration used for all instances of this processor by default. 500 ms by default.
     */
    public static int defaultDuration = 500;

    @FloatRange(from = 0)
    private float mMinimumScale = 0;
    @FloatRange(from = 0)
    private float mMaximumScale = 1;

    {
        mRepeatMode = REVERSE;
        mDuration = defaultDuration;
    }

    /**
     * @param minimumScale The scale which will be used as minimal available value.
     */
    @NonNull
    public BlinkScaleProcessor minimumScale(@FloatRange(from = 0) float minimumScale) {
        mMinimumScale = minimumScale;
        return this;
    }

    /**
     * @param maximumScale The scale which will be used as maximal available value.
     */
    @NonNull
    public BlinkScaleProcessor maximumScale(@FloatRange(from = 0) float maximumScale) {
        mMaximumScale = maximumScale;
        return this;
    }

    /**
     * @return The minimal available scale.
     */
    @FloatRange(from = 0)
    public float getMinimumScale() {
        return mMinimumScale;
    }

    /**
     * @return The minimal available scale.
     */
    @FloatRange(from = 0)
    public float getMaximumScale() {
        return mMaximumScale;
    }

    @Override
    @NonNull
    public String animationTag() {
        return "blink_scale";
    }

    @Override
    protected void processPreDraw(
            @NonNull Canvas canvas,
            @NonNull IconicsBrush<TextPaint> iconBrush,
            @NonNull IconicsBrush<Paint> iconContourBrush,
            @NonNull IconicsBrush<Paint> backgroundBrush,
            @NonNull IconicsBrush<Paint> backgroundContourBrush) {

        float scaleByPercent = (mMaximumScale - mMinimumScale) / 100;

        float scale = getAnimatedPercent() * scaleByPercent;
        Rect bounds = getDrawableBounds();

        canvas.save();
        canvas.scale(scale, scale, bounds.width() / 2, bounds.height() / 2);
    }

    @Override
    protected void processPostDraw(@NonNull Canvas canvas) {
        canvas.restore();
    }
}
