package com.open.iconics

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import androidx.annotation.IntRange

/**
 * Helper class to control applying state changes to colors and paint. Also for compact providing
 * into [AnimationProcessor][com.mikepenz.iconics.animation.IconicsAnimationProcessor]
 */
class IconicsBrush<T : Paint>(
    /** Will be used for drawing something (icon, background etc.) */
    val paint: T
) {
    private var state: IntArray? = null

    init {
        paint.alpha = 255
    }

    /** Colors which applied on [paint] for drawing current state */
    var colorsList: ColorStateList? = null

    /** Alpha channel for colors */
    var alpha: Int
        get() = paint.alpha
        set(@IntRange(from = 0, to = 255) alpha) {
            if (paint.alpha != alpha) {
                paint.alpha = alpha
            }
        }

    val isStateful: Boolean
        get() = colorsList?.isStateful == true

    val colorForCurrentState: Int
        get() = getColorForCurrentState(colorsList?.defaultColor ?: Color.TRANSPARENT)

    private fun getColorForCurrentState(defaultColor: Int): Int {
        return colorsList?.getColorForState(state, defaultColor) ?: defaultColor
    }

    fun applyState(state: IntArray?): Boolean {
        this.state = state

        val colorForState = colorForCurrentState
        val oldColor = paint.color
        paint.color = colorForState
        return colorForState != oldColor
    }

    override fun toString(): String {
        return "color=#${Integer.toHexString(paint.color)}, state=$state, colorList=$colorsList"
    }
}
