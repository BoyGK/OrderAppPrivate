package com.imuges.order.base

import android.app.Application
import com.imuges.order.db.AppDatabaseManager

/**
 * @author BQG
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabaseManager.init(this)
    }

}