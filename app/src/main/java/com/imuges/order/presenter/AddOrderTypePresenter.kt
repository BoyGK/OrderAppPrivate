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
     * 选中
     */
    fun select(position: Int) {
        for (i in mGoodsTypeData.indices) {
            if (mGoodsTypeData[i].select) {
                view?.updateTypeItem(i, mGoodsTypeData[i])
            }
            mGoodsTypeData[i].select = false
        }
        mGoodsTypeData[position].select = true
        view?.updateTypeItem(position, mGoodsTypeData[position])
    }

    /**
     * 定位
     */
    fun position(typeId: Int) {
        val position = mGoodsTypeData.indexOfFirst { it.goodsTypeId == typeId }
        if (mGoodsTypeData[position].select) {
            return
        }
        select(position)
        view?.positionType(position)
    }
}