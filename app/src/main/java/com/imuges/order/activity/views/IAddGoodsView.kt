package com.imuges.order.activity.views

import com.imuges.order.base.IBaseView
import java.io.FileDescriptor

/**
 * Created by "BGQ" on 2020/11/21.
 */
interface IAddGoodsView : IBaseView {

    fun updateCurrentType(type: String)

    fun updateTypeList()

    fun updateTypeInsert(position: Int)

    fun updateTypeItem(position: Int)

    fun updateGoodsList()

    fun updateGoodsInsert(position: Int)

    fun selectFile(fileCall: (name: String, fd: FileDescriptor) -> Unit)

    fun showGoodsEditView(
        goodsCall: ((goodsName: String, percent: Float, unit: String, imagePath: String) -> Unit)
    )

    fun showTypeEditView(nameCall: ((typeName: String) -> Unit))

    fun showWaitProgress()

    fun hideWaitProgress()
}