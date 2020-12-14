package com.imuges.order.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import com.blankj.utilcode.util.ConvertUtils
import com.imuges.order.R
import com.imuges.order.activity.views.IOrderDetailView
import com.imuges.order.base.BaseFullTitleActivity
import com.imuges.order.presenter.OrderDetailPresenter
import com.nullpt.base.framework.BasePresenter
import com.nullpt.base.framework.ContentView
import com.nullpt.base.framework.IBaseView
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
        initTitleBar()
        initListener()
    }

    override fun initTitleBar() {
        setStateBarColor(getColor(R.color.mainTheme))
        titleBarCard.mBolHeight = ConvertUtils.dp2px(20f).toFloat()
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