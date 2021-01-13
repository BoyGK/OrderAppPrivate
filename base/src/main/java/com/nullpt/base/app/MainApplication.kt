package com.nullpt.base.app

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.nullpt.base.db.AppDatabaseManager
import com.nullpt.base.expan.TAG

/**
 * @author BGQ
 */
class MainApplication : LifecycleObserver {

    companion object {
        /**
         * Application对象
         */
        lateinit var instance: Application

        fun inject(instance: Application) {
            this.instance = instance
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.i(TAG, "onCreate")
        AppDatabaseManager.init(instance)
    }

}