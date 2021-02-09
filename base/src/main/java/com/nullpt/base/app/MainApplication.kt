package com.nullpt.base.app

import android.app.Application
import android.content.Context
import android.util.Log
import com.nullpt.base.db.AppDatabaseManager
import com.nullpt.base.expan.TAG

/**
 * @author BGQ
 */
object MainApplication {

    /**
     * Application对象
     */
    lateinit var instance: Application

    /**
     * 初始化注入application对象
     */
    fun inject(instance: Application) {
        this.instance = instance
    }

    /**
     * Application生命周期
     * [Application.attachBaseContext]
     */
    fun attachBaseContext(base: Context) {
        Log.i(TAG, "attachBaseContext")
    }

    /**
     * Application生命周期
     * [Application.onCreate]
     */
    fun onCreate() {
        Log.i(TAG, "onCreate")
        AppDatabaseManager.init(instance)
    }

}