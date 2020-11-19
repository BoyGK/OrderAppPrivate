package com.imuges.order.activity

import com.imuges.order.base.IBaseView
import com.imuges.order.data.GoodsOrderInfo

/**
 * @author BGQ
 */
interface IAddOrderView : IBaseView {

    fun setBackGround(background: Int)

    fun setCustomerName(name: String)

    fun setGoodsToday(time: Long)

    fun updateTypeList()

    fun updateGoodsList()

    fun updateGoodsItem(position: Int, data: GoodsOrderInfo)

    fun positionType(position: Int)

    fun positionGoods(position: Int)

}