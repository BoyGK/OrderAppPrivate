package com.imuges.order.presenter

import com.imuges.order.activity.IAddOrderView
import com.imuges.order.base.BasePresenter
import com.imuges.order.data.GoodsTypeInfo


/**
 * @author BGQ
 * 货物类型
 */
class AddOrderTypePresenter : BasePresenter<IAddOrderView>() {

    private val mGoodsTypeData by lazy { mutableListOf<GoodsTypeInfo>() }

    override fun onViewCreate() {
        initFakeData()
    }

    private fun initFakeData() {
        for (i in 0..10) {
            mGoodsTypeData.add(GoodsTypeInfo(i, "Type-$i"))
        }
        mGoodsTypeData[0].select = true
        view?.updateTypeList()
    }

    fun getGoodsTypeData() = mGoodsTypeData

    /**
     * 定位
     */
    fun position(typeId: Int) {
        val position = mGoodsTypeData.indexOfFirst { it.goodsTypeId == typeId }
        for (i in mGoodsTypeData.indices) {
            mGoodsTypeData[i].select = false
        }
        mGoodsTypeData[position].select = true
        view?.positionType(position)
    }
}