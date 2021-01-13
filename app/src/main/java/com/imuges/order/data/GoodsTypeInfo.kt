package com.imuges.order.data

/**
 * @author BGQ
 * 货物类别分类
 */
data class GoodsTypeInfo(
    val goodsTypeId: Int,
    val typeName: String,
    var select: Boolean = false
)