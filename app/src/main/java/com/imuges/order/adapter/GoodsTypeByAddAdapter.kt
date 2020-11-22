package com.imuges.order.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.imuges.order.R
import com.imuges.order.data.GoodsTypeInfo

/**
 * @author BGQ
 * 添加货物界面 类型列表
 */
class GoodsTypeByAddAdapter(data: MutableList<GoodsTypeInfo>) :
    BaseQuickAdapter<GoodsTypeInfo, BaseViewHolder>(R.layout.item_add_goods_type, data) {

    override fun convert(holder: BaseViewHolder, item: GoodsTypeInfo) {
        holder.setText(R.id.typeName, item.typeName)
        if (item.select) {
            holder.setBackgroundColor(R.id.typeName, Color.parseColor("#FFEEEEEE"))
        } else {
            holder.setBackgroundResource(R.id.typeName, R.drawable.shape_click_background)
        }
    }

}