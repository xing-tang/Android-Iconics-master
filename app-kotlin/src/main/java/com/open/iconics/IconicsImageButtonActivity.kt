package com.open.iconics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.open.iconics.iconfont.ChangbaFont
import com.open.iconics.utils.colorString
import com.open.iconics.utils.paddingDp
import com.open.iconics.utils.roundedCornersDp
import com.open.iconics.utils.sizeDp
import kotlinx.android.synthetic.main.activity_iconics_image_button.*

class IconicsImageButtonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iconics_image_button)
        initView()
    }

    private fun initView() {
        image_iconics_button.setOnClickListener {
            val drawable = IconicsDrawable(it.context, ChangbaFont.font_unlike)
                .apply {
                    sizeDp = 150
                    // sizeX = IconicsSize.dp(48)
                    // sizeY = IconicsSize.dp(32)
                    // paddingPx = 4
                    paddingDp = 4
                    roundedCornersDp = 8
                    colorString = "#deFF0000"
                }
            // .toBitmap()
            // image_iconics_button.setImageBitmap(drawable)
            image_iconics_button.icon = drawable
        }
    }
}