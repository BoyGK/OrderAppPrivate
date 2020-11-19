package com.imuges.order.adapter

import androidx.appcompat.widget.AppCompatImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.imuges.order.R
import com.imuges.order.data.GoodsOrderInfo
import com.imuges.order.expan.loadImage

/**
 * @author BGQ
 * 订购界面 货物列表
 */
class GoodsOrderAdapter(data: MutableList<GoodsOrderInfo>) :
    BaseQuickAdapter<GoodsOrderInfo, BaseViewHolder>(R.layout.item_add_order_goods, data) {

    override fun convert(holder: BaseViewHolder, item: GoodsOrderInfo) {
        val image = holder.getView<AppCompatImageView>(R.id.picture)
        image.loadImage(item.picturePath)
        holder.setText(R.id.name, item.goodsName)
        holder.setText(R.id.percent, "单价￥：${item.percent} / ${item.unit}")
        holder.setText(R.id.count, "已选：${item.selectCount}")
        addChildClickViewIds(R.id.add, R.id.delete)
    }

}