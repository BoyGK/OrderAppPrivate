package com.nullpt.base.dao

import androidx.room.*
import com.nullpt.base.entity.Type

/**
 * @author BGQ
 */
@Dao
interface TypeDao {

    @Query("select * from type")
    fun queryAll(): MutableList<Type>

    @Query("select * from type where typeName = :name")
    fun queryByName(name: String): Type

    @Insert
    fun insert(type: Type)

    @Delete
    fun delete(type: Type)

    @Update
    fun update(type: Type)

}