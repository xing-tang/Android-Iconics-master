package com.open.iconics.typeface

import android.content.Context
import com.open.iconics.utils.IconicsPreconditions

object IconicsHolder {
    private var context: Context? = null

    val FONTS = HashMap<String, ITypeface>()

    @JvmStatic var applicationContext: Context
        get() = context ?: errorContextNotInitialized()
        set(value) {
            if (context == null) {
                context = value.applicationContext
            }
        }

    private fun errorContextNotInitialized(): Nothing {
        val message = buildString {
            append("A 'Iconics.init(context)' has to happen first. ")
            append("Call from your application. ")
            append("Usually this happens via an 'IconicsDrawable' usage.")
        }
        throw RuntimeException(message)
    }

    /** Registers a fonts into the FONTS array for performance */
    @JvmStatic fun registerFont(font: ITypeface): Boolean {
        FONTS[font.mappingPrefix] = font.validate()
        return true
    }

    /** Perform a basic sanity check for a font. */
    @JvmStatic private fun ITypeface.validate(): ITypeface {
        IconicsPreconditions.checkMappingPrefix(mappingPrefix)
        return this
    }
}