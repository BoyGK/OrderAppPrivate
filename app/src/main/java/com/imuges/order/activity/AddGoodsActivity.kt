package com.imuges.order.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.UriUtils
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
import com.imuges.order.expan.*
import com.imuges.order.presenter.AddGoodsPresenter
import kotlinx.android.synthetic.main.activity_add_goods.*
import java.io.FileDescriptor

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
        importExcel.setOnClickListener(this)
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
                typeTitleArrow.setImageResource(
                    if (typeRecycler.isVisible) R.drawable.ic_arrow_up_black
                    else R.drawable.ic_arrow_down_black
                )
            }
            importExcel -> {
                defaultPresenter<AddGoodsPresenter>().importExcel()
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

    @SuppressLint("SetTextI18n")
    override fun updateCurrentType(type: String) {
        goodsTitle.text = "${getString(R.string.goods_lists)}(${type})"
    }

    override fun updateTypeList() {
        mGoodsTypeAdapter.notifyDataSetChanged()
    }

    override fun updateTypeInsert(position: Int) {
        mGoodsTypeAdapter.notifyItemInserted(position)
    }

    override fun updateTypeItem(position: Int) {
        mGoodsTypeAdapter.notifyItemChanged(position)
    }

    override fun updateGoodsList() {
        mGoodsAdapter.notifyDataSetChanged()
    }

    override fun updateGoodsInsert(position: Int) {
        mGoodsAdapter.notifyItemInserted(position)
    }

    override fun selectFile(fileCall: (name: String, fd: FileDescriptor) -> Unit) {
        openFile(
            mimeTypes = arrayOf(
                MIME_MapTable[".xls"] ?: error(".xls is not config"),
                MIME_MapTable[".xlsx"] ?: error(".xlsx is not config")
            )
        ) { name, fd ->
            fileCall(name, fd)
        }
    }

    override fun showGoodsEditView(
        goodsCall: (goodsName: String, percent: Float, unit: String, imagePath: String) -> Unit
    ) {
        val layout = layout(R.layout.dialog_add_goods, isCache = true)
        val editName = layout.findViewById<AppCompatEditText>(R.id.goods_name_edit)
        val editPercent = layout.findViewById<AppCompatEditText>(R.id.goods_percent_edit)
        val editUnit = layout.findViewById<AppCompatEditText>(R.id.goods_unit_edit)
        val image = layout.findViewById<AppCompatImageView>(R.id.goods_image)
        val submit = layout.findViewById<AppCompatImageView>(R.id.goods_type_submit)
        var path = ""
        val dialog = showBottomDialog(layout, 0.5f)
        image.setOnClickListener {
            permission(
                permissions = arrayOf(
                    PermissionConstants.CAMERA,
                    PermissionConstants.STORAGE
                ), onSuccess = {
                    openCamera {
                        path = UriUtils.uri2File(it).absolutePath
                        val options = BitmapFactory.Options()
                        options.inSampleSize = 5
                        val bitmap = BitmapFactory.decodeFile(path, options)
                        image.setImageBitmap(bitmap)
                    }
                }, onRefuse = {
                    toast(getString(R.string.no_permission))
                })

        }
        submit.setOnClickListener {
            if (editName.text.toString().isEmpty() ||
                editPercent.text.toString().isEmpty() ||
                editUnit.text.toString().isEmpty()
            ) {
                return@setOnClickListener
            }
            goodsCall(
                editName.text.toString(),
                editPercent.text.toString().toFloat(),
                editUnit.text.toString(),
                path
            )
            editName.setText("")
            editPercent.setText("")
            editUnit.setText("")
            image.setImageResource(R.drawable.ic_add_white)
            dialog.dismiss()
        }
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
            edit.setText("")
            dialog.dismiss()
        }
    }

    override fun showWaitProgress() {
        waitLoading.isVisible = true
    }

    override fun hideWaitProgress() {
        waitLoading.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseCacheView()
    }

}