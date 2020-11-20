package com.imuges.order.activity

import com.imuges.order.base.IBaseView
import com.imuges.order.data.GoodsOrderInfo
import com.imuges.order.data.GoodsTypeInfo

/**
 * @author BGQ
 */
interface IAddOrderView : IBaseView {

    fun setBackGround(background: Int)

    fun setCustomerName(name: String)

    /**
     * 经典语句
     */
    fun setOrderText(text: String)

    fun setGoodsToday(time: Long)

    fun updateTypeList()

    fun updateTypeItem(position: Int, data: GoodsTypeInfo)

    fun updateGoodsList()

    fun updateGoodsItem(position: Int, data: GoodsOrderInfo)

    fun positionType(position: Int)

    fun positionGoods(position: Int)

    fun setTotalPercent(percent: Float)

}