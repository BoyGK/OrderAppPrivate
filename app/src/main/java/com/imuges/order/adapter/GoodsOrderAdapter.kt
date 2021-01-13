package com.imuges.order.adapter

import androidx.appcompat.widget.AppCompatImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.imuges.order.R
import com.nullpt.base.entity.GoodsOrderInfo
import com.nullpt.base.expan.loadImage

/**
 * @author BGQ
 * 订购界面 货物列表
 */
class GoodsOrderAdapter(data: MutableList<GoodsOrderInfo>) :
    BaseQuickAdapter<GoodsOrderInfo, BaseViewHolder>(R.layout.item_add_order_goods, data) {

    init {
        addChildClickViewIds(R.id.add, R.id.delete)
    }

    override fun convert(holder: BaseViewHolder, item: GoodsOrderInfo) {
        val image = holder.getView<AppCompatImageView>(R.id.picture)
        image.loadImage(item.picturePath)
        holder.setText(R.id.name, item.goodsName)
        holder.setText(R.id.percent, "单价￥：${item.percent} / ${item.unit}")
        holder.setText(R.id.count, "已选：${item.selectCount}")
    }

    override fun convert(holder: BaseViewHolder, item: GoodsOrderInfo, payloads: List<Any>) {
        holder.setText(R.id.count, "已选：${item.selectCount}")
    }

}