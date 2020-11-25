package com.imuges.order.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.widget.addTextChangedListener
import com.blankj.utilcode.util.KeyboardUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.google.android.flexbox.FlexboxLayoutManager
import com.imuges.order.R
import com.imuges.order.activity.views.IAddGoodsView
import com.imuges.order.adapter.GoodsByAddAdapter
import com.imuges.order.adapter.GoodsTypeByAddAdapter
import com.imuges.order.base.BaseFullTitleActivity
import com.imuges.order.base.BasePresenter
import com.imuges.order.base.ContentView
import com.imuges.order.base.IBaseView
import com.imuges.order.expan.layout
import com.imuges.order.expan.marginBottom
import com.imuges.order.expan.showBottomDialog
import com.imuges.order.expan.showBottomView
import com.imuges.order.presenter.AddGoodsPresenter
import kotlinx.android.synthetic.main.activity_add_goods.*

/**
 * @author BGQ
 * 添加本地货物仓库
 */
@SuppressLint("NonConstantResourceId")
@ContentView(R.layout.activity_add_goods)
class AddGoodsActivity : BaseFullTitleActivity(), IAddGoodsView, View.OnClickListener,
    OnItemClickListener {

    private val mGoodsTypeAdapter by lazy {
        GoodsTypeByAddAdapter(defaultPresenter<AddGoodsPresenter>().getGoodsType())
    }
    private val mGoodsAdapter by lazy {
        GoodsByAddAdapter(defaultPresenter<AddGoodsPresenter>().getGoods())
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, AddGoodsActivity::class.java))
        }
    }

    override fun getPresenterMap(): Map<String, BasePresenter<out IBaseView>> {
        return mapOf(Pair(DEFAULT, AddGoodsPresenter()))
    }

    override fun onCreate() {
        initTitleBar()
        initView()
        initListener()
    }

    override fun initTitleBar() {
        setStateBarColor(Color.WHITE)
        setStateBarLightModel(true)
    }

    private fun initView() {
        typeRecycler.adapter = mGoodsTypeAdapter
        typeRecycler.layoutManager = FlexboxLayoutManager(this)
        goodsRecyclerView.adapter = mGoodsAdapter
    }

    private fun initListener() {
        backCard.setOnClickListener(this)
        typeTitle.setOnClickListener(this)
        mGoodsTypeAdapter.setOnItemClickListener(this)
        mGoodsAdapter.setOnItemClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            backCard -> {
                onBackPressed()
            }
            typeTitle -> {
                typeRecycler.isVisible = !typeRecycler.isVisible
            }
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when (adapter) {
            is GoodsTypeByAddAdapter -> {
                defaultPresenter<AddGoodsPresenter>().selectType(position)
            }
            is GoodsByAddAdapter -> {
                defaultPresenter<AddGoodsPresenter>().addGoods(position)
            }
        }
    }

    override fun updateTypeList() {
        mGoodsTypeAdapter.notifyDataSetChanged()
    }

    override fun updateTypeItem(position: Int) {
        mGoodsTypeAdapter.notifyItemChanged(position)
    }

    override fun updateGoodsList() {
        mGoodsAdapter.notifyDataSetChanged()
    }

    override fun showGoodsEditView(goodsCall: (goodsName: String, percent: Float, unit: String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun showTypeEditView(nameCall: (typeName: String) -> Unit) {
        val layout = layout(R.layout.dialog_add_goods_type, isCache = true)
        val edit = layout.findViewById<AppCompatEditText>(R.id.goods_type_edit)
        val submit = layout.findViewById<AppCompatImageView>(R.id.goods_type_submit)
        val dialog = showBottomDialog(layout, 0.5f)
        submit.setOnClickListener {
            if (edit.text.toString().isEmpty()) {
                return@setOnClickListener
            }
            nameCall(edit.text.toString())
            dialog.dismiss()
        }
    }

}