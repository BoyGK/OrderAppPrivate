package com.imuges.order.dao

import androidx.room.*
import com.imuges.order.data.entity.Order

/**
 * @author BGQ
 */
@Dao
interface OrderDao {

    @Query("select * from history_order")
    fun queryAll(): MutableList<Order>

    @Insert
    fun insert(order: Order)

    @Delete
    fun delete(order: Order)

    @Update
    fun update(order: Order)

}