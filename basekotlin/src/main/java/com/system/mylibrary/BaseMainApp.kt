package com.system.mylibrary

import android.content.Context


import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

/**
 * 创建人： zhoudingwen
 * 创建时间：2018/4/2
 */

open class BaseMainApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        mainApp = this
    }
    companion object {
        private var mainApp: BaseMainApp? = null
        val context: Context?
            get() = mainApp
    }
}
