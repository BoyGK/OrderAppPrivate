package com.imuges.order.data

/**
 * @author BGQ
 * 订单简易信息
 */
data class OrderSimpleInfo(
    val orderId: Int,
    val name: String,
    val percent: Float,
    val time: Long,
    val backgroundResId: Int
)