package com.open.iconics.internal

import com.open.iconics.IconicsDrawable

internal interface CompoundIconicsDrawables {

    var iconicsDrawableStart: IconicsDrawable?

    var iconicsDrawableTop: IconicsDrawable?

    var iconicsDrawableEnd: IconicsDrawable?

    var iconicsDrawableBottom: IconicsDrawable?

    fun setDrawableForAll(drawable: IconicsDrawable?)
}
