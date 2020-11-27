package com.imuges.order.dao

import androidx.room.*
import com.imuges.order.data.entity.Goods

/**
 * @author BGQ
 */
@Dao
interface GoodsDao {

    @Query("select * from goods")
    fun queryAll(): MutableList<Goods>

    @Insert
    fun insert(goods: Goods)

    @Delete
    fun delete(goods: Goods)

    @Update
    fun update(goods: Goods)

}