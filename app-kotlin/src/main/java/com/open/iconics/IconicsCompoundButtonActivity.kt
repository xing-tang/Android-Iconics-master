package com.open.iconics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.open.iconics.iconfont.ChangbaFont
import com.open.iconics.utils.colorString
import com.open.iconics.utils.sizeDp
import kotlinx.android.synthetic.main.activity_iconics_compound_button.*

class IconicsCompoundButtonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iconics_compound_button)
        initView()
    }

    private fun initView() {
        val checkDrawable = IconicsDrawable(this, ChangbaFont.font_liked)
            .apply {
                sizeDp = 50
                colorString = "#FFFF0000"
            }
        val uncheckDrawable = IconicsDrawable(this, ChangbaFont.font_unlike)
            .apply {
                sizeDp = 50
                colorString = "#FFFFFF00"
            }
        button_iconics_compound?.apply {
            checkedIcon = checkDrawable
            uncheckedIcon = uncheckDrawable
        }
        button_iconics_compound.setOnClickListener {

        }
    }
}