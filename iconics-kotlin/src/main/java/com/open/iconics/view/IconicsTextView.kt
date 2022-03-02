package com.open.iconics.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.open.iconics.IconicsDrawable
import com.open.iconics.animation.tryToEnableIconicsAnimation
import com.open.iconics.internal.CompoundIconicsDrawables
import com.open.iconics.internal.CompoundIconsBundle
import com.open.iconics.internal.IconicsViewsAttrsApplier
import com.open.iconics.utils.buildIconics

open class IconicsTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyle), CompoundIconicsDrawables {
    internal val iconsBundle: CompoundIconsBundle = CompoundIconsBundle()

    //region CompoundIconicsDrawablesImpl
    override var iconicsDrawableStart: IconicsDrawable?
        get() = iconsBundle.startIcon
        set(value) {
            iconsBundle.startIcon = tryToEnableIconicsAnimation(value)
            setIcons()
        }

    override var iconicsDrawableTop: IconicsDrawable?
        get() = iconsBundle.topIcon
        set(value) {
            iconsBundle.topIcon = tryToEnableIconicsAnimation(value)
            setIcons()
        }

    override var iconicsDrawableEnd: IconicsDrawable?
        get() = iconsBundle.endIcon
        set(value) {
            iconsBundle.endIcon = tryToEnableIconicsAnimation(value)
            setIcons()
        }

    override var iconicsDrawableBottom: IconicsDrawable?
        get() = iconsBundle.bottomIcon
        set(value) {
            iconsBundle.bottomIcon = tryToEnableIconicsAnimation(value)
            setIcons()
        }

    init {
        IconicsViewsAttrsApplier.readIconicsTextView(context, attrs, iconsBundle)

        tryToEnableIconicsAnimation(
            iconsBundle.bottomIcon,
            iconsBundle.topIcon,
            iconsBundle.endIcon,
            iconsBundle.startIcon
        )
        setIcons()
    }

    private fun setIcons() {
        iconsBundle.setIcons(this)
    }

    override fun setDrawableForAll(drawable: IconicsDrawable?) {
        iconsBundle.startIcon = tryToEnableIconicsAnimation(drawable)
        iconsBundle.topIcon = tryToEnableIconicsAnimation(drawable)
        iconsBundle.endIcon = tryToEnableIconicsAnimation(drawable)
        iconsBundle.bottomIcon = tryToEnableIconicsAnimation(drawable)
        setIcons()
    }
    //endregion

    override fun setText(text: CharSequence?, type: BufferType) {
        if (!isInEditMode) {
            super.setText(text?.buildIconics(), type)
        } else {
            super.setText(text, type)
        }
    }
}
