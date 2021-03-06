package com.open.iconics.view

import android.content.Context
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.SoundEffectConstants
import android.view.View
import android.widget.Checkable
import androidx.core.widget.TextViewCompat
import com.open.iconics.IconicsDrawable
import com.open.iconics.animation.tryToEnableIconicsAnimation
import com.open.iconics.internal.CheckedCompoundIconicsDrawables
import com.open.iconics.internal.CompoundIconsBundle
import com.open.iconics.internal.IconicsViewsAttrsApplier
import com.open.iconics.utils.IconicsUtils

open class IconicsCheckableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = androidx.appcompat.R.attr.checkedTextViewStyle
) : IconicsTextView(context, attrs, defStyle), Checkable, CheckedCompoundIconicsDrawables {

    companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }

    private val checkedIconsBundle: CompoundIconsBundle = CompoundIconsBundle()
    private var isAnimateChanges: Boolean = false

    private var isChecked: Boolean = false
    private var isBroadcasting: Boolean = false

    private var onCheckedChangeListener: OnCheckedChangeListener? = null

    init {
        isFocusable = true
        isClickable = true

        //taking checked state attrs
        IconicsViewsAttrsApplier.readIconicsCheckableTextView(context, attrs, checkedIconsBundle)
        isAnimateChanges = IconicsViewsAttrsApplier.isIconicsAnimateChanges(context, attrs)

        tryToEnableIconicsAnimation(
            checkedIconsBundle.bottomIcon,
            checkedIconsBundle.topIcon,
            checkedIconsBundle.endIcon,
            checkedIconsBundle.startIcon
        )

        //setting created icons
        setIcons()
    }

    //region CheckedCompoundIconicsDrawablesImpl
    override var checkedIconicsDrawableStart: IconicsDrawable?
        get() = checkedIconsBundle.startIcon
        set(value) {
            checkedIconsBundle.startIcon = tryToEnableIconicsAnimation(value)
            setIcons()
        }

    override var checkedIconicsDrawableTop: IconicsDrawable?
        get() = checkedIconsBundle.topIcon
        set(value) {
            checkedIconsBundle.topIcon = tryToEnableIconicsAnimation(value)
            setIcons()
        }

    override var checkedIconicsDrawableEnd: IconicsDrawable?
        get() = checkedIconsBundle.endIcon
        set(value) {
            checkedIconsBundle.endIcon = tryToEnableIconicsAnimation(value)
            setIcons()
        }

    override var checkedIconicsDrawableBottom: IconicsDrawable?
        get() = checkedIconsBundle.bottomIcon
        set(value) {
            checkedIconsBundle.bottomIcon = tryToEnableIconicsAnimation(value)
            setIcons()
        }

    private fun setIcons() {
        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(
            this,
            createStatesStart(),
            createStatesTop(),
            createStatesEnd(),
            createStatesBottom()
        )
    }

    private fun createStatesStart(): StateListDrawable {
        return IconicsUtils.getCheckableIconStateList(
            context,
            iconsBundle.startIcon,
            checkedIconsBundle.startIcon,
            isAnimateChanges
        )
    }

    private fun createStatesTop(): StateListDrawable {
        return IconicsUtils.getCheckableIconStateList(
            context,
            iconsBundle.topIcon,
            checkedIconsBundle.topIcon,
            isAnimateChanges
        )
    }

    private fun createStatesEnd(): StateListDrawable {
        return IconicsUtils.getCheckableIconStateList(
            context,
            iconsBundle.endIcon,
            checkedIconsBundle.endIcon,
            isAnimateChanges
        )
    }

    private fun createStatesBottom(): StateListDrawable {
        return IconicsUtils.getCheckableIconStateList(
            context,
            iconsBundle.bottomIcon,
            checkedIconsBundle.bottomIcon,
            isAnimateChanges
        )
    }

    override fun getAccessibilityClassName(): CharSequence {
        return IconicsCheckableTextView::class.java.name
    }

    override fun setChecked(checked: Boolean) {
        if (isChecked != checked) {
            isChecked = checked
            refreshDrawableState()

            // Avoid infinite recursions if setChecked() is called from a listener
            if (isBroadcasting) {
                return
            }

            isBroadcasting = true
            onCheckedChangeListener?.onCheckedChanged(this, isChecked)
            isBroadcasting = false
        }
    }

    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener?) {
        onCheckedChangeListener = listener
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace.inc())
        if (isChecked) {
            View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    override fun isChecked(): Boolean = isChecked

    override fun toggle() {
        isChecked = !isChecked
    }

    override fun performClick(): Boolean {
        toggle()

        val handled = super.performClick()
        if (!handled) {
            playSoundEffect(SoundEffectConstants.CLICK)
        }

        return handled
    }

    override fun setCheckedDrawableForAll(drawable: IconicsDrawable?) {
        checkedIconsBundle.startIcon = tryToEnableIconicsAnimation(drawable)
        checkedIconsBundle.topIcon = tryToEnableIconicsAnimation(drawable)
        checkedIconsBundle.endIcon = tryToEnableIconicsAnimation(drawable)
        checkedIconsBundle.bottomIcon = tryToEnableIconicsAnimation(drawable)
        setIcons()
    }
    //endregion

    interface OnCheckedChangeListener {
        fun onCheckedChanged(buttonView: IconicsCheckableTextView, isChecked: Boolean)
    }
}
