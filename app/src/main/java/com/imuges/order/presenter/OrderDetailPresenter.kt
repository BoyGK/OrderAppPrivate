package com.imuges.order.presenter

import com.blankj.utilcode.util.TimeUtils
import com.imuges.order.activity.IOrderDetailView
import com.imuges.order.base.BasePresenter
import com.imuges.order.data.GoodsOrderInfo
import com.imuges.order.data.OrderInfo
import java.lang.StringBuilder

/**
 * @author BQG
 */
class OrderDetailPresenter : BasePresenter<IOrderDetailView>() {

    private lateinit var mOrderInfo: OrderInfo

    override fun onViewCreate() {
        initFakeData()
        createOrderStruct()
    }

    /**
     * 创建文本显示结构
     */
    private fun createOrderStruct() {
        val textBuilder = StringBuilder()
        textBuilder.append("客户名称：")
            .append(mOrderInfo.name).appendln()
        textBuilder.append("交易金额：")
            .append(mOrderInfo.percent).append("元").appendln()
        textBuilder.append("订单创建时间：")
            .append(TimeUtils.millis2String(mOrderInfo.time)).appendln()
        textBuilder.append("订单最后修改时间：")
            .append(TimeUtils.millis2String(mOrderInfo.lastModifyTime)).appendln()
        for (i in mOrderInfo.goodsOrderInfos.indices) {
            textBuilder.append(i + 1).append(". ")
                .append(mOrderInfo.goodsOrderInfos[i].goodsName).append(":")
                .append(mOrderInfo.goodsOrderInfos[i].percent).append("/")
                .append(mOrderInfo.goodsOrderInfos[i].unit).append(" * ")
                .append(mOrderInfo.goodsOrderInfos[i].selectCount).append(".").appendln()
            textBuilder.append("    金额：")
                .append(mOrderInfo.goodsOrderInfos[i].percent * mOrderInfo.goodsOrderInfos[i].selectCount)
                .append("元.").appendln()
        }
        view?.setOrderContent(textBuilder)
    }

    private fun initFakeData() {
        val orderlist = mutableListOf<GoodsOrderInfo>()
        for (i in 0..100) {
            orderlist.add(
                GoodsOrderInfo(
                    i, "Goods-$i", "", 1.1f * i, "斤", 0, i % 10
                )
            )
        }
        mOrderInfo = OrderInfo(
            0,
            "A-",
            2080.0f,
            System.currentTimeMillis() - 24 * 60 * 60 * 1000,
            System.currentTimeMillis(),
            orderlist
        )
    }
}