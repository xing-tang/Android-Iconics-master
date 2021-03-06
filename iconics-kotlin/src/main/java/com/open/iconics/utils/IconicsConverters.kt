@file:Suppress("NOTHING_TO_INLINE")

package com.open.iconics.utils

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import androidx.core.graphics.drawable.IconCompat
import com.open.iconics.IconicsColor
import com.open.iconics.IconicsDrawable
import com.open.iconics.IconicsSize
import com.open.iconics.dsl.NON_READABLE

// VARIOUS convenient extension functions for quick common setters
var IconicsDrawable.colorString: String
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(value) {
        color = IconicsColor.parse(value)
    }

var IconicsDrawable.colorRes: Int
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(@ColorRes value) {
        color = IconicsColor.colorRes(value)
    }

var IconicsDrawable.contourColorString: String
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(value) {
        contourColor = IconicsColor.parse(value)
    }

var IconicsDrawable.contourColorRes: Int
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(@ColorRes value) {
        contourColor = IconicsColor.colorRes(value)
    }

var IconicsDrawable.backgroundColorString: String
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(value) {
        backgroundColor = IconicsColor.parse(value)
    }

var IconicsDrawable.backgroundColorRes: Int
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(@ColorRes value) {
        backgroundColor = IconicsColor.colorRes(value)
    }

var IconicsDrawable.backgroundContourColorString: String
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(value) {
        backgroundContourColor = IconicsColor.parse(value)
    }

var IconicsDrawable.backgroundContourColorRes: Int
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(@ColorRes value) {
        backgroundContourColor = IconicsColor.colorRes(value)
    }

var IconicsDrawable.sizeDp: Int
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(@Dimension(unit = Dimension.DP) value) {
        size = IconicsSize.dp(value)
    }

var IconicsDrawable.sizeRes: Int
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(@DimenRes value) {
        size = IconicsSize.res(value)
    }

var IconicsDrawable.paddingDp: Int
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(@Dimension(unit = Dimension.DP) value) {
        padding = IconicsSize.dp(value)
    }

var IconicsDrawable.paddingRes: Int
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(@DimenRes value) {
        padding = IconicsSize.res(value)
    }

var IconicsDrawable.roundedCornersDp: Int
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(@Dimension(unit = Dimension.DP) value) {
        roundedCorners = IconicsSize.dp(value)
    }

var IconicsDrawable.roundedCornersRes: Int
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(@DimenRes value) {
        roundedCorners = IconicsSize.res(value)
    }

var IconicsDrawable.contourWidthDp: Int
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(@Dimension(unit = Dimension.DP) value) {
        contourWidth = IconicsSize.dp(value)
    }

var IconicsDrawable.contourWidthRes: Int
    @Deprecated(level = DeprecationLevel.ERROR, message = NON_READABLE)
    get() = throw UnsupportedOperationException()
    set(@DimenRes value) {
        contourWidth = IconicsSize.res(value)
    }

/**
 * Pretty converter to [androidx.core.graphics.drawable.IconCompat]
 *
 * Note: use [IconCompat.toIcon] to transform into Platform's Icon
 */
inline fun IconicsDrawable.toAndroidIconCompat(): IconCompat {
    return IconCompat.createWithBitmap(toBitmap())
}

/** Pretty converter to [IconicsSize.dp] */
@Deprecated("Use IconicsSize.dp() instead", ReplaceWith("IconicsSize.dp(x)", "com.open.iconics.IconicsSize"))
@SuppressLint("SupportAnnotationUsage")
inline fun @receiver:Dimension(unit = Dimension.DP) Number.toIconicsSizeDp(): IconicsSize {
    return IconicsSize.dp(this)
}

/** Pretty converter to [IconicsSize.px] */
@Deprecated("Use IconicsSize.px() instead", ReplaceWith("IconicsSize.px(x)", "com.open.iconics.IconicsSize"))
@SuppressLint("SupportAnnotationUsage")
inline fun @receiver:Dimension(unit = Dimension.PX) Number.toIconicsSizePx(): IconicsSize {
    return IconicsSize.px(this)
}

/** Pretty converter to [IconicsSize.res] */
@Deprecated("Use IconicsSize.res() instead", ReplaceWith("IconicsSize.res(x)", "com.open.iconics.IconicsSize"))
inline fun @receiver:DimenRes Int.toIconicsSizeRes(): IconicsSize {
    return IconicsSize.res(toInt())
}

/** Pretty converter to [IconicsColor.colorInt] */
@Deprecated("Use IconicsColor.colorInt() instead", ReplaceWith("IconicsColor.colorInt(x)", "com.open.iconics.IconicsColor"))
inline fun @receiver:ColorInt Int.toIconicsColor(): IconicsColor {
    return IconicsColor.colorInt(this)
}

/** Pretty converter to [IconicsColor.parse] */
@Deprecated("Use IconicsColor.parse() instead", ReplaceWith("IconicsColor.parse(x)", "com.open.iconics.IconicsColor"))
inline fun String.toIconicsColor(): IconicsColor {
    return IconicsColor.parse(this)
}

/** Pretty converter to [IconicsColor.colorList] */
@Deprecated("Use IconicsColor.colorList() instead", ReplaceWith("IconicsColor.colorList(x)", "com.open.iconics.IconicsColor"))
inline fun ColorStateList.toIconicsColor(): IconicsColor {
    return IconicsColor.colorList(this)
}

/** Pretty converter to [IconicsColor.colorRes] */
@Deprecated("Use IconicsColor.colorRes() instead", ReplaceWith("IconicsColor.colorRes(x)", "com.open.iconics.IconicsColor"))
inline fun @receiver:ColorRes Int.toIconicsColorRes(): IconicsColor {
    return IconicsColor.colorRes(this)
}