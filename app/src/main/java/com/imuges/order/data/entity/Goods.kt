package com.imuges.order.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author BGQ
 */
@Entity
data class Goods(
    /**
     * 货物id
     */
    @PrimaryKey val goodsId: Int,
    /**
     * 货物名称
     */
    @ColumnInfo(name = "goodsName") val goodsName: String,
    /**
     * 货物价格
     */
    @ColumnInfo(name = "percent") val percent: Float,
    /**
     * 货物单位
     */
    @ColumnInfo(name = "unit") val unit: String,
    /**
     * 货物图片
     */
    @ColumnInfo(name = "picturePath") val picturePath: String,
    /**
     * 货物类型id
     */
    @ColumnInfo(name = "goodsTypeId") val goodsTypeId: Int
)