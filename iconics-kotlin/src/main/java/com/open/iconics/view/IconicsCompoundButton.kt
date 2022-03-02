package com.open.iconics.view

import android.content.Context
import android.util.AttributeSet
import android.widget.CompoundButton
import android.widget.TextView
import androidx.core.widget.CompoundButtonCompat
import com.open.iconics.IconicsDrawable
import com.open.iconics.animation.tryToEnableIconicsAnimation
import com.open.iconics.internal.CheckableIconBundle
import com.open.iconics.internal.IconicsViewsAttrsApplier
import com.open.iconics.utils.buildIconics

open class IconicsCompoundButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CompoundButton(context, attrs, defStyle) {
    private val iconsBundle = CheckableIconBundle()

    var checkedIcon: IconicsDrawable?
        get() = iconsBundle.checkedIcon
        set(icon) {
            iconsBundle.checkedIcon = tryToEnableIconicsAnimation(icon)
            buttonDrawable = iconsBundle.createStates(context)
        }

    var uncheckedIcon: IconicsDrawable?
        get() = iconsBundle.uncheckedIcon
        set(icon) {
            iconsBundle.uncheckedIcon = tryToEnableIconicsAnimation(icon)
            buttonDrawable = iconsBundle.createStates(context)
        }

    init {
        iconsBundle.createIcons(context)

        IconicsViewsAttrsApplier.readIconicsCompoundButton(context, attrs, iconsBundle)
        iconsBundle.animateChanges = IconicsViewsAttrsApplier.isIconicsAnimateChanges(context, attrs)

        tryToEnableIconicsAnimation(iconsBundle.checkedIcon, iconsBundle.uncheckedIcon)
        buttonDrawable = iconsBundle.createStates(context)

        @Suppress("LeakingThis")
        CompoundButtonCompat.getButtonDrawable(this)?.let {
            minWidth = it.minimumWidth
            minHeight = it.minimumHeight
        }
    }

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        if (!isInEditMode) {
            super.setText(text.buildIconics(), type)
        } else {
            super.setText(text, type)
        }
    }

    override fun getAccessibilityClassName(): CharSequence = IconicsCompoundButton::class.java.name
}
