package com.open.iconics.animation

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import androidx.annotation.IntRange
import com.open.iconics.IconicsBrush
import com.open.iconics.animation.IconicsAnimationProcessor.RepeatMode.REVERSE

open class BlinkAlphaProcessor(
    /** The minimal available alpha. */
    @IntRange(from = 0, to = 255) open var minimumAlpha: Int = 0,
    /** The maximal available alpha. */
    @IntRange(from = 0, to = 255) open var maximumAlpha: Int = 255,

    override var interpolator: TimeInterpolator = DEFAULT_INTERPOLATOR,
    override var duration: Long = DEFAULT_DURATION,
    override var repeatCount: Int = INFINITE,
    override var repeatMode: RepeatMode = REVERSE,
    override var isStartImmediately: Boolean = true
) : IconicsAnimationProcessor(interpolator, duration, repeatCount, repeatMode, isStartImmediately) {

    companion object {
        /** Duration used for all instances of this processor by default. 500 ms by default. */
        @JvmField var DEFAULT_DURATION = 500L
    }

    override val animationTag: String = "blink_alpha"

    override fun processPreDraw(
        canvas: Canvas,
        iconBrush: IconicsBrush<TextPaint>,
        iconContourBrush: IconicsBrush<Paint>,
        backgroundBrush: IconicsBrush<Paint>,
        backgroundContourBrush: IconicsBrush<Paint>
    ) {

        val alphaByPercent = (maximumAlpha - minimumAlpha) / 100

        val alpha = (animatedPercent * alphaByPercent).toInt()

        iconBrush.alpha = alpha
        iconContourBrush.alpha = alpha
        backgroundBrush.alpha = alpha
        backgroundContourBrush.alpha = alpha
    }
}
