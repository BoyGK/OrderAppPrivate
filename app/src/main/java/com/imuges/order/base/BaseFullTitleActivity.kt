package com.imuges.order.base

import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.BarUtils
import com.imuges.order.R
import com.nullpt.base.framework.BaseMVPActivity

/**
 * @author BGQ
 * 顶部栏颜色沉浸
 */
abstract class BaseFullTitleActivity : BaseMVPActivity() {

    open fun initTitleBar() {}

    /**
     * 设置顶部栏颜色
     */
    fun setStateBarColor(color: Int, topMargin: Boolean = true) {
        BarUtils.setStatusBarColor(this, color)
        if (topMargin) {
            topMargin()
        }
    }

    /**
     * 设置顶部栏文字颜色
     * true -> 黑色文字
     * false -> 白色文字
     */
    fun setStateBarLightModel(isLightModel: Boolean) {
        BarUtils.setStatusBarLightMode(this, isLightModel)
    }

    /**
     * 设置间距
     */
    private fun topMargin() {
        val parent = window.findViewById<View>(android.R.id.content)
        val root = parent.findViewById<ViewGroup>(R.id.root) ?: return
        val lp = root.layoutParams as ViewGroup.MarginLayoutParams
        lp.topMargin = BarUtils.getStatusBarHeight()
        root.layoutParams = lp
    }

}