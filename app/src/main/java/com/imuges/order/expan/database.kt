package com.imuges.order.expan

import com.imuges.order.db.AppDatabase
import com.imuges.order.db.AppDatabaseManager


/**
 * @author BQG
 * 数据库简易调用
 */


fun db(): AppDatabase = AppDatabaseManager.db