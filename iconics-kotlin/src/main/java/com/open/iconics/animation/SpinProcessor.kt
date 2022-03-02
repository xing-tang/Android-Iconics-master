package com.open.iconics.animation

import android.animation.TimeInterpolator
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import com.open.iconics.IconicsBrush
import com.open.iconics.animation.IconicsAnimationProcessor.RepeatMode.RESTART
import com.open.iconics.animation.SpinProcessor.Direction.CLOCKWISE

open class SpinProcessor(
    /** The direction of the spin */
    open var direction: Direction = CLOCKWISE,

    override var interpolator: TimeInterpolator = DEFAULT_INTERPOLATOR,
    override var duration: Long = DEFAULT_DURATION,
    override var repeatCount: Int = INFINITE,
    override var repeatMode: IconicsAnimationProcessor.RepeatMode = RESTART,
    override var isStartImmediately: Boolean = true
) : IconicsAnimationProcessor(interpolator, duration, repeatCount, repeatMode, isStartImmediately) {

    companion object {
        /** Duration used for all instances of this processor by default. 2000 ms by default. */
        @JvmField var DEFAULT_DURATION = 2000L
    }

    private var isDrawableShadowCleared = false

    override val animationTag: String = "spin"

    override fun processPreDraw(
        canvas: Canvas,
        iconBrush: IconicsBrush<TextPaint>,
        iconContourBrush: IconicsBrush<Paint>,
        backgroundBrush: IconicsBrush<Paint>,
        backgroundContourBrush: IconicsBrush<Paint>
    ) {

        // Shadow are not recalculate while drawable are spinning. It looks ugly!
        // Turn off ugly shadow!
        if (!isDrawableShadowCleared) {
            iconBrush.paint.clearShadowLayer()
            isDrawableShadowCleared = true
        }

        canvas.save()

        val bounds = drawableBounds
        val degrees = animatedPercent * 3.6f * direction.sign

        bounds?.let {
            canvas.rotate(degrees, (it.width() / 2).toFloat(), (it.height() / 2).toFloat())
        }
    }

    override fun processPostDraw(canvas: Canvas) {
        canvas.restore()
    }

    override fun onDrawableDetached() {
        isDrawableShadowCleared = false
    }

    enum class Direction(internal val sign: Int) { CLOCKWISE(+1), COUNTER_CLOCKWISE(-1) }
}
