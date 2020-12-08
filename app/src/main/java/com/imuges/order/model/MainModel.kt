package com.imuges.order.model

import com.imuges.order.data.OrderSimpleInfo
import com.imuges.order.expan.db
import com.imuges.order.util.BackGroundTransform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author BGQ
 * 历史账单管理
 */
class MainModel {

    private val mOrderDao by lazy { db().orderDao() }

    /**
     * 加载订单
     */
    fun loadOrders(call: ((data: MutableList<OrderSimpleInfo>) -> Unit)) {
        GlobalScope.launch {
            val list = mOrderDao.queryAll()
            val result = list.flatMap {
                val randomBg = BackGroundTransform.randomBackground()
                mutableListOf(OrderSimpleInfo(it.orderId!!, it.name, it.percent, it.time, randomBg))
            }
            withContext(Dispatchers.Main) {
                call.invoke(result.toMutableList())
            }
        }
    }

}