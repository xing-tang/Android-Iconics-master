@file:Suppress("DEPRECATION")

package com.open.iconics.context

import android.content.Context
import android.util.AttributeSet
import android.view.View

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.LayoutInflaterFactory

@Deprecated(message = "Use the IconicsImageView or IconicsTextView instead")
class IconicsLayoutInflater(
    private val appCompatDelegate: AppCompatDelegate
) : LayoutInflaterFactory {

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        val view = appCompatDelegate.createView(parent, name, context, attrs)
        return IconicsFactory.onViewCreated(view, context, attrs)
    }
}
