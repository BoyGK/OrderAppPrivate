package com.imuges.order.app

import android.app.Application
import android.content.Context
import com.nullpt.base.app.MainApplication
import com.nullpt.hotfit.Hotfit
import com.nullpt.hotfit.Hotfit2
import com.nullpt.hotfit.Hotfit3

/**
 * @author BQG
 */
class App : Application() {

    init {
        MainApplication.inject(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MainApplication.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        MainApplication.onCreate()
        //启动热修复
        //Hotfit.check(this)
        //Hotfit2.check(this)
        //Hotfit3.check(this)
    }

}