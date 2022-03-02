package com.open.iconics

import android.annotation.SuppressLint
import android.content.res.Resources
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import com.open.iconics.utils.IconicsUtils

sealed class IconicsSize : IconicsExtractor {

    companion object {
        /** Size of [androidx.appcompat.widget.Toolbar] icon in dp */
        @JvmField val TOOLBAR_ICON_SIZE: IconicsSize = dp(24f)

        /** Size of [androidx.appcompat.widget.Toolbar] icon padding in dp */
        @JvmField val TOOLBAR_ICON_PADDING: IconicsSize = dp(1f)

        /** @param dp The size in density-independent pixels (dp). */
        @SuppressLint("SupportAnnotationUsage")
        @JvmStatic
        fun dp(@Dimension(unit = Dimension.DP) dp: Number): IconicsSize {
            return IconicsSizeDp(dp)
        }

        /** @param px The size in pixels (px). */
        @SuppressLint("SupportAnnotationUsage")
        @JvmStatic
        fun px(@Dimension(unit = Dimension.PX) px: Number): IconicsSize {
            return IconicsSizePx(px)
        }

        /** @param res The dimension resource. */
        @JvmStatic
        fun res(@DimenRes res: Int): IconicsSize {
            return IconicsSizeRes(res)
        }
    }

    internal abstract fun extractFloat(res: Resources): Float

    internal abstract fun extract(res: Resources): Int
}

class IconicsSizeDp internal constructor(
    @SuppressLint("SupportAnnotationUsage")
    @Dimension(unit = Dimension.DP)
    private val dp: Number
) : IconicsSize() {
    var pxCache: Int? = null

    override fun extractFloat(res: Resources): Float = extract(res).toFloat()

    override fun extract(res: Resources): Int {
        val pxCache = pxCache ?: IconicsUtils.convertDpToPx(res, dp)
        this.pxCache = pxCache
        return pxCache
    }
}

class IconicsSizePx internal constructor(
    @SuppressLint("SupportAnnotationUsage")
    @Dimension(unit = Dimension.PX)
    private val px: Number
) : IconicsSize() {
    override fun extractFloat(res: Resources): Float = px.toFloat()

    override fun extract(res: Resources): Int = px.toInt()
}

class IconicsSizeRes internal constructor(
    @DimenRes private val resId: Int
) : IconicsSize() {
    // note we should not cache the value, as a configurationchange could mean different values

    override fun extractFloat(res: Resources): Float {
        return extract(res).toFloat()
    }

    override fun extract(res: Resources): Int = res.getDimensionPixelSize(resId)
}
