package com.open.iconics.utils

import androidx.annotation.RestrictTo

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
object IconicsPreconditions {

    @JvmStatic fun checkMappingPrefix(s: String) {
        if (s.length == 3) return
        throw IllegalArgumentException("The mapping prefix of a font must be 3 characters long.")
    }
}