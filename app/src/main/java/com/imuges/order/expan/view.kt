package com.imuges.order.expan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * @author BGQ
 * View的扩展方法
 */


/**
 * 动态设置bottomMargin
 */
fun View.marginBottom(params: Int) {
    val lp = layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.bottomMargin = params
        layoutParams = lp
    } else return
}


/**
 * 动态设置topMargin
 */
fun View.marginTop(params: Int) {
    val lp = layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.topMargin = params
        layoutParams = lp
    } else return
}


/**
 * 动态设置startMargin
 */
fun View.marginStart(params: Int) {
    val lp = layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.marginStart = params
        layoutParams = lp
    } else return
}


/**
 * 动态设置endMargin
 */
fun View.marginEnd(params: Int) {
    val lp = layoutParams
    if (lp is ViewGroup.MarginLayoutParams) {
        lp.marginEnd = params
        layoutParams = lp
    } else return
}

/**
 * 创建布局
 */
fun Context.layout(@LayoutRes id: Int, parent: ViewGroup? = null, isCache: Boolean = false): View {
    return if (isCache) {
        getViewFromCache(this, id, parent)
    } else {
        LayoutInflater.from(this).inflate(id, parent)
    }
}

/**
 * 缓存view持有context对象，容易造成内存泄漏
 * 除非大量创建相同的临时view，否则尽量不要使用
 */
fun Context.releaseCacheView() {
    val localThread = Thread.currentThread()
    mViewCache.remove(localThread)
}


/**
 * 创建的View的缓存
 */
private val mViewCache = mutableMapOf<Thread, MutableList<View>>()

/**
 * 添加缓存
 */
private fun addViewToCache(view: View) {
    val localThread = Thread.currentThread()
    mViewCache[localThread]!!.add(0, view)
    if (mViewCache.size > 10) {
        mViewCache[localThread]!!.removeAt(10)
    }
}

/**
 * 取view
 */
private fun getViewFromCache(context: Context, id: Int, parent: ViewGroup? = null): View {
    val localThread = Thread.currentThread()
    if (!mViewCache.keys.contains(localThread)) {
        mViewCache[localThread] = mutableListOf()
        val layout = LayoutInflater.from(context).inflate(id, parent)
        layout.tag = id
        addViewToCache(layout)
        return layout
    }
    val index = mViewCache[localThread]!!.indexOfFirst { it.tag == id }
    if (index == -1) {
        val layout = LayoutInflater.from(context).inflate(id, parent)
        addViewToCache(layout)
        layout.tag = id
        return layout
    }
    val temp = mViewCache[localThread]!![index]
    mViewCache[localThread]!![index] = mViewCache[localThread]!![0]
    mViewCache[localThread]!![0] = temp
    val parents = mViewCache[localThread]!![0].parent as ViewGroup
    parents.removeView(mViewCache[localThread]!![0])
    return mViewCache[localThread]!![0]
}