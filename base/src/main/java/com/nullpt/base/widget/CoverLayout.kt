package com.nullpt.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.children
import kotlin.math.abs
import kotlin.math.max

/**
 * @author BGQ
 * 抽屉拖拽布局，左右排布
 * 必须包含两个子view
 */
class CoverLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val childBounds = arrayOf(Rect(), Rect())

    private var downX = 0f
    private var downScrollX = 0f
    private var scrolling = false
    private val overScroller = OverScroller(context)
    private val viewConfiguration = ViewConfiguration.get(context)
    private val velocityTracker = VelocityTracker.obtain()
    private var minVelocity = viewConfiguration.scaledMinimumFlingVelocity
    private var maxVelocity = viewConfiguration.scaledMaximumFlingVelocity
    private var pagingSlop = viewConfiguration.scaledPagingTouchSlop

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount != childBounds.size) {
            error("Assertion failed. must only have two view")
        }
        var selfWidthSize = 0
        var selfHeightSize = 0
        var widthUsed = 0
        val heightUsed = 0
        for ((index, child) in children.withIndex()) {
            //不使用widthUsed测量这里，要把第二个view也留出来全屏宽的位置,但是记录位置需要加上
            measureChildWithMargins(
                child,
                widthMeasureSpec,
                0,//widthUsed
                heightMeasureSpec,
                heightUsed
            )

            childBounds[index].set(
                widthUsed,
                0,
                widthUsed + child.measuredWidth,
                heightUsed + child.measuredHeight
            )

            selfWidthSize += child.measuredWidth
            selfHeightSize = max(selfHeightSize, child.measuredHeight)
            widthUsed += child.measuredWidth
        }
        setMeasuredDimension(selfWidthSize, selfHeightSize)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((index, child) in children.withIndex()) {
            val bounds = childBounds[index]
            child.layout(bounds.left, bounds.top, bounds.right, bounds.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        var result = false
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                scrolling = false
                downX = event.x
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = downX - event.x
                if (abs(dx) > pagingSlop) {
                    scrolling = true
                    parent.requestDisallowInterceptTouchEvent(true)
                    result = true
                }
            }
        }
        return result
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = (downX - event.x + downScrollX).toInt()
                    .coerceAtLeast(0)
                    .coerceAtMost(childBounds[1].width())
                scrollTo(dx, 0)
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000, maxVelocity.toFloat())
                val vx = velocityTracker.xVelocity
                val scrollX = scrollX
                val targetPage = if (abs(vx) < minVelocity) {
                    if (scrollX > childBounds[1].width() / 2) 1 else 0
                } else {
                    if (vx < 0) 1 else 0
                }
                val scrollDistance =
                    if (targetPage == 1) childBounds[1].width() - scrollX else -scrollX
                overScroller.startScroll(getScrollX(), 0, scrollDistance, 0)
                postInvalidateOnAnimation()
            }
        }
        return true
    }

    override fun computeScroll() {
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.currX, overScroller.currY)
            postInvalidateOnAnimation()
        }
    }

}