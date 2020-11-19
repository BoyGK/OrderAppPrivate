package com.imuges.order.util

import androidx.annotation.DrawableRes
import com.imuges.order.R

/**
 * @author BGQ
 * 背景图片 序号->资源 转换
 */
object BackGroundTransform {

    @DrawableRes
    fun transform(background: Int): Int {
        return when (background) {
            1 -> R.mipmap.ic_background_001
            2 -> R.mipmap.ic_background_002
            3 -> R.mipmap.ic_background_003
            4 -> R.mipmap.ic_background_004
            else -> {
                R.mipmap.ic_background_001
            }
        }
    }
}