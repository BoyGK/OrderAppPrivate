package com.imuges.order.expan

import com.blankj.utilcode.util.ConvertUtils

/**
 * @author BGQ
 *单位转换扩展
 */


fun Float.dp2px() = ConvertUtils.dp2px(this)

fun Float.sp2px() = ConvertUtils.sp2px(this)

fun Float.px2sp() = ConvertUtils.px2sp(this)

fun Float.px2dp() = ConvertUtils.px2dp(this)

fun Int.dp2px() = ConvertUtils.dp2px(toFloat())

fun Int.sp2px() = ConvertUtils.sp2px(toFloat())

fun Int.px2sp() = ConvertUtils.px2sp(toFloat())

fun Int.px2dp() = ConvertUtils.px2dp(toFloat())