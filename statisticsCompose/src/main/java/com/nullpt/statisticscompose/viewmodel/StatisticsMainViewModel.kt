package com.nullpt.statisticscompose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nullpt.statisticscompose.viewmodel.ui.data.StatisticsMainViewData

/**
 * @author BGQ
 * 统计主界面数据管理
 */
class StatisticsMainViewModel : ViewModel() {

    /**
     * 元数据
     */
    private lateinit var viewData: StatisticsMainViewData

    /**
     * 页面数据状态
     * todo LiveData
     */
    private val viewDataState by lazy { mutableStateOf(viewData) }

    /**
     * 初始化
     */
    init {
        val pieChart = StatisticsMainViewData.Chart(
            StatisticsMainViewData.TYPE_PIE_CHART,
            "饼状图",
            "更清楚的观察收入所占比例"
        )
        val histogram = StatisticsMainViewData.Chart(
            StatisticsMainViewData.TYPE_HISTOGRAM,
            "柱状图",
            "更具体的对比年、月、日收入分布"
        )
        val lineChart = StatisticsMainViewData.Chart(
            StatisticsMainViewData.TYPE_LINE_CHART,
            "折线图",
            "更直观的分析收入走势情况"
        )
        val excel = StatisticsMainViewData.Chart(
            StatisticsMainViewData.TYPE_EXCEL,
            "表格",
            "更详细的展示收入细节"
        )
        viewData = StatisticsMainViewData(mutableListOf(pieChart, histogram, lineChart, excel))
    }

    /**
     * 获取数据
     */
    fun viewData() = viewDataState.value


}