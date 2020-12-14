package com.imuges.statistics.fragment

import com.imuges.statistics.R
import com.imuges.statistics.presenter.PieChartPresenter
import com.nullpt.base.framework.BaseMVPFragment
import com.nullpt.base.framework.BasePresenter
import com.nullpt.base.framework.IBaseView

/**
 * @author BGQ
 * 饼图
 */
class PieChartFragment : BaseMVPFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_pie_chart

    override fun getPresenterMap(): Map<String, BasePresenter<out IBaseView>> {
        return mapOf(Pair(DEFAULT, PieChartPresenter()))
    }

    override fun initView() {

    }


}