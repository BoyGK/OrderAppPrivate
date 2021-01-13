package com.imuges.order.app

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.nullpt.base.app.MainApplication
import com.nullpt.base.db.AppDatabaseManager

/**
 * @author BQG
 */
class App : Application(), LifecycleOwner {

    /**
     * Application生命周期感知
     */
    private var lifecycleRegistry: LifecycleRegistry

    init {
        MainApplication.inject(this)

        lifecycleRegistry = LifecycleRegistry(this)
        lifecycle.addObserver(MainApplication())
    }

    override fun onCreate() {
        super.onCreate()
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    override fun getLifecycle() = lifecycleRegistry

}