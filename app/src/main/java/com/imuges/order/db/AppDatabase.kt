package com.imuges.order.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.imuges.order.dao.GoodsDao
import com.imuges.order.dao.OrderDao
import com.imuges.order.dao.TypeDao
import com.imuges.order.data.entity.Goods
import com.imuges.order.data.entity.Order
import com.imuges.order.data.entity.Type

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