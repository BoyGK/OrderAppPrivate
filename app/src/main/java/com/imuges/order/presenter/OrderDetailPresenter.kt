package com.imuges.order.presenter

import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.TimeUtils
import com.imuges.order.activity.views.IOrderDetailView
import com.imuges.order.data.OrderInfo
import com.nullpt.base.entity.GoodsOrderInfo
import com.nullpt.base.framework.BasePresenter

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
        //标题，商户和时间信息
        textBuilder.append("客户名称 : ")
            .append(mOrderInfo.name).appendln()
        textBuilder.append("订单创建时间 : ").appendln()
            .append("    ").append(TimeUtils.millis2String(mOrderInfo.time)).appendln()
        textBuilder.append("订单最后修改时间 : ").appendln()
            .append("    ").append(TimeUtils.millis2String(mOrderInfo.lastModifyTime)).appendln()
        textBuilder.appendln()

        val sizeSpanStart = textBuilder.length

        //正文，具体货物交易详情信息
        for (i in mOrderInfo.goodsOrderInfos.indices) {
            textBuilder.append("${(i + 1)}").append(". ")
                .append(mOrderInfo.goodsOrderInfos[i].goodsName).append(" : ")
                .append(mOrderInfo.goodsOrderInfos[i].percent).append("/")
                .append(mOrderInfo.goodsOrderInfos[i].unit).append(" × ")
                .append(mOrderInfo.goodsOrderInfos[i].selectCount).append(" .").appendln()
            textBuilder.append("    金额 : ")
                .append(mOrderInfo.goodsOrderInfos[i].percent * mOrderInfo.goodsOrderInfos[i].selectCount)
                .append("元 .").appendln()
        }
        textBuilder.appendln()

        val sizeSpanEnd = textBuilder.length

        //结尾，总金额信息
        textBuilder.append("总交易金额(￥) : ")
            .append(mOrderInfo.percent).append("元").appendln()

        //调整正文文字大小
        val spannableString = SpannableString(textBuilder)
        spannableString.setSpan(
            AbsoluteSizeSpan(ConvertUtils.sp2px(16f)),
            sizeSpanStart,
            sizeSpanEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        view?.setOrderContent(spannableString)
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