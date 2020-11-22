package com.imuges.order.presenter

import com.imuges.order.activity.views.IMainView
import com.imuges.order.base.BasePresenter
import com.imuges.order.data.OrderSimpleInfo
import com.imuges.order.db.AppDatabaseManager

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
        for (i in 1..1000) {
            val bg = (Math.random() * 10).toInt() % 3 + 1
            mOrderData.add(
                OrderSimpleInfo(i, "A${i}", i + 1.0f, System.currentTimeMillis(), bg)
            )
        }
        mOrderViewData.addAll(mOrderData)
        view?.updateList()
    }

    /**
     * 获取页面数据
     */
    fun getViewData() = mOrderViewData

    /**
     * 搜索账单列表
     */
    fun searchOrder(name: String) {
        if (name.isEmpty()) {
            return
        }
        if (name == ":cancel") {
            mOrderViewData.clear()
            mOrderViewData.addAll(mOrderData)
            view?.updateList()
            return
        }
        mOrderViewData.clear()
        mOrderViewData.addAll(mOrderData.filter { it.name.contains(name) })
        view?.updateList()
    }

}