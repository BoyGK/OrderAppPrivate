package com.imuges.order.model

import com.imuges.order.data.GoodsSimpleInfo
import com.imuges.order.data.GoodsTypeInfo
import com.imuges.order.expan.db
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author BGQ
 * 货物仓库数据管理
 */
class AddGoodsModel {

    private val mTypeDao by lazy { db().typeDao() }
    private val mGoodsDao by lazy { db().goodsDao() }

    /**
     * 加载货物类型
     */
    fun loadTypes(call: ((data: MutableList<GoodsTypeInfo>) -> Unit)) {
        GlobalScope.launch {
            val list = mTypeDao.queryAll()
            val result = list.flatMap {
                mutableListOf(GoodsTypeInfo(it.typeId!!, it.typeName))
            }
            if (result.isNotEmpty()) {
                result[0].select = true
                call.invoke(result.toMutableList())
            }
        }
    }

    /**
     * 加载货物
     */
    fun loadGoods(call: ((data: MutableList<GoodsSimpleInfo>) -> Unit)) {
        GlobalScope.launch {
            val list = mGoodsDao.queryAll()
            val result = list.flatMap {
                mutableListOf(
                    GoodsSimpleInfo(
                        it.goodsId!!,
                        it.goodsTypeId,
                        it.goodsName,
                        it.percent,
                        it.unit,
                        it.picturePath
                    )
                )
            }
            call.invoke(result.toMutableList())
        }
    }


}