package com.open.iconics

import android.graphics.Color
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.open.iconics.iconfont.DevIcon
import com.open.iconics.utils.*
import kotlinx.android.synthetic.main.activity_iconics_button.*
import kotlinx.android.synthetic.main.activity_main.*

class IconicsButtonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iconics_button)
        initView()
    }

    private fun initView() {
        Iconics.Builder()
            .style(BackgroundColorSpan(ContextCompat.getColor(this, R.color.transparent)))
            .style(RelativeSizeSpan(1f))
            .style(ForegroundColorSpan(ContextCompat.getColor(this, R.color.red)))
            .styleFor(
                DevIcon.Icon.dev_ssh_plain,//"font_danmu_off",
                BackgroundColorSpan(Color.RED),
                ForegroundColorSpan(Color.parseColor("#ee000000")),
                RelativeSizeSpan(2f)
            )
            .on(button)
            .build()
        image_iconics_button.setOnClickListener {
            val drawable = IconicsDrawable(this@IconicsButtonActivity, DevIcon.Icon.dev_ssh_plain)
                .apply {
                    sizeDp = 150
                    // sizeX = IconicsSize.dp(150)
                    // sizeY = IconicsSize.dp(120)
                    // paddingPx = 4
                    paddingDp = 4
                    roundedCornersDp = 8
                    colorString = "#deFF0000"
                }
                .toBitmap()
            // image_iconics_button.icon = drawable
            image_iconics_button.setImageBitmap(drawable)
        }
    }
}