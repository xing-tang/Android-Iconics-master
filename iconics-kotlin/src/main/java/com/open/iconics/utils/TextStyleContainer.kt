package com.open.iconics.utils

import android.text.SpannableStringBuilder
import java.util.LinkedList

internal class TextStyleContainer(
    var spannableStringBuilder: SpannableStringBuilder,
    var styleContainers: LinkedList<StyleContainer>
)