package com.open.iconics.typeface

import android.graphics.Typeface
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat

interface ITypeface {

    val rawTypeface: Typeface
        get() = runCatching {
            ResourcesCompat.getFont(IconicsHolder.applicationContext, fontRes)
        }.getOrNull() ?: Typeface.DEFAULT

    @get:FontRes val fontRes: Int

    val characters: Map<String, Char>

    /** The Mapping Prefix to identify this font must have a length of 3 */
    val mappingPrefix: String

    val fontName: String

    val version: String

    val iconCount: Int

    val icons: List<String>

    val author: String

    val url: String

    val description: String

    val license: String

    val licenseUrl: String

    fun getIcon(key: String): IIcon
}