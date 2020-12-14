package com.imuges.order.presenter

import com.imuges.order.activity.views.IAddOrderView
import com.imuges.order.util.BackGroundTransform
import com.nullpt.base.entity.GoodsOrderInfo
import com.nullpt.base.framework.BasePresenter

/**
 * @author BGQ
 * 添加货物
 */
class AddOrderDefaultPresenter : BasePresenter<IAddOrderView>() {

    private val mGoodsData by lazy { mutableListOf<GoodsOrderInfo>() }
    private var mTotalPercent = 0f

    override fun onViewCreate() {
        val bg = BackGroundTransform.randomBackground()
        view?.setBackGround(bg)
        view?.setCustomerName("ABCD")
        view?.setOrderText("伟人之所以伟大，是因为他与别人共处逆境时，别人失去了信心，他却下决心实现自己的目标。")
        view?.setGoodsToday(System.currentTimeMillis())
        view?.setTotalPercent(mTotalPercent)

        initFakeData()
    }

    private fun initFakeData() {
        for (i in 0..100) {
            mGoodsData.add(
                GoodsOrderInfo(
                    i, "Goods-$i", "", i * 1.1f, "个", i / 10, 0
                )
            )
        }
        view?.updateTypeList()
    }

    fun getGoodsData() = mGoodsData

    /**
     * 定位
     */
    fun position(typeId: Int) {
        val position = mGoodsData.indexOfFirst { it.goodsTypeId == typeId }
        view?.positionGoods(position)
    }

    /**
     * 添加指定货物
     */
    fun addGoods(position: Int) {
        mGoodsData[position].selectCount++
        view?.updateGoodsItem(position, mGoodsData[position])
        mTotalPercent += mGoodsData[position].percent
        view?.setTotalPercent(mTotalPercent)
    }

    /**
     * 删除指定货物
     */
    fun deleteGoods(position: Int) {
        if (mGoodsData[position].selectCount == 0) {
            return
        }
        mGoodsData[position].selectCount--
        view?.updateGoodsItem(position, mGoodsData[position])
        mTotalPercent -= mGoodsData[position].percent
        view?.setTotalPercent(mTotalPercent)
    }

    /**
     * 出单，创建订单信息
     */
    fun createOrder() {

    }
}