package com.imuges.order.dao

import androidx.room.*
import com.imuges.order.data.entity.Type

/**
 * @author BGQ
 */
@Dao
interface TypeDao {

    @Query("select * from type")
    fun queryAll(): MutableList<Type>

    @Insert
    fun insert(type: Type)

    @Delete
    fun delete(type: Type)

    @Update
    fun update(type: Type)

}