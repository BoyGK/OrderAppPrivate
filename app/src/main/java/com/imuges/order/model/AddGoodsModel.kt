package com.imuges.order.model

import com.imuges.order.data.GoodsSimpleInfo
import com.imuges.order.data.GoodsTypeInfo
import com.imuges.order.data.entity.Goods
import com.imuges.order.data.entity.Type
import com.imuges.order.expan.db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            withContext(Dispatchers.Main) {
                call.invoke(result.toMutableList())
            }

        }
    }

    /**
     * 添加货物类型
     */
    fun addTypes(typeName: String, call: (goodsType: GoodsTypeInfo) -> Unit) {
        GlobalScope.launch {
            val dbType = Type(null, typeName)
            mTypeDao.insert(dbType)
            val type = mTypeDao.queryByName(typeName)
            withContext(Dispatchers.Main) {
                call.invoke(GoodsTypeInfo(type.typeId!!, type.typeName))
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
            withContext(Dispatchers.Main) {
                call.invoke(result.toMutableList())
            }
        }
    }

    /**
     * 添加货物
     */
    fun addGoods(goods: GoodsSimpleInfo, call: (goodsType: GoodsSimpleInfo) -> Unit) {
        GlobalScope.launch {
            var dbGoods = Goods(
                null,
                goodsName = goods.goodsName,
                percent = goods.percent,
                unit = goods.unit,
                picturePath = goods.picturePath,
                goodsTypeId = goods.typeId
            )
            mGoodsDao.insert(dbGoods)
            dbGoods = mGoodsDao.queryByName(goods.goodsName, goods.typeId)
            withContext(Dispatchers.Main) {
                call.invoke(goods.copy(goodId = dbGoods.goodsId!!))
            }
        }
    }

}