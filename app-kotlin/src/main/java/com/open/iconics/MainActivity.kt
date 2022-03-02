package com.open.iconics

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        iconics_button.setOnClickListener(this)
        iconics_image_button.setOnClickListener(this)
        iconics_image_view.setOnClickListener(this)
        iconics_text_view.setOnClickListener(this)
        iconics_compound_button.setOnClickListener(this)
        iconics_check_box.setOnClickListener(this)
        iconics_checkable_text_view.setOnClickListener(this)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onClick(v: View) {
        when (v.id) {
            R.id.iconics_button -> {
                startActivity(Intent(this, IconicsButtonActivity::class.java))
            }
            R.id.iconics_image_view -> {
                startActivity(Intent(this, IconicsImageViewActivity::class.java))
            }
            R.id.iconics_image_button -> {
                startActivity(Intent(this, IconicsImageButtonActivity::class.java))
            }
            R.id.iconics_text_view -> {
                startActivity(Intent(this, IconicsTextViewActivity::class.java))
            }
            R.id.iconics_compound_button -> {
                startActivity(Intent(this, IconicsCompoundButtonActivity::class.java))
            }
            R.id.iconics_check_box -> {
                startActivity(Intent(this, IconicsCheckBoxActivity::class.java))
            }
            R.id.iconics_checkable_text_view -> {
                startActivity(Intent(this, IconicsCheckableTextViewActivity::class.java))
            }
        }
    }
}

