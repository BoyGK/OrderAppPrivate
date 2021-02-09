package com.imuges.order.activity

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.addListener
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.imuges.order.R
import com.imuges.order.activity.views.IMainView
import com.imuges.order.adapter.MainAdapter
import com.imuges.order.base.BaseFullTitleActivity
import com.imuges.order.presenter.MainPresenter
import com.nullpt.base.app.MainApplication
import com.nullpt.base.framework.BasePresenter
import com.nullpt.base.framework.ContentView
import com.nullpt.base.framework.IBaseView
import com.nullpt.statisticscompose.activity.ComposeStatisticsMainActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream

/**
 * @author BGQ
 * 主界面 订单列表界面
 */
@SuppressLint("NonConstantResourceId")
@ContentView(R.layout.activity_main)
class MainActivity : BaseFullTitleActivity(), IMainView, View.OnClickListener,
    OnItemChildClickListener, SwipeRefreshLayout.OnRefreshListener {

    private val mMainAdapter by lazy { MainAdapter(defaultPresenter<MainPresenter>().getViewData()) }
    private val mRefreshLayout by lazy { root }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun getPresenterMap(): Map<String, BasePresenter<out IBaseView>> {
        return mapOf(Pair(DEFAULT, MainPresenter()))
    }

    override fun onCreate() {
        initTitleBar()
        initView()
        initListener()
    }

    override fun initTitleBar() {
        val color = getColor(R.color.mainTheme)
        setStateBarColor(color)
        titleBarCard.mBolHeight = ConvertUtils.dp2px(20f).toFloat()
    }

    private fun initView() {
        recyclerView.adapter = mMainAdapter
        mMainAdapter.setEmptyView(R.layout.view_main_empty)
        mRefreshLayout.setColorSchemeColors(getColor(R.color.mainTheme))
    }

    private fun initListener() {
        addGoods.setOnClickListener(this)
        addOrder.setOnClickListener(this)
        searchOrder.setOnClickListener(this)
        searchCancel.setOnClickListener(this)
        floatingTools.setOnClickListener(this)
        mRefreshLayout.setOnRefreshListener(this)
        mMainAdapter.setOnItemChildClickListener(this)
        searchEdit.addTextChangedListener(afterTextChanged = {
            defaultPresenter<MainPresenter>().searchOrder(it.toString())
        })
    }

    override fun onClick(v: View) {
        when (v) {
            addGoods -> {
                AddGoodsActivity.startActivity(this, addGoods)
            }
            addOrder -> {
                AddOrderActivity.startActivity(this, addOrder)
            }
            searchOrder -> {
                startSearchAnim()
            }
            searchCancel -> {
                stopSearchAnim()
                defaultPresenter<MainPresenter>().searchOrder(":cancel")
            }
            floatingTools -> {
                ComposeStatisticsMainActivity.startActivity(this)
            }
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        //热修复代码测试
        if (position == 0) {
            val app = MainApplication.instance
            val fileIs = assets.open("classes.dex")
            val outDir = File("${cacheDir.absolutePath}/hotfit")
            if (!outDir.exists()) {
                outDir.mkdir()
            }
            val outFile = File(outDir, "hotfit.dex")
            val outOs = FileOutputStream(outFile)
            var len: Int
            val bytes = ByteArray(1024)
            len = fileIs.read(bytes)
            while (len != -1) {
                outOs.write(bytes, 0, len)
                len = fileIs.read(bytes)
            }
            fileIs.close()
            outOs.close()
            toast("添加热修复插入，下次启动采用热修复代码")
            return
        }
        when (view.id) {
            R.id.clickView -> {
                OrderDetailActivity.startActivity(
                    this,
                    defaultPresenter<MainPresenter>().getOrderId(position)
                )
            }
            R.id.delete -> {
                // TODO: 2020/12/21 添加弹窗确认
                defaultPresenter<MainPresenter>().deleteItem(position)
            }
        }
    }

    override fun updateList() {
        mMainAdapter.notifyDataSetChanged()
    }

    override fun hiddenRefreshing() {
        mRefreshLayout.isRefreshing = false
    }

    /**
     * 打开搜索动画
     */
    private fun startSearchAnim() {
        val endLeftMargin = ConvertUtils.dp2px(16f)
        val startLeftMargin = resources.displayMetrics.widthPixels - endLeftMargin
        val sclp = searchCard.layoutParams as ConstraintLayout.LayoutParams
        sclp.leftMargin = startLeftMargin
        searchCard.layoutParams = sclp

        searchCard.isVisible = true

        val animator = ValueAnimator.ofInt(startLeftMargin, endLeftMargin)
        animator.addUpdateListener {
            val vl = it.animatedValue as Int
            sclp.leftMargin = vl
            searchCard.layoutParams = sclp
        }
        animator.duration = 300
        animator.start()
    }

    /**
     * 关闭搜索动画
     */
    private fun stopSearchAnim() {
        val endLeftMargin = ConvertUtils.dp2px(16f)
        val startLeftMargin = resources.displayMetrics.widthPixels - endLeftMargin
        val sclp = searchCard.layoutParams as ConstraintLayout.LayoutParams

        val animator = ValueAnimator.ofInt(endLeftMargin, startLeftMargin)
        animator.addUpdateListener {
            val vl = it.animatedValue as Int
            sclp.leftMargin = vl
            searchCard.layoutParams = sclp
        }
        animator.addListener(onEnd = {
            searchEdit.setText("")
            searchCard.isVisible = false
            if (KeyboardUtils.isSoftInputVisible(this)) {
                KeyboardUtils.hideSoftInput(this)
            }
        })
        animator.duration = 300
        animator.start()
    }

    override fun onRefresh() {
        defaultPresenter<MainPresenter>().refresh()
    }

}