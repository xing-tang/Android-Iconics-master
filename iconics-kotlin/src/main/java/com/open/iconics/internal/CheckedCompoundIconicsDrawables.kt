package com.open.iconics.internal

import com.open.iconics.IconicsDrawable

internal interface CheckedCompoundIconicsDrawables {

    var checkedIconicsDrawableStart: IconicsDrawable?

    var checkedIconicsDrawableTop: IconicsDrawable?

    var checkedIconicsDrawableEnd: IconicsDrawable?

    var checkedIconicsDrawableBottom: IconicsDrawable?

    fun setCheckedDrawableForAll(drawable: IconicsDrawable?)
}
