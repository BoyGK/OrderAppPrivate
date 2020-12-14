package com.nullpt.base.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * @author BGQ
 * 顶部栏流体背景
 */
class TitleBarBackground @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint by lazy { Paint() }
    private val mPath by lazy { Path() }

    /**
     * 主体背景颜色
     */
    var mBackColor = Color.parseColor("#FF8282CF")

    //波浪高度
    var mBolHeight = 150f

    //偏移量
    private var offset = 0f

    //动画刷新间隔
    private val POSITION = 15L

    init {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        mPaint.color = mBackColor
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        drawBol(canvas)
        postAnimation()
    }

    /**
     * 绘制波浪
     */
    private fun drawBol(canvas: Canvas) {
        mPath.reset()
        mPath.moveTo(0f, 0f)
        mPath.lineTo(-width.toFloat() + offset, height.toFloat())
        for (i in 0 until 2) {
            mPath.cubicTo(
                (width.toFloat() * (i - 1) + width.toFloat() / 5 * 2 + offset),
                (height.toFloat() + mBolHeight),
                (width.toFloat() * (i - 1) + width.toFloat() / 5 * 3 + offset),
                (height.toFloat() - mBolHeight),
                (width.toFloat() * i + offset),
                height.toFloat()
            )
        }
        mPath.lineTo(width.toFloat(), 0f)
        canvas.drawPath(mPath, mPaint)
    }

    /**
     * 执行动画
     */
    private fun postAnimation() {
        postDelayed({
            offset += 10f
            if (offset >= width.toFloat()) {
                offset = width.toFloat() - offset
            }
            invalidate()
        }, POSITION)
    }

}