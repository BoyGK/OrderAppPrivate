package com.nullpt.base.db

import android.content.Context
import androidx.room.Room

/**
 * @author BQG
 * 数据库管理
 */
object AppDatabaseManager {

    lateinit var db: AppDatabase

    fun init(applicationContext: Context) {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-orderApp"
        ).build()
    }

}