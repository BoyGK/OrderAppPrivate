package com.imuges.order.base

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.BarUtils
import com.imuges.order.R

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