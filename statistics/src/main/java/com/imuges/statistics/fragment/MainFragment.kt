package com.imuges.statistics.fragment

import android.view.View
import androidx.navigation.findNavController
import com.imuges.statistics.R
import com.imuges.statistics.presenter.MainPresenter
import com.nullpt.base.framework.BaseMVPFragment
import com.nullpt.base.framework.BasePresenter
import com.nullpt.base.framework.IBaseView
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * @author BGQ
 * 统计功能导航栏界面
 */
class MainFragment : BaseMVPFragment(), View.OnClickListener {

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun getPresenterMap(): Map<String, BasePresenter<out IBaseView>> {
        return mapOf(DEFAULT to MainPresenter())
    }

    override fun initView() {
    }

    override fun onClick(view: View) {
        val action = MainFragmentDirections.actionMainFragmentToPieChartFragment()
        view.findNavController().navigate(action)
    }

}