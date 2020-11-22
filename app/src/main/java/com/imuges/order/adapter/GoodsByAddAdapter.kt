package com.imuges.order.adapter

import androidx.appcompat.widget.AppCompatImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.imuges.order.R
import com.imuges.order.data.GoodsSimpleInfo
import com.imuges.order.expan.loadImage

/**
 * @author BGQ
 * 添加货物界面 货物列表
 */
class GoodsByAddAdapter(data: MutableList<GoodsSimpleInfo>) :
    BaseMultiItemQuickAdapter<GoodsSimpleInfo, BaseViewHolder>(data) {

    init {
        addItemType(GoodsSimpleInfo.ADD, R.layout.item_add_goods_add)
        addItemType(GoodsSimpleInfo.NORMAL, R.layout.item_add_goods_goods)
    }

    override fun convert(holder: BaseViewHolder, item: GoodsSimpleInfo) {
        when (holder.itemViewType) {
            GoodsSimpleInfo.ADD -> {
                //do nothing
            }
            GoodsSimpleInfo.NORMAL -> {
                val image = holder.getView<AppCompatImageView>(R.id.picture)
                image.loadImage(item.picturePath)
                holder.setText(R.id.name, item.goodsName)
                holder.setText(R.id.percent, "单价￥：${item.percent} / ${item.unit}")
            }
        }
    }

}