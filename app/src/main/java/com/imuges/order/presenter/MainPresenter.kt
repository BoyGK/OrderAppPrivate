package com.imuges.order.presenter

import com.imuges.order.activity.views.IMainView
import com.imuges.order.base.BasePresenter
import com.imuges.order.data.OrderSimpleInfo
import com.imuges.order.model.MainModel

/**
 * @author BGQ
 */
class MainPresenter : BasePresenter<IMainView>() {

    private val mMainModel by lazy { MainModel() }

    /**
     * 原数据
     */
    private val mOrderData by lazy { mutableListOf<OrderSimpleInfo>() }

    /**
     * 页面数据
     */
    private val mOrderViewData by lazy { mutableListOf<OrderSimpleInfo>() }

    override fun onViewCreate() {
        initData()
    }

    private fun initData() {
        mMainModel.loadOrders {
            mOrderData.addAll(it)
            mOrderViewData.addAll(mOrderData)
            view?.updateList()
        }
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