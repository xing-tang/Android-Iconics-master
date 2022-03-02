package com.open.iconics.animation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import com.open.iconics.IconicsBrush;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SpinProcessor extends IconicsAnimationProcessor {
    /**
     * Duration used for all instances of this processor by default. 2000 ms by default.
     */
    public static int defaultDuration = 2000;

    public final static int DIRECTION_CLOCKWISE = +1;
    public final static int DIRECTION_COUNTER_CLOCKWISE = -1;

    @IntDef({DIRECTION_CLOCKWISE, DIRECTION_COUNTER_CLOCKWISE})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Direction {
    }

    @Direction
    private int mDirection = DIRECTION_CLOCKWISE;
    private boolean mIsDrawableShadowCleared = false;

    {
        mDuration = defaultDuration;
    }

    /**
     * @param direction The direction of the spin, {@link #DIRECTION_CLOCKWISE clockwise (+1)}
     *                  or {@link #DIRECTION_COUNTER_CLOCKWISE counter clockwise (-1)}
     */
    @NonNull
    public SpinProcessor direction(@Direction int direction) {
        mDirection = direction;
        return this;
    }

    /**
     * @return the direction of the spin, clockwise (+1) or counter clockwise (-1)
     */
    @Direction
    public int getDirection() {
        return (int) Math.signum(mDirection);
    }

    @Override
    @NonNull
    public String animationTag() {
        return "spin";
    }

    @Override
    protected void processPreDraw(
            @NonNull Canvas canvas,
            @NonNull IconicsBrush<TextPaint> iconBrush,
            @NonNull IconicsBrush<Paint> iconContourBrush,
            @NonNull IconicsBrush<Paint> backgroundBrush,
            @NonNull IconicsBrush<Paint> backgroundContourBrush) {

        // Shadow are not recalculate while spin (it spin with drawable). It looks ugly!
        // Turn off ugly shadow!
        if (!mIsDrawableShadowCleared) {
            iconBrush.getPaint().clearShadowLayer();
            mIsDrawableShadowCleared = true;
        }

        canvas.save();

        Rect bounds = getDrawableBounds();
        float degrees = getAnimatedPercent() * 3.6f * getDirection();

        canvas.rotate(degrees, bounds.width() / 2, bounds.height() / 2);
    }

    @Override
    protected void processPostDraw(@NonNull Canvas canvas) {
        canvas.restore();
    }

    @Override
    protected void onDrawableDetached() {
        mIsDrawableShadowCleared = false;
    }
}
