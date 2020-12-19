package com.imuges.order.util

/**
 * @author BGQ
 * 背景图片 序号->资源 转换
 */
object MottoTransform {

    /**
     * 随机返回一代格言
     */
    fun randomMotto(): String {
        return when ((Math.random() * 10).toInt() % 1 + 1) {
            1 -> "伟人之所以伟大，是因为他与别人共处逆境时，别人失去了信心，他却下决心实现自己的目标。"
            else -> {
                ""
            }
        }
    }

}