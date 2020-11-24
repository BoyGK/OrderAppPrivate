package com.imuges.order.activity.views

import com.imuges.order.base.IBaseView

/**
 * Created by "BGQ" on 2020/11/21.
 */
interface IAddGoodsView : IBaseView {

    fun updateTypeList()

    fun updateTypeItem(position: Int)

    fun updateGoodsList()

    fun showGoodsEditView(goodsCall: ((goodsName: String, percent: Float, unit: String) -> Unit))

    fun showTypeEditView(nameCall: ((typeName: String) -> Unit))

}