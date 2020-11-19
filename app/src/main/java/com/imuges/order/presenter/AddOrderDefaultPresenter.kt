package com.imuges.order.presenter

import com.imuges.order.activity.IAddOrderView
import com.imuges.order.base.BasePresenter
import com.imuges.order.data.GoodsOrderInfo

/**
 * @author BGQ
 * 添加货物
 */
class AddOrderDefaultPresenter : BasePresenter<IAddOrderView>() {

    private val mGoodsData by lazy { mutableListOf<GoodsOrderInfo>() }

    override fun onViewCreate() {
        val bg = (Math.random() * 10).toInt() % 3 + 1
        view?.setBackGround(bg)
        view?.setCustomerName("ABCD")
        view?.setGoodsToday(System.currentTimeMillis())

        initFakeData()
    }

    private fun initFakeData() {
        for (i in 10..100) {
            mGoodsData.add(
                GoodsOrderInfo(
                    i, "Goods-$i", "", i * 1.1f, "个", i / 10, 0
                )
            )
        }
        view?.updateTypeList()
    }

    fun getGoodsData() = mGoodsData

    /**
     * 定位
     */
    fun position(typeId: Int) {
        val position = mGoodsData.indexOfFirst { it.goodsTypeId == typeId }
        view?.positionGoods(position)
    }
}