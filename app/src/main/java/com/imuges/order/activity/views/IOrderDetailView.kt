package com.imuges.order.activity.views

import com.nullpt.base.framework.IBaseView

/**
 * @author BGQ
 */
interface IOrderDetailView : IBaseView {

    fun getInitOrderId(): Int

    fun setOrderContent(text: CharSequence)

}