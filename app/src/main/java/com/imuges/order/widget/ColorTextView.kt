package com.imuges.order.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * @author BGQ
 * 跑马灯样式，彩色字体
 */
class ColorTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint by lazy { Paint() }

    private var mOffset = 10f
    private var mOffsetTemp = mOffset

    //渐变矩阵
    private val mShaderLocalMatrix by lazy { Matrix() }

    //渐变颜色
    private val mColorShader by lazy {
        val colorArray = intArrayOf(Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN)
        val positionArray = floatArrayOf(0f, 0.3f, 0.6f, 0.9f)
        val shader = LinearGradient(
            0f, height / 2f, width.toFloat(), height / 2f,
            colorArray,
            positionArray,
            Shader.TileMode.REPEAT
        )
        shader.getLocalMatrix(mShaderLocalMatrix)
        shader
    }

    init {
        mPaint.isAntiAlias = true
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        drawText(canvas)

        postAnim()

    }

    /**
     * 绘制文字和上层背景
     */
    private fun drawText(canvas: Canvas) {

        mShaderLocalMatrix.postTranslate(mOffset, 0f)
        mColorShader.setLocalMatrix(mShaderLocalMatrix)

        mPaint.style = Paint.Style.FILL
        mPaint.shader = mColorShader
        mPaint.textSize = sp2px(36f).toFloat()
        canvas.drawText("动画", paddingStart.toFloat(), height.toFloat() / 3f * 2f, mPaint)
    }

    /**
     * 循环执行动画
     */
    private fun postAnim() {
        postDelayed({
            mOffsetTemp += 10f
            if (mOffsetTemp >= width) {
                mShaderLocalMatrix.postTranslate(-mOffsetTemp, 0f)
                mColorShader.setLocalMatrix(mShaderLocalMatrix)
                mOffsetTemp -= width
            }
            invalidate()
        }, 30L)
    }

    /**
     * 文字大小转换
     */
    private fun sp2px(spValue: Float): Int {
        val fontScale = Resources.getSystem().displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

}