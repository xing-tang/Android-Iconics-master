package com.open.iconics.utils

import android.text.ParcelableSpan
import android.text.Spannable
import android.text.style.CharacterStyle
import com.open.iconics.typeface.ITypeface

internal class StyleContainer(
    var startIndex: Int = 0,
    var endIndex: Int = 0,
    var icon: String? = null,
    var font: ITypeface? = null,
    var span: ParcelableSpan? = null,
    var style: CharacterStyle? = null,
    var flags: Int = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
)
