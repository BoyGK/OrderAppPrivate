package com.imuges.order.presenter

import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.TimeUtils
import com.imuges.order.activity.views.IOrderDetailView
import com.imuges.order.data.OrderInfo
import com.imuges.order.model.OrderDetailsModel
import com.nullpt.base.framework.BasePresenter

/**
 * @author BQG
 */
class OrderDetailPresenter : BasePresenter<IOrderDetailView>() {

    private val mOrderDetailsModel by lazy { OrderDetailsModel() }
    private lateinit var mOrderInfo: OrderInfo

    private val nextLine = "\n"

    override fun onViewCreate() {
        initData()
    }

    override fun onViewReStart() {
        initData()
    }

    private fun initData() {
        view ?: return
        mOrderDetailsModel.loadOrderDetails(view!!.getInitOrderId()) {
            mOrderInfo = it
            createOrderStruct()
        }
    }

    /**
     * 创建文本显示结构
     */
    private fun createOrderStruct() {
        val textBuilder = StringBuilder()
        //标题，商户和时间信息
        textBuilder.append("客户名称 : ")
            .append(mOrderInfo.name).append(nextLine)
        textBuilder.append("订单创建时间 : ").append(nextLine)
            .append("    ").append(TimeUtils.millis2String(mOrderInfo.time)).append(nextLine)
        textBuilder.append("订单最后修改时间 : ").append(nextLine)
            .append("    ").append(TimeUtils.millis2String(mOrderInfo.lastModifyTime))
            .append(nextLine)
        textBuilder.append(nextLine)

        val sizeSpanStart = textBuilder.length

        //正文，具体货物交易详情信息
        for (i in mOrderInfo.goodsOrderInfos.indices) {
            textBuilder.append("${(i + 1)}").append(". ")
                .append(mOrderInfo.goodsOrderInfos[i].goodsName).append(" : ")
                .append(mOrderInfo.goodsOrderInfos[i].percent).append("/")
                .append(mOrderInfo.goodsOrderInfos[i].unit).append(" × ")
                .append(mOrderInfo.goodsOrderInfos[i].selectCount).append(" .").append(nextLine)
            textBuilder.append("    金额 : ")
                .append(mOrderInfo.goodsOrderInfos[i].percent * mOrderInfo.goodsOrderInfos[i].selectCount)
                .append("元 .").append(nextLine)
        }
        textBuilder.append(nextLine)

        val sizeSpanEnd = textBuilder.length

        //结尾，总金额信息
        textBuilder.append("总交易金额(￥) : ")
            .append(mOrderInfo.percent).append("元").append(nextLine)

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
}