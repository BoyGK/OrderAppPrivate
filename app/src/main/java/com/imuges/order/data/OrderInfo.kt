package com.imuges.order.data

/**
 * @author BGQ
 * 订单详细信息
 */
data class OrderInfo(
    val orderId: Int,
    val name: String,
    val percent: Float,
    val time: Long,
    val lastModifyTime: Long,//最后修改时间
    val goodsOrderInfos: MutableList<GoodsOrderInfo>//订单列表信息
)