package com.imuges.order.presenter

import android.os.SystemClock
import com.imuges.order.activity.views.IAddOrderView
import com.imuges.order.model.AddOrderModel
import com.imuges.order.util.BackGroundTransform
import com.imuges.order.util.MottoTransform
import com.nullpt.base.entity.GoodsOrderInfo
import com.nullpt.base.framework.BasePresenter

/**
 * @author BGQ
 * 添加货物
 */
class AddOrderDefaultPresenter : BasePresenter<IAddOrderView>() {

    private val mGoodsData by lazy { mutableListOf<GoodsOrderInfo>() }

    private var mCustomerName = ""
    private var mTotalPercent = 0f

    private val mAddOrderModel by lazy { AddOrderModel() }

    override fun onViewCreate() {
        view?.setBackGround(BackGroundTransform.randomBackground())
        view?.setOrderText(MottoTransform.randomMotto())

        view?.setGoodsToday(System.currentTimeMillis())
        view?.setTotalPercent(mTotalPercent)

        initData()
    }

    private fun initData() {
        mAddOrderModel.loadGoods {
            val goodsOrderInfos = it.flatMap { goods ->
                mutableListOf(
                    GoodsOrderInfo(
                        goods.goodsName,
                        goods.picturePath,
                        goods.percent,
                        goods.unit,
                        goods.typeId
                    )
                )
            }
            mGoodsData.addAll(goodsOrderInfos)
            view?.updateGoodsList()
        }
    }

    fun getGoodsData() = mGoodsData

    /**
     * 设置商家名称
     */
    fun setCustomerName(name: String) {
        mCustomerName = name
        view?.setCustomerName(name)
    }

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
        if (mCustomerName.isEmpty()) {
            view?.createOrderFailByNoMerchantName()
            return
        }
        if (mTotalPercent == 0f || mGoodsData.isEmpty()) {
            view?.createOrderFailByNoSelect()
            return
        }
        view?.showLoading()
        mAddOrderModel.createOrders(mCustomerName, mTotalPercent, mGoodsData) {
            SystemClock.sleep(500L)
            view?.hiddenLoading()
            view?.createOrderSuccess()
        }
    }
}