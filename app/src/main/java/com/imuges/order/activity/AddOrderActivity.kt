package com.imuges.order.activity

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.imuges.order.R
import com.imuges.order.activity.views.IAddOrderView
import com.imuges.order.adapter.GoodsOrderAdapter
import com.imuges.order.adapter.GoodsTypeAdapter
import com.imuges.order.base.BaseFullTitleActivity
import com.imuges.order.data.GoodsTypeInfo
import com.imuges.order.presenter.AddOrderDefaultPresenter
import com.imuges.order.presenter.AddOrderTypePresenter
import com.imuges.order.util.BackGroundTransform
import com.nullpt.base.entity.GoodsOrderInfo
import com.nullpt.base.expan.*
import com.nullpt.base.framework.BasePresenter
import com.nullpt.base.framework.ContentView
import com.nullpt.base.framework.IBaseView
import kotlinx.android.synthetic.main.activity_add_order.*
import kotlin.math.abs

/**
 * @author BGQ
 * 新增订单界面
 */
@SuppressLint("NonConstantResourceId")
@ContentView(R.layout.activity_add_order)
class AddOrderActivity : BaseFullTitleActivity(), IAddOrderView, View.OnClickListener,
    OnItemClickListener, OnItemChildClickListener {

    private val mGoodsTypeAdapter by lazy {
        GoodsTypeAdapter(presenter<AddOrderTypePresenter>(PRESENTER_ORDER_TYPE).getGoodsTypeData())
    }
    private val mGoodsOrderAdapter by lazy {
        GoodsOrderAdapter(defaultPresenter<AddOrderDefaultPresenter>().getGoodsData())
    }

    private val orderId by lazy { intent.getIntExtra(KEY_ORDER_ID, -1) }

    companion object {
        private const val PRESENTER_ORDER_TYPE = "presenter_order_type"
        private const val KEY_ORDER_ID = "key_order_id"

        fun startActivity(context: Context, view: View) {
            context.startActivity(
                Intent(context, AddOrderActivity::class.java),
                ActivityOptions.makeScaleUpAnimation(
                    view, view.left, view.top, view.width, view.height
                ).toBundle()
            )
        }

        fun startActivity(context: Context, view: View, orderId: Int) {
            context.startActivity(
                Intent(context, AddOrderActivity::class.java).apply {
                    putExtra(KEY_ORDER_ID, orderId)
                },
                ActivityOptions.makeScaleUpAnimation(
                    view, view.left, view.top, view.width, view.height
                ).toBundle()
            )
        }
    }

    override fun getPresenterMap(): Map<String, BasePresenter<out IBaseView>> {
        return mapOf(
            Pair(DEFAULT, AddOrderDefaultPresenter()),
            Pair(PRESENTER_ORDER_TYPE, AddOrderTypePresenter())
        )
    }

    override fun onCreate() {
        initTitleBar()
        initView()
        initListener()
    }

    override fun initTitleBar() {
        setStateBarColor(Color.TRANSPARENT)
        scrollBar.minimumHeight += BarUtils.getStatusBarHeight()
        //背景
        val sbbgLp = scrollBarBackGround.layoutParams as CollapsingToolbarLayout.LayoutParams
        sbbgLp.height += BarUtils.getStatusBarHeight()
        scrollBarBackGround.layoutParams = sbbgLp
        //titlebar
        val titleBarLp = titleBar.layoutParams as CollapsingToolbarLayout.LayoutParams
        titleBarLp.topMargin = BarUtils.getStatusBarHeight()
        titleBar.layoutParams = titleBarLp
        //appbar lock slide
        val tabRecyclerViewLp = tabRecyclerView.layoutParams as ConstraintLayout.LayoutParams
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val offs = abs(verticalOffset)
            tabRecyclerViewLp.topMargin =
                ConvertUtils.dp2px(254f) + BarUtils.getStatusBarHeight() - offs
            tabRecyclerView.layoutParams = tabRecyclerViewLp
            orderText.alpha = 1f - (offs / (ConvertUtils.dp2px(100f)).toFloat())
        })
    }

    private fun initView() {
        tabRecyclerView.adapter = mGoodsTypeAdapter
        recyclerView.adapter = mGoodsOrderAdapter
    }

    private fun initListener() {
        customerBack.setOnClickListener(this)
        mGoodsTypeAdapter.setOnItemClickListener(this)
        mGoodsOrderAdapter.setOnItemChildClickListener(this)
        createOrder.setOnClickListener(this)
        customerName.setOnClickListener(this)
        //监听货物列表滑动，主动定位type列表
        var lastGoodsPosition = 0
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val viewPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (viewPosition == lastGoodsPosition) {
                    return
                }
                val typeId =
                    ((recyclerView.adapter as BaseQuickAdapter<*, *>).data[viewPosition] as GoodsOrderInfo).goodsTypeId
                //type选中
                presenter<AddOrderTypePresenter>(PRESENTER_ORDER_TYPE).position(typeId)

                lastGoodsPosition = viewPosition
            }
        })
    }

    override fun onClick(v: View) {
        when (v) {
            customerBack -> {
                onBackPressed()
            }
            createOrder -> {
                defaultPresenter<AddOrderDefaultPresenter>().submitOrder()
            }
            customerName -> {
                editCustomerName()
            }
        }
    }

    /**
     * 仅type列表点击调用
     */
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val typeId = (adapter.data[position] as GoodsTypeInfo).goodsTypeId
        //货物定位
        defaultPresenter<AddOrderDefaultPresenter>().position(typeId)
        //type选中
        presenter<AddOrderTypePresenter>(PRESENTER_ORDER_TYPE).select(position)
    }

    /**
     * 编辑商家名称
     */
    private fun editCustomerName() {
        val layout = layout(R.layout.dialog_add_goods_type)
        val edit = layout.findViewById<AppCompatEditText>(R.id.goods_type_edit)
        val submit = layout.findViewById<AppCompatImageView>(R.id.goods_type_submit)
        val dialog = showBottomDialog(layout, 0.5f)
        edit.hint = "输入商家名称"
        submit.setOnClickListener {
            if (edit.text.toString().isEmpty()) {
                return@setOnClickListener
            }
            defaultPresenter<AddOrderDefaultPresenter>().setCustomerName(edit.text.toString())
            dialog.dismiss()
        }
    }

    /**
     * 仅goods列表点击调用
     */
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when (view.id) {
            R.id.add -> {
                defaultPresenter<AddOrderDefaultPresenter>().addGoods(position)
            }
            R.id.delete -> {
                defaultPresenter<AddOrderDefaultPresenter>().deleteGoods(position)
            }
        }
    }

    override fun getInitOrderId(): Int = orderId

    override fun setBackGround(background: Int) {
        imageBackGround.loadBlurImage(BackGroundTransform.transform(background), 16, 2)
    }

    override fun setCustomerName(name: String) {
        customerName.text = name
    }

    override fun setOrderText(text: String) {
        orderText.text = text
    }

    override fun setGoodsToday(time: Long) {
        todayTime.text = TimeUtils.millis2String(time)
    }

    override fun updateTypeList() {
        mGoodsTypeAdapter.notifyDataSetChanged()
    }

    override fun updateTypeItem(position: Int, data: GoodsTypeInfo) {
        mGoodsTypeAdapter.setData(position, data)
    }

    override fun updateGoodsList() {
        mGoodsOrderAdapter.notifyDataSetChanged()
    }

    override fun updateGoodsItem(position: Int, data: GoodsOrderInfo) {
        mGoodsOrderAdapter.notifyItemChanged(position, data)
    }

    override fun positionType(position: Int) {
        tabRecyclerView.smoothScrollToPosition(position)
    }

    override fun positionGoods(position: Int) {
        //recyclerView.scrollToPosition(position)
        //定位的item滑动到顶部
        (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, 0)
    }

    @SuppressLint("SetTextI18n")
    override fun setTotalPercent(percent: Float) {
        totalPercent.text = "总金额(￥)：${percent}元"
    }

    override fun showLoading() {
        val dialog = showCenterDialog(ProgressBar(this))
        dialog.setCancelable(false)
    }

    override fun hiddenLoading() {
        latestDialog?.dismiss()
        latestDialog = null
    }

    override fun createOrderSuccess() {
        toast("创建订单成功")
        onBackPressed()
    }

    override fun createOrderFailByNoMerchantName() {
        toast("未编辑商家名称（点击“创建订单”编辑）")
    }

    override fun createOrderFailByNoSelect() {
        toast("未选择货物")
    }

}