package com.imuges.order.model

import com.blankj.utilcode.util.GsonUtils
import com.imuges.order.data.GoodsSimpleInfo
import com.imuges.order.data.GoodsTypeInfo
import com.nullpt.base.entity.GoodsOrderInfo
import com.nullpt.base.entity.Order
import com.nullpt.base.expan.db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author BQG
 * 创建订单
 */
class AddOrderModel {

    private val mDataFromAddGoodModel by lazy { AddGoodsModel() }
    private val mOrderDao by lazy { db().orderDao() }

    /**
     * 加载货物类型
     */
    fun loadTypes(call: ((data: MutableList<GoodsTypeInfo>) -> Unit)) {
        mDataFromAddGoodModel.loadTypes(call)
    }

    /**
     * 加载货物
     */
    fun loadGoods(call: ((data: MutableList<GoodsSimpleInfo>) -> Unit)) {
        mDataFromAddGoodModel.loadGoods(call)
    }

    /**
     * 创建订单
     */
    fun createOrders(
        name: String,
        percent: Float,
        orders: MutableList<GoodsOrderInfo>,
        call: () -> Unit = {}
    ) {
        GlobalScope.launch {
            val dbOrder = Order(
                null,
                name = name,
                percent = percent,
                time = System.currentTimeMillis(),
                lastModifyTime = System.currentTimeMillis(),
                goods = GsonUtils.toJson(orders)
            )
            mOrderDao.insert(dbOrder)
            withContext(Dispatchers.Main) {
                call.invoke()
            }
        }
    }

}