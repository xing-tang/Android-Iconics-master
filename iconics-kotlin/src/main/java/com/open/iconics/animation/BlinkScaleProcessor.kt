package com.open.iconics.animation

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import androidx.annotation.FloatRange
import com.open.iconics.IconicsBrush
import com.open.iconics.animation.IconicsAnimationProcessor.RepeatMode.REVERSE

open class BlinkScaleProcessor(
    /** The minimal available scale. */
    @FloatRange(from = 0.0) open var minimumScale: Float = 0f,
    /** The maximal available scale. */
    @FloatRange(from = 0.0) open var maximumScale: Float = 1f,

    override var interpolator: TimeInterpolator = DEFAULT_INTERPOLATOR,
    override var duration: Long = DEFAULT_DURATION,
    override var repeatCount: Int = INFINITE,
    override var repeatMode: RepeatMode = REVERSE,
    override var isStartImmediately: Boolean = true
) : IconicsAnimationProcessor(interpolator, duration, repeatCount, repeatMode, isStartImmediately) {

    companion object {
        /** Duration used for all instances of this processor by default. 500 ms by default. */
        @JvmField var DEFAULT_DURATION: Long = 500L
    }

    override val animationTag: String = "blink_scale"

    override fun processPreDraw(
        canvas: Canvas,
        iconBrush: IconicsBrush<TextPaint>,
        iconContourBrush: IconicsBrush<Paint>,
        backgroundBrush: IconicsBrush<Paint>,
        backgroundContourBrush: IconicsBrush<Paint>
    ) {

        val scaleByPercent = (maximumScale - minimumScale) / 100

        val scale = animatedPercent * scaleByPercent
        val bounds = drawableBounds

        bounds?.let {
            canvas.save()
            canvas.scale(scale, scale, (it.width() / 2).toFloat(), (it.height() / 2).toFloat())
        }
    }

    override fun processPostDraw(canvas: Canvas) {
        canvas.restore()
    }
}
