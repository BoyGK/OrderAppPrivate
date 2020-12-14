package com.nullpt.base.entity

/**
 * @author BGQ
 * 货物订购信息
 * [Order.goods]的实体信息
 */
data class GoodsOrderInfo(
    val goodsOrderId: Int,
    val goodsName: String,
    val picturePath: String,
    val percent: Float,
    val unit: String,//单位（例子：/只）
    val goodsTypeId: Int,
    var selectCount: Int
)