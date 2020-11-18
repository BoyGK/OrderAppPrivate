package com.imuges.order.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.addListener


/**
 * @author BGQ
 * 简单加载动画 2
 */
class LoadingView2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint by lazy { Paint() }

    //反转体
    private val mRect by lazy { RectF() }

    //背景颜色
    var mBackColor = Color.WHITE//Color.parseColor("#FF8282CF")

    /**
     * 是否画圆
     */
    var isCircle = false

    //反转角度
    private var mRotation = 0f

    //相机
    private val camera by lazy { Camera() }

    //移动动画
    private val mAnimation by lazy { ValueAnimator() }

    //执行标记
    private var mNext = true

    //坐标轴标记 1标识x轴，-1标识y轴
    private var mXYTag = 1

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
     * 初始化动画监听逻辑
     */
    private fun initAnimationListener() {
        //动画结束事件
        mAnimation.addListener(onEnd = {
            mNext = true
            mXYTag = -mXYTag
            invalidate()
        })
        //动画更新事件
        mAnimation.addUpdateListener {
            mRotation = it.animatedValue as Float
            invalidate()
        }
    }

    /**
     * 绘制反转矩形
     */
    private fun drawRect(canvas: Canvas) {
        mRect.set(0f, 0f, width.toFloat(), height.toFloat())

        canvas.save()

        camera.save()
        if (mXYTag == 1) {
            camera.rotateX(mRotation)
        } else {
            camera.rotateY(mRotation)
        }
        canvas.translate(mRect.centerX(), mRect.centerY())
        camera.applyToCanvas(canvas)
        canvas.translate(-mRect.centerX(), -mRect.centerY())
        camera.restore()

        if (isCircle) {
            canvas.drawCircle(mRect.centerX(), mRect.centerY(), mRect.width() / 2f, mPaint)
        } else {
            canvas.drawRect(mRect, mPaint)
        }
        canvas.restore()
    }

    /**
     * 执行动画
     */
    private fun postAnim() {
        mNext = false
        mAnimation.setFloatValues(0f, 180f)
        mAnimation.duration = 500L
        mAnimation.start()
    }

}