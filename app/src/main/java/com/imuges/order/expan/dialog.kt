package com.imuges.order.expan

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import com.imuges.order.R

/**
 * @author BGQ
 * 弹框方式扩展
 */


/**
 * 打开一个Pop Window
 */
fun View.showPopView(popView: View, xOff: Int, yOff: Int): PopupWindow {
    popView.isFocusable = true
    val popupWindow = PopupWindow(
        popView,
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    popupWindow.setBackgroundDrawable(ColorDrawable())

    // 外部点击事件
    popupWindow.isOutsideTouchable = true
    popupWindow.isFocusable = false

    popView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    popupWindow.showAsDropDown(this, xOff, yOff)
    return popupWindow
}


/**
 * 居中弹出ialog
 */
fun Activity.showCenterDialog(view: View): Dialog {
    val dialog = Dialog(this, R.style.translucent_dialog)
    dialog.setCancelable(true)
    dialog.setContentView(view)
    val window = dialog.window!!
    val attr = window.attributes
    attr.width = WindowManager.LayoutParams.WRAP_CONTENT
    attr.height = WindowManager.LayoutParams.WRAP_CONTENT
    attr?.dimAmount = 0.3f
    window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    window.attributes = attr
    dialog.show()
    return dialog
}


/**
 * 底部界面Dialog
 */
fun Activity.showBottomDialog(view: View, dimAmount: Float = 0f): Dialog {
    val dialog = Dialog(this, R.style.translucent_dialog)
    dialog.setCancelable(true)
    dialog.setContentView(view)
    val window = dialog.window!!
    val attr = window.attributes
    attr.width = WindowManager.LayoutParams.MATCH_PARENT
    attr.height = WindowManager.LayoutParams.WRAP_CONTENT
    attr.gravity = Gravity.BOTTOM
    attr.horizontalMargin = 50f
    attr?.dimAmount = dimAmount
    window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    attr.windowAnimations = R.style.dialog_bottom
    window.attributes = attr
    dialog.show()
    return dialog
}


/**
 * 底部界面弹出
 */
fun Activity.showBottomView(view: View): WindowManager {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val layoutParams = WindowManager.LayoutParams()
    layoutParams.gravity = Gravity.BOTTOM
    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
    layoutParams.flags =
        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    layoutParams.windowAnimations = R.style.dialog_bottom
    windowManager.addView(view, layoutParams)
    return windowManager
}