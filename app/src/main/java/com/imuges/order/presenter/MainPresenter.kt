package com.imuges.order.presenter

import com.imuges.order.R
import com.imuges.order.activity.IMainView
import com.imuges.order.base.BasePresenter
import com.imuges.order.data.OrderSimpleInfo

/**
 * @author BGQ
 */
class MainPresenter : BasePresenter<IMainView>() {

    /**
     * 原数据
     */
    private val mOrderData by lazy { mutableListOf<OrderSimpleInfo>() }

    /**
     * 页面数据
     */
    private val mOrderViewData by lazy { mutableListOf<OrderSimpleInfo>() }

    override fun onViewCreate() {
        initFakeData()
    }

    private fun initFakeData() {
        mOrderData.add(
            OrderSimpleInfo(0, "A", 1.0f, System.currentTimeMillis(), R.mipmap.ic_test)
        )
        mOrderData.add(
            OrderSimpleInfo(1, "b", 1.2f, System.currentTimeMillis(), R.mipmap.ic_test)
        )
        mOrderData.add(
            OrderSimpleInfo(2, "B", 1.9f, System.currentTimeMillis(), R.mipmap.ic_test)
        )
        mOrderData.add(
            OrderSimpleInfo(3, "D", 1.56f, System.currentTimeMillis(), R.mipmap.ic_test)
        )
        mOrderData.add(
            OrderSimpleInfo(4, "E", 1.1f, System.currentTimeMillis(), R.mipmap.ic_test)
        )
        mOrderData.add(
            OrderSimpleInfo(5, "F", 1.066f, System.currentTimeMillis(), R.mipmap.ic_test)
        )
        mOrderViewData.addAll(mOrderData)
        view?.updateList()
    }

    /**
     * 获取页面数据
     */
    fun getViewData() = mOrderViewData

}