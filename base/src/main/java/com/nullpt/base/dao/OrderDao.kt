package com.nullpt.base.dao

import androidx.room.*
import com.nullpt.base.entity.Order

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