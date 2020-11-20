package com.imuges.order.activity

import com.imuges.order.base.IBaseView

/**
 * @author BGQ
 */
interface IOrderDetailView : IBaseView {

    fun setOrderContent(text: CharSequence)

}