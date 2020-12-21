package com.imuges.order.presenter

import com.imuges.order.activity.views.IMainView
import com.imuges.order.data.OrderSimpleInfo
import com.imuges.order.model.MainModel
import com.nullpt.base.framework.BasePresenter

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

    override fun onViewReStart() {
        refresh()
    }

    /**
     * 初始化数据
     */
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
     * 获取历史订单id
     */
    fun getOrderId(position: Int): Int = mOrderViewData[position].orderId

    /**
     * 刷新数据
     */
    fun refresh() {
        mMainModel.loadOrders {
            mOrderData.clear()
            mOrderViewData.clear()
            mOrderData.addAll(it)
            mOrderViewData.addAll(mOrderData)
            view?.updateList()
            view?.hiddenRefreshing()
        }
    }

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

    /**
     * 删除历史订单
     */
    fun deleteItem(position: Int) {

    }

}