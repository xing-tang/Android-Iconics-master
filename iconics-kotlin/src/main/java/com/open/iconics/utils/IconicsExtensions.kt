package com.open.iconics.utils

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.text.Spanned
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.LayoutInflaterCompat
import com.open.iconics.Iconics
import com.open.iconics.IconicsArrayBuilder
import com.open.iconics.IconicsDrawable
import com.open.iconics.context.IconicsContextWrapper
import com.open.iconics.context.IconicsLayoutInflater
import com.open.iconics.context.IconicsLayoutInflater2

/** Adaptation for [IconicsContextWrapper] */
fun Context.wrapByIconics(): ContextWrapper = IconicsContextWrapper.wrap(this)

/**
 * Compatible adaptation for setting IconicsLayoutInflater
 * @see IconicsLayoutInflater
 * @see IconicsLayoutInflater2
 * */
@Suppress("DEPRECATION")
fun LayoutInflater.setIconicsFactory(appCompatDelegate: AppCompatDelegate) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LayoutInflaterCompat.setFactory2(this, IconicsLayoutInflater2(appCompatDelegate))
    } else {
        LayoutInflaterCompat.setFactory(this, IconicsLayoutInflater(appCompatDelegate))
    }
}

/** Adaptation for [IconicsMenuInflaterUtil.inflate] */
@JvmOverloads
fun MenuInflater.inflateWithIconics(
    context: Context,
    menuId: Int,
    menu: Menu,
    checkSubMenu: Boolean = false
) {
    IconicsMenuInflaterUtil.inflate(this, context, menuId, menu, checkSubMenu)
}

/** Adaptation for [Iconics.Builder] with auto-executing [Iconics.BuilderString.build] */
fun Spanned.buildIconics(block: Iconics.Builder.() -> Unit = {}): Spanned {
    return Iconics.Builder().also(block).on(this).build()
}

/** Adaptation for [Iconics.Builder] with auto-executing [Iconics.BuilderString.build] */
fun String.buildIconics(block: Iconics.Builder.() -> Unit = {}): Spanned {
    return Iconics.Builder().also(block).on(this).build()
}

/** Adaptation for [Iconics.Builder] with auto-executing [Iconics.BuilderString.build] */
fun CharSequence.buildIconics(block: Iconics.Builder.() -> Unit = {}): Spanned {
    return Iconics.Builder().also(block).on(this).build()
}

/** Adaptation for [Iconics.Builder] with auto-executing [Iconics.BuilderString.build] */
fun StringBuilder.buildIconics(block: Iconics.Builder.() -> Unit = {}): Spanned {
    return Iconics.Builder().also(block).on(this).build()
}

/** Adaptation for [Iconics.Builder] with auto-executing [Iconics.BuilderView.build] */
fun TextView.buildIconics(block: Iconics.Builder.() -> Unit = {}) {
    Iconics.Builder().also(block).on(this).build()
}

/** Adaptation for [Iconics.Builder] with auto-executing [Iconics.BuilderView.build] */
fun Button.buildIconics(block: Iconics.Builder.() -> Unit = {}) {
    Iconics.Builder().also(block).on(this).build()
}

/** Adaptation for [IconicsArrayBuilder] with auto-executing [IconicsArrayBuilder.build] */
fun IconicsDrawable.createArray(
    block: IconicsArrayBuilder.() -> IconicsArrayBuilder
): Array<IconicsDrawable> {
    val builder = IconicsArrayBuilder(this)
    return block(builder).build()
}

/**
 * Uses the styleable tags to get the iconics data of menu items. Useful for set icons into
 * `BottomNavigationView`
 *
 *
 * By default, menus don't show icons for sub menus, but this can be enabled via reflection
 * So use this function if you want that sub menu icons are checked as well
 */
@JvmOverloads
fun Menu.parseXmlAndSetIconicsDrawables(
    context: Context,
    @MenuRes menuId: Int,
    checkSubMenu: Boolean = false
) {
    IconicsMenuInflaterUtil.parseXmlAndSetIconicsDrawables(context, menuId, this, checkSubMenu)
}

/** Returns icon prefix (first 3 chars) */
val String.iconPrefix
    get() = substring(0, 3)

/** Returns cleared icon name (all hyphens are replaced with underscores) */
val String.clearedIconName
    get() = (this as CharSequence).clearedIconName

/** Returns cleared icon name (all hyphens are replaced with underscores) */
val CharSequence.clearedIconName: String
    get() = replace("-".toRegex(), "_")

fun View.enableShadowSupport() {
    IconicsUtils.enableShadowSupport(this)
}