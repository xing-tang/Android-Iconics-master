package com.open.iconics

import android.graphics.Typeface
import com.open.iconics.typeface.IIcon
import com.open.iconics.utils.icon

class IconicsArrayBuilder(private val iconBase: IconicsDrawable) {
    private val icons = ArrayList<Pair<Any, Typeface?>>()

    fun add(icon: IIcon): IconicsArrayBuilder {
        icons.add(Pair(icon, null))
        return this
    }

    @JvmOverloads
    fun add(icon: String, typeface: Typeface = Typeface.DEFAULT): IconicsArrayBuilder {
        icons.add(Pair(icon, typeface))
        return this
    }

    @JvmOverloads
    fun add(icon: Char, typeface: Typeface = Typeface.DEFAULT): IconicsArrayBuilder {
        icons.add(Pair(icon, typeface))
        return this
    }

    fun build(): Array<IconicsDrawable> {
        return icons.map { (_icon, _typeface) ->
            iconBase.copy().apply {
                when (_icon) {
                    is IIcon -> icon = _icon
                    is Char -> {
                        icon(_icon)
                        typeface = _typeface
                    }
                    is String -> {
                        iconText = _icon
                        typeface = _typeface
                    }
                }
            }
        }.toTypedArray()
    }
}
