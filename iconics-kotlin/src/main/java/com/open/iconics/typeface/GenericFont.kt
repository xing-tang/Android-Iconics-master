package com.open.iconics.typeface

import android.graphics.Typeface
import androidx.annotation.FontRes
import com.open.iconics.Iconics
import com.open.iconics.utils.IconicsPreconditions
import java.util.LinkedList

open class GenericFont : ITypeface {
    override val fontName: String

    override val author: String

    override val mappingPrefix: String

    override val fontRes: Int

    private val fontFile: String

    private val chars = HashMap<String, Char>()

    override val rawTypeface: Typeface
        get() = try {
            Typeface.createFromAsset(Iconics.applicationContext.assets, fontFile)
        } catch (ignored: Exception) {
            super.rawTypeface
        }

    override val characters: Map<String, Char>
        get() = chars

    override val version: String
        get() = "1.0.0"

    override val iconCount: Int
        get() = characters.size

    override val icons: List<String>
        get() = characters.keys.toCollection(LinkedList())

    override val url: String
        get() = ""

    override val description: String
        get() = ""

    override val license: String
        get() = ""

    override val licenseUrl: String
        get() = ""

    protected constructor() : this("GenericFont", "GenericAuthor", "", "")

    constructor(
        mappingPrefix: String,
        fontFile: String
    ) : this("GenericFont", "GenericAuthor", mappingPrefix, fontFile)

    constructor(
        mappingPrefix: String,
        @FontRes fontRes: Int
    ) : this("GenericFont", "GenericAuthor", mappingPrefix, fontRes)

    @Suppress("LeakingThis")
    constructor(fontName: String, author: String, mappingPrefix: String, fontFile: String) {
        IconicsPreconditions.checkMappingPrefix(mappingPrefix)
        this.fontName = fontName
        this.author = author
        this.mappingPrefix = mappingPrefix
        this.fontFile = fontFile
        this.fontRes = -1
    }

    @Suppress("LeakingThis")
    constructor(fontName: String, author: String, mappingPrefix: String, @FontRes fontRes: Int) {
        IconicsPreconditions.checkMappingPrefix(mappingPrefix)
        this.fontName = fontName
        this.author = author
        this.mappingPrefix = mappingPrefix
        this.fontFile = ""
        this.fontRes = fontRes
    }

    fun registerIcon(name: String, char: Char) {
        chars[mappingPrefix + "_" + name] = char
    }

    override fun getIcon(key: String): IIcon = Icon(key, chars.getValue(key)).withTypeface(this)

    inner class Icon : IIcon {
        private val customName: String?
        private var customTypeface: ITypeface? = null

        constructor(c: Char) {
            this.customName = null
            this.character = c
        }

        constructor(name: String, c: Char) {
            this.customName = name
            this.character = c
        }

        override val character: Char

        override val name: String
            get() = customName ?: character.toString()

        override val typeface: ITypeface
            get() = customTypeface ?: this@GenericFont

        fun withTypeface(typeface: ITypeface?): Icon {
            this.customTypeface = typeface
            return this
        }
    }
}
