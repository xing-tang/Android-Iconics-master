package com.open.iconics.application

import android.app.Application
import android.content.Context
import com.open.iconics.Iconics
import com.open.iconics.iconfont.ChangbaIconFont

class BaseApplication : Application() {

    companion object {

        private lateinit var instance: BaseApplication

        fun getInstance(): BaseApplication = instance
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        // DynamicColors.applyToActivitiesIfAvailable(this)

        // register custom fonts like this (or also provide a font definition file)
        Iconics.registerFont(ChangbaIconFont())

        // Generic font creation process
        // GenericFont("GenericFont", "SampleGenericFont", "gnf", "font/materialdrawerfont.ttf")
        //     .also {
        //         it.registerIcon("person", '\ue800')
        //         it.registerIcon("up", '\ue801')
        //         it.registerIcon("down", '\ue802')
        //         Iconics.registerFont(it)
        //    }
    }
}
