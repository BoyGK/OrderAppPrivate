package com.imuges.order.expan

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * 加载模糊图片
 */
fun ImageView.loadBlurImage(path: String, radius: Int = 8, sampling: Int = 1) {
    Glide.with(this).load(path)
        .apply(RequestOptions.bitmapTransform(BlurTransformation(radius, sampling)))
        .override(measuredWidth, measuredHeight)
        .into(this)
}

/**
 * 加载模糊图片
 */
fun ImageView.loadBlurImage(id: Int, radius: Int = 8, sampling: Int = 1) {
    Glide.with(this).load(id)
        .apply(RequestOptions.bitmapTransform(BlurTransformation(radius, sampling)))
        .override(measuredWidth, measuredHeight)
        .into(this)
}