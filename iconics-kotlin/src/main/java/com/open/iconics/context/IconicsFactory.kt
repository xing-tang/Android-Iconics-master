package com.open.iconics.context

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import com.open.iconics.Iconics
import com.open.iconics.R
import com.open.iconics.animation.tryToEnableIconicsAnimation
import com.open.iconics.utils.buildIconics

internal object IconicsFactory {

    @JvmStatic
    fun onViewCreated(view: View?, context: Context, attrs: AttributeSet): View? {
        if (view != null && view.getTag(R.id.iconics_tag_id) != true) {
            onViewCreatedInternal(view, context, attrs)
            view.setTag(R.id.iconics_tag_id, true)
        }
        return view
    }

    @JvmStatic
    @SuppressLint("RestrictedApi")
    private fun onViewCreatedInternal(view: View, context: Context, attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }

        when (view) {
            is ActionMenuItemView -> {
                IconicsAttrsApplier.getIconicsDrawable(context, attrs)?.let {
                    try {
                        view.setIcon(view.tryToEnableIconicsAnimation(it))
                    } catch (ex: Exception) {
                        Log.e(
                            "IconicsFactory",
                            "Could not apply the icon as the `ActionMenuItemView` is not ready."
                        )
                    }
                }
            }
            is EditText -> {
                // for an editText we only style initial as styling the Editable causes problems!
                view.buildIconics()
            }
            is TextView -> {
                view.buildIconics()

                view.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(cs: CharSequence, i: Int, i1: Int, i2: Int) {
                    }

                    override fun onTextChanged(cs: CharSequence, i: Int, i1: Int, i2: Int) {
                    }

                    override fun afterTextChanged(editable: Editable) {
                        Iconics.styleEditable(editable)
                    }
                })
            }
            is ImageView -> {
                IconicsAttrsApplier.getIconicsDrawable(context, attrs)?.let {
                    view.setImageDrawable(view.tryToEnableIconicsAnimation(it))
                }
            }
        }
    }
}