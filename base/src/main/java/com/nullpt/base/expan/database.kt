package com.nullpt.base.expan

import com.nullpt.base.db.AppDatabase
import com.nullpt.base.db.AppDatabaseManager


/**
 * @author BQG
 * 数据库简易调用
 */


fun db(): AppDatabase = AppDatabaseManager.db