package com.nullpt.base.dao

import androidx.room.*
import com.nullpt.base.entity.Goods

/**
 * @author BGQ
 */
@Dao
interface GoodsDao {

    @Query("select * from goods")
    fun queryAll(): MutableList<Goods>

    @Query("select * from goods where goodsName = :name and goodsTypeId = :typeId")
    fun queryByName(name: String, typeId: Int): Goods

    @Insert
    fun insert(goods: Goods)

    @Delete
    fun delete(goods: Goods)

    @Update
    fun update(goods: Goods)

}