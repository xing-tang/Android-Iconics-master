package com.open.iconics.internal

import android.content.Context
import android.graphics.drawable.StateListDrawable
import com.open.iconics.IconicsDrawable
import com.open.iconics.utils.IconicsUtils

internal class CheckableIconBundle {
    var animateChanges: Boolean = false
    var checkedIcon: IconicsDrawable? = null
    var uncheckedIcon: IconicsDrawable? = null

    fun createIcons(ctx: Context) {
        checkedIcon = IconicsDrawable(ctx)
        uncheckedIcon = IconicsDrawable(ctx)
    }

    fun createStates(ctx: Context): StateListDrawable {
        return IconicsUtils.getCheckableIconStateList(
            ctx,
            uncheckedIcon,
            checkedIcon,
            animateChanges
        )
    }
}
