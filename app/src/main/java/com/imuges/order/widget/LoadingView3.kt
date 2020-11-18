package com.imuges.order.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.addListener


/**
 * @author BGQ
 * 简单加载动画 3
 */
class LoadingView3 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint by lazy { Paint() }

    //方框大小
    private val mRectHalfWidth by lazy { width / 6f }
    /**
     * 矩形数组
     * 4 7 9
     * 2 5 8
     * 1 3 6
     */
    private val mRects by lazy {
        arrayOf(
            RectF(
                width / 6f - mRectHalfWidth,
                height / 6f * 5f - mRectHalfWidth,
                width / 6f + mRectHalfWidth,
                height / 6f * 5f + mRectHalfWidth
            ),
            RectF(
                width / 6f - mRectHalfWidth,
                height / 2f - mRectHalfWidth,
                width / 6f + mRectHalfWidth,
                height / 2f + mRectHalfWidth
            ),
            RectF(
                width / 2f - mRectHalfWidth,
                height / 6f * 5f - mRectHalfWidth,
                width / 2f + mRectHalfWidth,
                height / 6f * 5f + mRectHalfWidth
            ),
            RectF(
                width / 6f - mRectHalfWidth,
                height / 6f - mRectHalfWidth,
                width / 6f + mRectHalfWidth,
                height / 6f + mRectHalfWidth
            ),
            RectF(
                width / 2f - mRectHalfWidth,
                height / 2f - mRectHalfWidth,
                width / 2f + mRectHalfWidth,
                height / 2f + mRectHalfWidth
            ),
            RectF(
                width / 6f * 5f - mRectHalfWidth,
                height / 6f * 5f - mRectHalfWidth,
                width / 6f * 5f + mRectHalfWidth,
                height / 6f * 5f + mRectHalfWidth
            ),
            RectF(
                width / 2f - mRectHalfWidth,
                height / 6f - mRectHalfWidth,
                width / 2f + mRectHalfWidth,
                height / 6f + mRectHalfWidth
            ),
            RectF(
                width / 6f * 5f - mRectHalfWidth,
                height / 2f - mRectHalfWidth,
                width / 6f * 5f + mRectHalfWidth,
                height / 2f + mRectHalfWidth
            ),
            RectF(
                width / 6f * 5f - mRectHalfWidth,
                height / 6f - mRectHalfWidth,
                width / 6f * 5f + mRectHalfWidth,
                height / 6f + mRectHalfWidth
            )
        )
    }

    //五个区域的动画执行器
    private val mAnimation1 by lazy { ValueAnimator() }
    private val mAnimation2 by lazy { ValueAnimator() }
    private val mAnimation3 by lazy { ValueAnimator() }
    private val mAnimation4 by lazy { ValueAnimator() }
    private val mAnimation5 by lazy { ValueAnimator() }

    //执行标记
    private var mNext = true

    init {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.WHITE
        initAnimationListener()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        drawRect(canvas)

        if (mNext) {
            postAnim()
        }
    }

    /**
     * 绘制变换矩形
     */
    private fun drawRect(canvas: Canvas) {
        for (i in mRects.indices) {
            canvas.drawRect(mRects[i], mPaint)
        }
    }

    /**
     * 初始化动画监听逻辑
     */
    private fun initAnimationListener() {
        //动画结束事件
        mAnimation5.addListener(onEnd = {
            mNext = true
            invalidate()
        })
        //动画更新事件
        mAnimation1.addUpdateListener {
            val vl = it.animatedValue as Float
            mRects[0].set(
                mRects[0].centerX() - vl,
                mRects[0].centerY() - vl,
                mRects[0].centerX() + vl,
                mRects[0].centerY() + vl
            )
            invalidate()
        }
        mAnimation2.addUpdateListener {
            val vl = it.animatedValue as Float
            for (i in 1..2) {
                mRects[i].set(
                    mRects[i].centerX() - vl,
                    mRects[i].centerY() - vl,
                    mRects[i].centerX() + vl,
                    mRects[i].centerY() + vl
                )
            }
            invalidate()
        }
        mAnimation3.addUpdateListener {
            val vl = it.animatedValue as Float
            for (i in 3..5) {
                mRects[i].set(
                    mRects[i].centerX() - vl,
                    mRects[i].centerY() - vl,
                    mRects[i].centerX() + vl,
                    mRects[i].centerY() + vl
                )
            }
            invalidate()
        }
        mAnimation4.addUpdateListener {
            val vl = it.animatedValue as Float
            for (i in 6..7) {
                mRects[i].set(
                    mRects[i].centerX() - vl,
                    mRects[i].centerY() - vl,
                    mRects[i].centerX() + vl,
                    mRects[i].centerY() + vl
                )
            }
            invalidate()
        }
        mAnimation5.addUpdateListener {
            val vl = it.animatedValue as Float
            mRects[8].set(
                mRects[8].centerX() - vl,
                mRects[8].centerY() - vl,
                mRects[8].centerX() + vl,
                mRects[8].centerY() + vl
            )
            invalidate()
        }
    }

    /**
     * 分段执行动画
     */
    private fun postAnim() {
        mNext = false
        mAnimation1.setFloatValues(mRectHalfWidth, 0f, mRectHalfWidth)
        mAnimation1.duration = 600L
        mAnimation1.start()

        mAnimation2.setFloatValues(mRectHalfWidth, 0f, mRectHalfWidth)
        mAnimation2.duration = 600L
        mAnimation2.startDelay = 150L
        mAnimation2.start()

        mAnimation3.setFloatValues(mRectHalfWidth, 0f, mRectHalfWidth)
        mAnimation3.duration = 600L
        mAnimation3.startDelay = 300L
        mAnimation3.start()

        mAnimation4.setFloatValues(mRectHalfWidth, 0f, mRectHalfWidth)
        mAnimation4.duration = 600L
        mAnimation4.startDelay = 450L
        mAnimation4.start()

        mAnimation5.setFloatValues(mRectHalfWidth, 0f, mRectHalfWidth)
        mAnimation5.duration = 600L
        mAnimation5.startDelay = 600L
        mAnimation5.start()
    }

}