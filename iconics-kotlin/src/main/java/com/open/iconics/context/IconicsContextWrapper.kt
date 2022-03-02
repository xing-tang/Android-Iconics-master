package com.open.iconics.context

import android.content.Context
import android.content.ContextWrapper
import android.view.LayoutInflater

/**
 * https://github.com/chrisjenx/Calligraphy
 */
@Deprecated(message = "Use the IconicsImageView or IconicsTextView instead")
class IconicsContextWrapper private constructor(base: Context) : ContextWrapper(base) {

    private val inflater by lazy {
        InternalLayoutInflater(LayoutInflater.from(baseContext), this, false)
    }

    override fun getSystemService(name: String): Any? {
        return if (Context.LAYOUT_INFLATER_SERVICE == name) {
            inflater
        } else {
            super.getSystemService(name)
        }
    }

    companion object {
        @JvmStatic fun wrap(base: Context): ContextWrapper = IconicsContextWrapper(base)
    }
}