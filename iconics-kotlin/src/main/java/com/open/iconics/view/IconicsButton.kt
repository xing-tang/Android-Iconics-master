package com.open.iconics.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.open.iconics.IconicsDrawable
import com.open.iconics.R
import com.open.iconics.animation.tryToEnableIconicsAnimation
import com.open.iconics.internal.CompoundIconicsDrawables
import com.open.iconics.internal.CompoundIconsBundle
import com.open.iconics.internal.IconicsViewsAttrsApplier
import com.open.iconics.utils.buildIconics

open class IconicsButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = R.attr.buttonStyle
) : AppCompatButton(context, attrs, defStyle), CompoundIconicsDrawables {
    private val iconsBundle = CompoundIconsBundle()

    //region CompoundIconicsDrawablesImpl
    override var iconicsDrawableStart: IconicsDrawable?
        get() = iconsBundle.startIcon
        set(value) {
            iconsBundle.startIcon = tryToEnableIconicsAnimation(value)
            setIcons()
        }

    override var iconicsDrawableTop: IconicsDrawable?
        get() = iconsBundle.startIcon
        set(value) {
            iconsBundle.startIcon = tryToEnableIconicsAnimation(value)
            setIcons()
        }

    override var iconicsDrawableEnd: IconicsDrawable?
        get() = iconsBundle.startIcon
        set(value) {
            iconsBundle.startIcon = tryToEnableIconicsAnimation(value)
            setIcons()
        }

    override var iconicsDrawableBottom: IconicsDrawable?
        get() = iconsBundle.startIcon
        set(value) {
            iconsBundle.startIcon = tryToEnableIconicsAnimation(value)
            setIcons()
        }

    init {
        IconicsViewsAttrsApplier.readIconicsTextView(context, attrs, iconsBundle)
        //setting created icons
        iconsBundle.apply { tryToEnableIconicsAnimation(bottomIcon, topIcon, endIcon, startIcon) }
        setIcons()
    }

    private fun setIcons() {
        iconsBundle.setIcons(this)
    }

    override fun setDrawableForAll(drawable: IconicsDrawable?) {
        iconsBundle.apply {
            startIcon = tryToEnableIconicsAnimation(drawable)
            topIcon = tryToEnableIconicsAnimation(drawable)
            endIcon = tryToEnableIconicsAnimation(drawable)
            bottomIcon = tryToEnableIconicsAnimation(drawable)
        }
        setIcons()
    }
    //endregion

    override fun setText(text: CharSequence, type: TextView.BufferType) {
        // NOTES:
        // 1. Need to disable the All Caps option to make Spannable work properly!
        // 2. This method will be called from the constructor of the super class
        isAllCaps = false

        if (!isInEditMode) {
            super.setText(text.buildIconics(), type)
        } else {
            super.setText(text, type)
        }
    }
}
