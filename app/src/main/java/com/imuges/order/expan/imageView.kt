package com.imuges.order.expan

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.imuges.order.R
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * @author BGQ
 * 图片加载扩展
 */


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

/**
 * 加载模糊图片
 */
fun ImageView.loadImage(path: String) {
    Glide.with(this).load(path).placeholder(R.mipmap.ic_background_004)
        .override(measuredWidth, measuredHeight)
        .into(this)
}

/**
 * 加载模糊图片
 */
fun ImageView.loadImage(id: Int) {
    Glide.with(this).load(id).placeholder(R.mipmap.ic_background_004)
        .override(measuredWidth, measuredHeight)
        .into(this)
}
