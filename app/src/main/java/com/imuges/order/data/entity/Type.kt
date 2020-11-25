package com.imuges.order.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author BGQ
 */
@Entity
data class Type(
    /**
     * 类型id
     */
    @PrimaryKey val typeId: Int,
    /**
     * 类型名称
     */
    @ColumnInfo(name = "typeName") val typeName: String
)