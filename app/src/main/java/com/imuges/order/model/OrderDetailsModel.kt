package com.imuges.order.model

import com.blankj.utilcode.util.GsonUtils
import com.google.gson.reflect.TypeToken
import com.imuges.order.data.OrderInfo
import com.nullpt.base.entity.GoodsOrderInfo
import com.nullpt.base.expan.db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author BGQ
 * 账单详情管理
 */
class OrderDetailsModel {

    private val mOrderDao by lazy { db().orderDao() }

    /**
     * 加载订单详情
     */
    fun loadOrderDetails(orderId: Int, call: ((data: OrderInfo) -> Unit)) {
        GlobalScope.launch {
            val order = mOrderDao.queryByOrderId(orderId)
            withContext(Dispatchers.Main) {
                call.invoke(
                    OrderInfo(
                        order.orderId!!,
                        order.name,
                        order.percent,
                        order.time,
                        order.lastModifyTime,
                        GsonUtils.fromJson(
                            order.goods,
                            object : TypeToken<MutableList<GoodsOrderInfo>>() {}.type
                        )
                    )
                )
            }
        }
    }

}