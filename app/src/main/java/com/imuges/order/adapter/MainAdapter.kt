package com.imuges.order.adapter

import androidx.appcompat.widget.AppCompatImageView
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.imuges.order.R
import com.imuges.order.data.OrderSimpleInfo
import com.imuges.order.expan.loadBlurImage
import com.imuges.order.util.BackGroundTransform

/**
 * @author BGQ
 * 主界面列表
 */
class MainAdapter(data: MutableList<OrderSimpleInfo>) :
    BaseQuickAdapter<OrderSimpleInfo, BaseViewHolder>(R.layout.item_main_order_list, data) {

    override fun convert(holder: BaseViewHolder, item: OrderSimpleInfo) {
        val imageBack = holder.getView<AppCompatImageView>(R.id.imageBackGround)
        imageBack.loadBlurImage(BackGroundTransform.transform(item.background), 16, 2)
        holder.setText(R.id.name, item.name)
        holder.setText(R.id.percent, "交易额：￥${item.percent}")
        holder.setText(R.id.time, TimeUtils.millis2String(item.time))
        addChildClickViewIds(R.id.clickView)
    }
}