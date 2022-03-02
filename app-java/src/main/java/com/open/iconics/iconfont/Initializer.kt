package com.open.iconics.iconfont

import android.content.Context
import com.open.iconics.typeface.ITypeface
import com.open.iconics.typeface.IconicsHolder
import com.open.iconics.typeface.IconicsInitializer

class Initializer : androidx.startup.Initializer<ITypeface> {
    override fun create(context: Context): ITypeface {
        IconicsHolder.registerFont(DevIcon)
        return DevIcon
    }

    override fun dependencies(): List<Class<out androidx.startup.Initializer<*>>> {
        return listOf(IconicsInitializer::class.java)
    }
}