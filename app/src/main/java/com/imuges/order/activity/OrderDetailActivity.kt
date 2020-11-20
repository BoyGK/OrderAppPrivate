package com.imuges.order.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import com.imuges.order.R
import com.imuges.order.base.BaseFullTitleActivity
import com.imuges.order.base.BasePresenter
import com.imuges.order.base.ContentView
import com.imuges.order.base.IBaseView
import com.imuges.order.presenter.OrderDetailPresenter
import kotlinx.android.synthetic.main.activity_order_detail.*

/**
 * @author BGQ
 * 订单详情界面
 */
@SuppressLint("NonConstantResourceId")
@ContentView(R.layout.activity_order_detail)
class OrderDetailActivity : BaseFullTitleActivity(), View.OnClickListener, IOrderDetailView {

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, OrderDetailActivity::class.java))
        }
    }

    override fun getPresenterMap(): Map<String, BasePresenter<out IBaseView>> {
        return mapOf(Pair(DEFAULT, OrderDetailPresenter()))
    }

    override fun onCreate() {
        setStateBarColor(Color.WHITE)
        setStateBarLightModel(true)
        initListener()
    }

    override fun setOrderContent(text: CharSequence) {
        orderContent.text = text
    }

    private fun initListener() {
        modify.setOnClickListener(this)
        backCard.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            modify -> {

            }
            backCard -> {
                onBackPressed()
            }
            else -> {
            }
        }
    }

}