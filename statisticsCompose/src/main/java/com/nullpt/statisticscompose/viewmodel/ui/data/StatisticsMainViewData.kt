package com.nullpt.statisticscompose.viewmodel.ui.data

/**
 * @author BGQ
 * 统计主界面数据模型
 */
data class StatisticsMainViewData(
    var types: MutableList<Chart>
) {
    companion object {
        /**
         * 饼图
         */
        const val TYPE_PIE_CHART = 1

        /**
         * 折线图
         */
        const val TYPE_LINE_CHART = 2

        /**
         * 柱状图
         */
        const val TYPE_HISTOGRAM = 3

        /**
         * 统计表
         */
        const val TYPE_EXCEL = 4
    }

    data class Chart(val type: Int, val name: String, val description: String)
}