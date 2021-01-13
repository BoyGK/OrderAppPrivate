package com.imuges.order.activity.views

import com.imuges.order.data.GoodsTypeInfo
import com.nullpt.base.entity.GoodsOrderInfo
import com.nullpt.base.framework.IBaseView

/**
 * @author BGQ
 */
interface IAddOrderView : IBaseView {

    fun getInitOrderId(): Int

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

    fun showLoading()

    fun hiddenLoading()

    fun createOrderSuccess()

    fun createOrderFailByNoMerchantName()

    fun createOrderFailByNoSelect()
}