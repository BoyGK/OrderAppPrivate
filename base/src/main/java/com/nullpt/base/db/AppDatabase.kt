package com.nullpt.base.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nullpt.base.dao.GoodsDao
import com.nullpt.base.dao.OrderDao
import com.nullpt.base.dao.TypeDao
import com.nullpt.base.entity.Goods
import com.nullpt.base.entity.Order
import com.nullpt.base.entity.Type

/**
 * @author BGQ
 * 数据库
 */
@Database(entities = [Goods::class, Order::class, Type::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    /**
     * 货物查询
     */
    abstract fun goodsDao(): GoodsDao

    /**
     * 订单查询
     */
    abstract fun orderDao(): OrderDao

    /**
     * 货物类型查新
     */
    abstract fun typeDao(): TypeDao

}