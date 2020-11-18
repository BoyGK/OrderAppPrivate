package com.imuges.order.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener

/**
 * @author BGQ
 * 简单的加载动画 1
 */
class LoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint by lazy { Paint() }

    /**
     * 主体背景颜色
     */
    var mBackColor = Color.WHITE

    //方框大小
    private val mRectHalfWidth = 10f
    //移动的小方块(基于（0,0）和（宽,高）)
    private val mRectA by lazy {
        RectF(
            0f - mRectHalfWidth,
            0f - mRectHalfWidth,
            0f + mRectHalfWidth,
            0f + mRectHalfWidth
        )
    }
    private val mRectB by lazy {
        RectF(
            width.toFloat() - mRectHalfWidth,
            height.toFloat() - mRectHalfWidth,
            width.toFloat() + mRectHalfWidth,
            height.toFloat() + mRectHalfWidth
        )
    }
    private val mRectTempA by lazy { RectF() }
    private val mRectTempB by lazy { RectF() }

    //旋转角度
    private var mRotation = 0f

    /**
     * 顶点坐标(顺时针)
     * 0,1
     * 3,2
     */
    private val mBorderPoint by lazy {
        arrayOf(
            PointF(0f, 0f),
            PointF(width.toFloat(), 0f),
            PointF(width.toFloat(), height.toFloat()),
            PointF(0f, height.toFloat())
        )
    }

    //方块的当前坐标
    private var mIndexA = 0
    private var mIndexB = 2

    //移动动画
    private val mAnimation by lazy { ValueAnimator() }

    //执行标记
    private var mNext = true

    init {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        mPaint.color = mBackColor

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
     * 绘制方块
     */
    private fun drawRect(canvas: Canvas) {
        canvas.save()
        canvas.rotate(-mRotation, mRectA.centerX(), mRectA.centerY())
        canvas.drawRect(mRectA, mPaint)
        canvas.restore()

        canvas.save()
        canvas.rotate(-mRotation, mRectB.centerX(), mRectB.centerY())
        canvas.drawRect(mRectB, mPaint)
        canvas.restore()

    }

    /**
     * 初始化动画监听逻辑
     */
    private fun initAnimationListener() {
        //动画结束事件
        mAnimation.addListener(onEnd = {
            mNext = true
            if (++mIndexA > 3) mIndexA = 0
            if (++mIndexB > 3) mIndexB = 0
            invalidate()
        })
        //动画更新事件
        mAnimation.addUpdateListener {
            val vl = it.animatedValue as Float
            val progress = when (mIndexA) {
                0, 1 -> {
                    vl / width.toFloat()
                }
                2, 3 -> {
                    1f - vl / width.toFloat()
                }
                else -> 0f
            }
            mRotation = 90 * progress
            updateRectA(vl, progress)
            updateRectB(vl, progress)
            invalidate()
        }
    }

    /**
     * 更新方块A
     */
    private fun updateRectA(vl: Float, progress: Float) {
        when (mIndexA) {
            0, 2 -> {
                mRectA.left = vl - mRectHalfWidth - mRectHalfWidth * progress
                mRectA.right = vl + mRectHalfWidth + mRectHalfWidth * progress
                //随动边
                mRectA.top = mRectTempA.top - mRectHalfWidth * progress
                mRectA.bottom = mRectTempA.bottom + mRectHalfWidth * progress
            }
            1, 3 -> {
                mRectA.top = vl - mRectHalfWidth - mRectHalfWidth * (1f - progress)
                mRectA.bottom = vl + mRectHalfWidth + mRectHalfWidth * (1f - progress)
                //随动边
                mRectA.left = mRectTempA.left + mRectHalfWidth * progress
                mRectA.right = mRectTempA.right - mRectHalfWidth * progress
            }
        }

    }

    /**
     * 更新方块B
     */
    private fun updateRectB(avl: Float, progress: Float) {
        val vl = width - avl
        when (mIndexB) {
            0, 2 -> {
                mRectB.left = vl - mRectHalfWidth - mRectHalfWidth * progress
                mRectB.right = vl + mRectHalfWidth + mRectHalfWidth * progress
                //随动边
                mRectB.top = mRectTempB.top - mRectHalfWidth * progress
                mRectB.bottom = mRectTempB.bottom + mRectHalfWidth * progress
            }
            1, 3 -> {
                mRectB.top = vl - mRectHalfWidth - mRectHalfWidth * (1f - progress)
                mRectB.bottom = vl + mRectHalfWidth + mRectHalfWidth * (1f - progress)
                //随动边
                mRectB.left = mRectTempB.left + mRectHalfWidth * progress
                mRectB.right = mRectTempB.right - mRectHalfWidth * progress
            }
        }
    }

    /**
     * 执行动画
     */
    private fun postAnim() {
        mNext = false
        mRectTempA.set(mRectA)
        mRectTempB.set(mRectB)
        when (mIndexA) {
            3 -> {
                mAnimation.setFloatValues(mBorderPoint[3].y, mBorderPoint[0].y)
            }
            0 -> {
                mAnimation.setFloatValues(mBorderPoint[0].x, mBorderPoint[1].x)
            }
            1 -> {
                mAnimation.setFloatValues(mBorderPoint[1].y, mBorderPoint[2].y)
            }
            2 -> {
                mAnimation.setFloatValues(mBorderPoint[2].x, mBorderPoint[3].x)
            }
        }
        mAnimation.duration = 500L
        mAnimation.interpolator = AccelerateDecelerateInterpolator()
        mAnimation.start()
    }

}