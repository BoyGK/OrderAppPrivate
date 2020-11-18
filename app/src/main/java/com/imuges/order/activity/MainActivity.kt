package com.imuges.order.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.imuges.order.R
import com.imuges.order.adapter.MainAdapter
import com.imuges.order.base.BaseFullTitleActivity
import com.imuges.order.base.BasePresenter
import com.imuges.order.base.ContentView
import com.imuges.order.base.IBaseView
import com.imuges.order.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author BGQ
 * 订单界面
 */
@SuppressLint("NonConstantResourceId")
@ContentView(R.layout.activity_main)
class MainActivity : BaseFullTitleActivity(), IMainView, View.OnClickListener,
    OnItemChildClickListener {

    private val mMainAdapter by lazy { MainAdapter(defaultPresenter<MainPresenter>().getViewData()) }

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
        val color = Color.parseColor("#FF8282CF")
        setStateBarColor(color)
        titleBarCard.mBolHeight = ConvertUtils.dp2px(20f).toFloat()
    }

    private fun initView() {
        recyclerView.adapter = mMainAdapter
    }

    private fun initListener() {
        addOrder.setOnClickListener(this)
        searchOrder.setOnClickListener(this)
        mMainAdapter.setOnItemChildClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            addOrder -> {

            }
            searchOrder -> {

            }
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        Toast.makeText(this, "child $position", Toast.LENGTH_SHORT).show()
    }

    override fun updateList() {
        mMainAdapter.notifyDataSetChanged()
    }

}