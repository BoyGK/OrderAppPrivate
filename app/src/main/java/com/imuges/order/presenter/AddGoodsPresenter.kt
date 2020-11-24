package com.imuges.order.presenter

import com.imuges.order.activity.views.IAddGoodsView
import com.imuges.order.base.BasePresenter
import com.imuges.order.data.GoodsSimpleInfo
import com.imuges.order.data.GoodsTypeInfo

/**
 * Created by "BGQ" on 2020/11/21.
 */
class AddGoodsPresenter : BasePresenter<IAddGoodsView>() {

    private val mGoodsTypeData by lazy { mutableListOf<GoodsTypeInfo>() }
    private val mGoodsData by lazy { mutableListOf<GoodsSimpleInfo>() }

    override fun onViewCreate() {
        initFakeData()
    }

    private fun initFakeData() {
        for (i in 0..10) {
            mGoodsTypeData.add(GoodsTypeInfo(i, "Type-${i}", i == 3))
        }
        mGoodsTypeData.add(GoodsTypeInfo(0, "添加", false))

        for (i in 0..10) {
            if (i == 0) {
                mGoodsData.add(
                    GoodsSimpleInfo(
                        0,
                        0,
                        "",
                        0f,
                        "",
                        "",
                        GoodsSimpleInfo.ADD
                    )
                )
            } else {
                mGoodsData.add(
                    GoodsSimpleInfo(
                        i,
                        i,
                        "Goods-${i}",
                        1.1f * i,
                        "只",
                        "",
                        GoodsSimpleInfo.NORMAL
                    )
                )
            }
        }
        view?.updateTypeList()
        view?.updateGoodsList()
    }

    fun getGoodsType() = mGoodsTypeData

    fun getGoods() = mGoodsData

    fun selectType(position: Int) {
        //最后一个是点击添加
        if (position == mGoodsTypeData.size - 1) {
            addGoodsType()
            return
        }
        //选中
        for (i in mGoodsTypeData.indices) {
            if (mGoodsTypeData[i].select) {
                mGoodsTypeData[i].select = false
                view?.updateTypeItem(i)
                continue
            }
            mGoodsTypeData[i].select = false
        }
        mGoodsTypeData[position].select = true
        view?.updateTypeItem(position)
    }

    fun addGoods(position: Int) {
        if (position != 0) {
            return
        }
        addGoods()
    }

    /**
     * 添加货物类型
     */
    private fun addGoodsType() {
        view?.showTypeEditView {

        }
    }

    /**
     * 添加货物
     */
    private fun addGoods() {
        view?.showGoodsEditView { goodsName, percent, unit ->

        }
    }

}