package com.imuges.order.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
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
import com.imuges.order.base.BasePresenter
import com.imuges.order.base.ContentView
import com.imuges.order.base.IBaseView
import com.imuges.order.data.GoodsOrderInfo
import com.imuges.order.data.GoodsTypeInfo
import com.imuges.order.expan.loadBlurImage
import com.imuges.order.presenter.AddOrderDefaultPresenter
import com.imuges.order.presenter.AddOrderTypePresenter
import com.imuges.order.util.BackGroundTransform
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.model.SlidrInterface
import com.r0adkll.slidr.model.SlidrPosition
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

    private lateinit var mSlideLock: SlidrInterface

    private val mGoodsTypeAdapter by lazy {
        GoodsTypeAdapter(presenter<AddOrderTypePresenter>(PRESENTER_ORDER_TYPE).getGoodsTypeData())
    }
    private val mGoodsOrderAdapter by lazy {
        GoodsOrderAdapter(defaultPresenter<AddOrderDefaultPresenter>().getGoodsData())
    }

    companion object {
        private const val PRESENTER_ORDER_TYPE = "presenter_order_type"

        fun startActivity(context: Context) {
            context.startActivity(Intent(context, AddOrderActivity::class.java))
        }
    }

    override fun getPresenterMap(): Map<String, BasePresenter<out IBaseView>> {
        return mapOf(
            Pair(DEFAULT, AddOrderDefaultPresenter()),
            Pair(PRESENTER_ORDER_TYPE, AddOrderTypePresenter())
        )
    }

    override fun onCreate() {
        bindSlide()
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
            orderText.alpha =
                1f - (offs / (ConvertUtils.dp2px(100f)).toFloat())
            /*
            if (offs == 0) {
                mSlideLock.unlock()
            } else {
                mSlideLock.lock()
            }*/
        })
    }

    private fun bindSlide() {
        val config = SlidrConfig.Builder()
            .position(SlidrPosition.TOP)
            .build()
        mSlideLock = Slidr.attach(this, config)
        //mSlideLock.unlock()
        //暂时不允许下滑退出
        mSlideLock.lock()
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
                defaultPresenter<AddOrderDefaultPresenter>().createOrder()
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

}