package com.open.iconics.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.TypedValue
import android.view.View

object IconicsUtils {

    /**
     * Enables the [View.LAYER_TYPE_SOFTWARE] for the view holding this icon, to enable correct
     * shadowLayer drawing
     *
     * @param view the view holding `IconicsDrawable`
     * @see View.setLayerType
     */
    @JvmStatic fun enableShadowSupport(view: View) {
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    @JvmStatic fun convertDpToPx(context: Context, dp: Number): Int {
        return convertDpToPx(context.resources, dp)
    }

    @JvmStatic fun convertDpToPx(res: Resources, dp: Number): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            res.displayMetrics
        ).toInt()
    }

    @JvmStatic
    @JvmOverloads
    fun getCheckableIconStateList(
        ctx: Context,
        icon: Drawable?,
        checkedIcon: Drawable?,
        animate: Boolean = true
    ): StateListDrawable {
        return StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_checked), checkedIcon)
            addState(intArrayOf(-android.R.attr.state_checked), icon)

            if (animate) {
                val duration = ctx.resources.getInteger(android.R.integer.config_shortAnimTime)
                setEnterFadeDuration(duration)
                setExitFadeDuration(duration)
            }
        }
    }
}
