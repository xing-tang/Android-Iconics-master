package com.open.iconics.context

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View

import androidx.appcompat.app.AppCompatDelegate

@Deprecated(message = "Use the IconicsImageView or IconicsTextView instead")
class IconicsLayoutInflater2(
    private val appCompatDelegate: AppCompatDelegate
) : LayoutInflater.Factory2 {

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        val view = appCompatDelegate.createView(parent, name, context, attrs)
        return IconicsFactory.onViewCreated(view, context, attrs)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val view = appCompatDelegate.createView(null, name, context, attrs)
        return IconicsFactory.onViewCreated(view, context, attrs)
    }
}
