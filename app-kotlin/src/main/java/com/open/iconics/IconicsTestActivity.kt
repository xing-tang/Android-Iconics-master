package com.open.iconics

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.*
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.open.iconics.databinding.ActivityIconicsTestBinding
import com.open.iconics.iconfont.ChangbaFont
import com.open.iconics.utils.*

class IconicsTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityIconicsTestBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_iconics_test)

        // Handle Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val themeValue1 = getThemeColor(android.R.attr.textColorPrimary)
        val themeValue2 = getThemeColor(android.R.attr.textColorPrimaryInverse)

        //Show how to style the text of an existing TextView
        Iconics.Builder()
            .style(
                ForegroundColorSpan(ContextCompat.getColor(this, R.color.red)),
                BackgroundColorSpan(Color.parseColor("#00000000")),
                RelativeSizeSpan(2f)
            )
            .styleFor(
                "font_topic",
                BackgroundColorSpan(Color.GREEN),
                ForegroundColorSpan(Color.parseColor("#33000000")),
                RelativeSizeSpan(2f)
            )
            .on(binding.test1)
            .build()

        //You can also do some advanced stuff like setting an image within a text
        val sb = SpannableString(binding.test5.text)
        val d = IconicsDrawable(this, ChangbaFont.font_openarrows).apply {
            sizeDp = 48
            paddingDp = 4
            colorInt = themeValue1
        }

        sb.setSpan(
            ImageSpan(d, DynamicDrawableSpan.ALIGN_BOTTOM),
            1,
            2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.test5.text = sb

        //Set the icon of an ImageView (or something else) as drawable
        binding.test2.setImageDrawable(
            IconicsDrawable(this, ChangbaFont.font_close_arrows).apply {
                sizeDp = 48
                colorString = "#aaFF0000"
                contourWidthDp = 1
            }
        )

        //Set the icon of an ImageView (or something else) as bitmap
        binding.test3.setImageBitmap(
            IconicsDrawable(this, ChangbaFont.font_openarrows).apply {
                sizeX = IconicsSize.dp(48)
                sizeY = IconicsSize.dp(32)
                paddingDp = 4
                roundedCornersDp = 8
                colorString = "#deFF0000"
            }.toBitmap()
        )

        //Show how to style the text of an existing button
        Iconics.Builder()
            .style(BackgroundColorSpan(themeValue1))
            .style(RelativeSizeSpan(2f))
            .style(ForegroundColorSpan(themeValue2))
            .on(binding.test4)
            .build()

        //Show how to style the text of an existing button
        val iconStateListDrawable = StateListDrawable()
        iconStateListDrawable.addState(
            intArrayOf(android.R.attr.state_pressed),
            IconicsDrawable(this, ChangbaFont.font_close_arrows).apply {
                sizeDp = 48
                colorString = "#aaFF0000"
                contourWidthDp = 1
            }
        )
        iconStateListDrawable.addState(
            intArrayOf(),
            IconicsDrawable(this, ChangbaFont.font_close_arrows).apply {
                sizeDp = 48
                colorString = "#aa00FF00"
                contourWidthDp = 2
            }
        )
        binding.test6.setImageDrawable(iconStateListDrawable)

        val span = SpannableStringBuilder(binding.test10.text)
        span.setSpan(
            StyleSpan(Typeface.BOLD),
            3,
            6,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        span.insert(20, "{font_editlist}")
        binding.test10.text = span
        binding.test10.text = binding.test10.text

        val iconicsDrawableBase = IconicsDrawable(this).apply {
            actionBar()
            colorInt = Color.GREEN
            backgroundColorInt = Color.RED
        }
        val array = IconicsArrayBuilder(iconicsDrawableBase)
            .add(ChangbaFont.font_openarrows)
            .add(ChangbaFont.font_album_num)
            .add("Hallo")
            .add('A')
            .add(";)")
            .build()
        binding.list.adapter = IconsAdapter(this, array)

        // Create icons for menu_navigation
        val planningIcon = IconicsDrawable(this, ChangbaFont.font_attention)
        val homeIcon = IconicsDrawable(this, ChangbaFont.font_rightarrows_gray)
        val calendarIcon = IconicsDrawable(this, ChangbaFont.font_singasong)

        // Set icons
        binding.navigation.menu.apply {
            findItem(R.id.navigation_home).icon = planningIcon
            findItem(R.id.navigation_dashboard).icon = homeIcon
            findItem(R.id.navigation_notifications).icon = calendarIcon
        }

        // Automatically process all icons in menu
        binding.navigationAuto.menu.parseXmlAndSetIconicsDrawables(this, R.menu.menu_playground)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_playground, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private inner class IconsAdapter internal constructor(
        context: Context,
        objects: Array<IconicsDrawable>
    ) : ArrayAdapter<IconicsDrawable>(context, 0, objects) {

        private val inflater: LayoutInflater = LayoutInflater.from(context)

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val v = inflater.inflate(R.layout.row_icon_array, parent, false)
            val icon = v.findViewById<ImageView>(android.R.id.icon)
            icon.setImageDrawable(getItem(position))
            return v
        }
    }

    companion object {
        @BindingAdapter("iconicsSrc", "iconicsColor")
        @JvmStatic
        fun loadIconicsImage(view: ImageView, name: String, color: Int?) {
            view.setImageDrawable(IconicsDrawable(view.context, name).apply {
                if (color != null) {
                    colorInt = color
                }
            })
        }
    }
}

