package com.imuges.order.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.imuges.order.R
import com.imuges.order.data.GoodsTypeInfo

/**
 * @author BGQ
 * 订购界面 类型列表
 */
class GoodsTypeAdapter(data: MutableList<GoodsTypeInfo>) :
    BaseQuickAdapter<GoodsTypeInfo, BaseViewHolder>(R.layout.item_add_order_tab, data) {

    override fun convert(holder: BaseViewHolder, item: GoodsTypeInfo) {
        holder.setText(R.id.tab, item.typeName)
        if (item.select) {
            holder.setBackgroundColor(R.id.tab, Color.parseColor("#FFEEEEEE"))
        } else {
            holder.setBackgroundColor(R.id.tab, Color.TRANSPARENT)
        }
    }

}