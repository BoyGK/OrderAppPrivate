package com.imuges.order.presenter

import com.imuges.order.activity.views.IAddGoodsView
import com.imuges.order.base.BasePresenter
import com.imuges.order.data.GoodsSimpleInfo
import com.imuges.order.data.GoodsTypeInfo
import com.imuges.order.excel.Excel
import com.imuges.order.excel.ExcelReader
import com.imuges.order.model.AddGoodsModel
import com.imuges.order.util.PicturePathTransform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by "BGQ" on 2020/11/21.
 */
class AddGoodsPresenter : BasePresenter<IAddGoodsView>() {

    private val mAddGoodsModel by lazy { AddGoodsModel() }

    private val mGoodsTypeData by lazy { mutableListOf<GoodsTypeInfo>() }
    private val mGoodsData by lazy { mutableListOf<GoodsSimpleInfo>() }
    private val mGoodsCurrentTypeData by lazy { mutableListOf<GoodsSimpleInfo>() }
    private val mDefaultGoodsAddTypeId = -1

    private var mCurrentTypeId: Int = -1

    override fun onViewCreate() {
        initTypeData()
    }

    private fun initTypeData() {
        mAddGoodsModel.loadTypes {
            mGoodsTypeData.addAll(it)
            if (mGoodsTypeData.isNotEmpty()) {
                mCurrentTypeId = mGoodsTypeData[0].goodsTypeId
                mGoodsTypeData[0].select = true
                view?.updateCurrentType(mGoodsTypeData[0].typeName)
            }
            mGoodsTypeData.add(GoodsTypeInfo(0, "添加", false))
            view?.updateTypeList()

            //优先初始化类型，获取mCurrentTypeId
            initGoodsData()
        }
    }

    private fun initGoodsData() {
        mAddGoodsModel.loadGoods {
            mGoodsData.addAll(it)
            mGoodsData.add(
                0,
                GoodsSimpleInfo(
                    0,
                    mDefaultGoodsAddTypeId,
                    "添加货物",
                    0f,
                    "",
                    "",
                    itemType = GoodsSimpleInfo.ADD
                )
            )
            getGoods()
            view?.updateGoodsList()
        }
    }

    fun getGoodsType() = mGoodsTypeData

    fun getGoods(): MutableList<GoodsSimpleInfo> {
        if (mGoodsData.isEmpty()) {
            return mGoodsCurrentTypeData
        }
        mGoodsCurrentTypeData.clear()
        mGoodsCurrentTypeData.addAll(
            mGoodsData.filter {
                it.typeId == mDefaultGoodsAddTypeId || it.typeId == mCurrentTypeId
            }
        )
        return mGoodsCurrentTypeData
    }

    fun selectType(position: Int) {
        //最后一个是点击添加
        if (position == mGoodsTypeData.size - 1) {
            addGoodsType()
            return
        }
        //选中
        for (i in mGoodsTypeData.indices) {
            if (mGoodsTypeData[i].select) {
                mGoodsTypeData[i].select = false
                view?.updateTypeItem(i)
                continue
            }
            mGoodsTypeData[i].select = false
        }
        mCurrentTypeId = mGoodsTypeData[position].goodsTypeId
        mGoodsTypeData[position].select = true
        view?.updateTypeItem(position)
        view?.updateCurrentType(mGoodsTypeData[position].typeName)

        getGoods()
        view?.updateGoodsList()
    }

    fun addGoods(position: Int) {
        if (position != 0) {
            return
        }
        if (mCurrentTypeId == -1) {
            return
        }
        addGoods()
    }

    /**
     * 添加货物类型
     */
    private fun addGoodsType() {
        view?.showTypeEditView { typeName ->
            mAddGoodsModel.addTypes(typeName) {
                val insertPosition = mGoodsTypeData.size - 1
                mGoodsTypeData.add(insertPosition, it)
                view?.updateTypeInsert(insertPosition)
            }
        }
    }

    /**
     * 添加货物
     */
    private fun addGoods() {
        view?.showGoodsEditView { goodsName, percent, unit, imagePath ->
            mAddGoodsModel.addGoods(
                GoodsSimpleInfo(
                    0,
                    mCurrentTypeId,
                    goodsName,
                    percent,
                    unit,
                    PicturePathTransform.transform(imagePath)
                )
            ) {
                mGoodsData.add(it)
                mGoodsCurrentTypeData.add(it)
                view?.updateGoodsInsert(mGoodsTypeData.size)
            }
        }
    }

    /**
     * 导入Excel数据
     * 暂时不支持直接再Excel中配置图片，后续添加
     */
    fun importExcel() {
        view?.selectFile { fileName, fd ->
            val isXlsx = fileName.endsWith(".xlsx")
            val er = ExcelReader()
            er.setXlsx(isXlsx)
            val excel = er.parse(fd)
            view?.showWaitProgress()
            GlobalScope.launch(Dispatchers.Main) {
                splitExcelData(excel)
                view?.hideWaitProgress()
                //重新加载数据
                mGoodsTypeData.clear()
                mGoodsData.clear()
                mGoodsCurrentTypeData.clear()
                initTypeData()
            }
        }
    }

    /**
     * 将Excel数据插入数据库
     * name / percent / unit / imagePath
     */
    private suspend fun splitExcelData(excel: Excel) = withContext(Dispatchers.IO) {
        excel.sheets.forEach { sheet ->
            mAddGoodsModel.addTypes(sheet.name) { type ->
                sheet.rows.forEach { goods ->
                    mAddGoodsModel.addGoods(
                        GoodsSimpleInfo(
                            0,
                            type.goodsTypeId,
                            goods.cells[0].text,
                            goods.cells[1].text.toFloat(),
                            goods.cells[2].text,
                            PicturePathTransform.transform(goods.cells[3].text)
                        )
                    )
                }
            }
        }
    }

}