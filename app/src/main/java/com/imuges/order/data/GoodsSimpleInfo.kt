package com.imuges.order.data

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by "BGQ" on 2020/11/22.
 * 添加货物时的货物简易信息
 */
data class GoodsSimpleInfo(
    val goodId: Int,
    val typeId: Int,
    val goodsName: String,
    val percent: Float,
    val unit: String,
    val picturePath: String,
    override val itemType: Int = NORMAL//0 add ,1 normal
) : MultiItemEntity {
    companion object {
        const val ADD = 0
        const val NORMAL = 1
    }
}