package com.imuges.order.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by "nullpointexception0" on 2020/11/22.
 */
@Entity
data class Order(
    /**
     * 订单id
     */
    @PrimaryKey(autoGenerate = true) val orderId: Int? = null,
    /**
     * 订单对应商家名称
     */
    @ColumnInfo(name = "name") val name: String,
    /**
     * 订单交易总额
     */
    @ColumnInfo(name = "percent") val percent: Float,
    /**
     * 订单创建时间
     */
    @ColumnInfo(name = "time") val time: String,
    /**
     * 订单最后修改时间
     */
    @ColumnInfo(name = "lastModifyTime") val lastModifyTime: String,
    /**
     * 订单货物列表
     * @param {MutableList<GoodsOrderInfo>}:json格式
     */
    @ColumnInfo(name = "goodsList") val goods: String
)