package com.open.iconics.typeface

interface IIcon {
    val formattedName: String
        get() = "{$name}"

    val name: String

    val character: Char

    val typeface: ITypeface
}