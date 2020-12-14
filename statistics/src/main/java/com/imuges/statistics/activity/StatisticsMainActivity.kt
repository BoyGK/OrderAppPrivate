package com.imuges.statistics.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.BarUtils
import com.imuges.statistics.R
import com.nullpt.base.framework.BasePermissionActivity

/**
 * @author BGQ
 * 统计模块
 */
class StatisticsMainActivity : BasePermissionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics_main)

        //顶部状态栏
        BarUtils.setStatusBarColor(this, getColor(R.color.colorAccent))

        val parent = window.findViewById<View>(android.R.id.content)
        val root = parent.findViewById<ViewGroup>(R.id.root) ?: return
        val lp = root.layoutParams as ViewGroup.MarginLayoutParams
        lp.topMargin = BarUtils.getStatusBarHeight()
        root.layoutParams = lp
    }
}