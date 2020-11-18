package com.imuges.order.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * @author BGQ
 * 简约的加载背景墙(学习记录)
 */
class ColorWallView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint by lazy { Paint() }

    /**
     * 圆半径和扩张进度
     */
    private val COR = 160f
    private val K = 8
    private val COR_PRO = COR / K
    private val POSITION = 10L

    //半径
    private var R = COR
    //半径临时变量
    private var RT = 0f
    //贝塞尔拟合圆常量
    private val C = 0.552284749831f

    //移动路径
    private val mPathMove by lazy { Path() }
    //常规路径
    private val mPath by lazy { Path() }

    //圆心点
    private val mCenterPoint = PointF(0f, 0f)

    //左侧控制点逆时针
    private val mLeftTopPoint1 = PointF()
    private val mLeftTopPoint2 = PointF()
    private val mLeftBottomPoint1 = PointF()
    private val mLeftBottomPoint2 = PointF()

    //右侧控制点顺时针
    private val mRightTopPoint1 = PointF()
    private val mRightTopPoint2 = PointF()
    private val mRightBottomPoint1 = PointF()
    private val mRightBottomPoint2 = PointF()

    //方向控制变量，1右侧，-1左侧
    private var mControl = 1

    //变化颜色
    private var mFontColor = Color.RED
    private var mBackColor = Color.WHITE

    init {
        mPaint.isAntiAlias = true
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        //初始化
        if (mCenterPoint.x == mCenterPoint.y && mCenterPoint.x == 0f) {
            initPoint()
        }

        drawBackGround(canvas, mControl)
        drawMoveHalf(canvas, mControl)
        drawHalf(canvas, mControl)

        //执行动画
        postAnim()
    }

    /**
     * 初始化point
     */
    private fun initPoint() {
        mCenterPoint.x = width / 2f - COR
        mCenterPoint.y = height / 4f * 3f
        updateControlPoint(mControl)
    }

    /**
     * 更新控制点坐标
     * 1右侧
     * -1左侧
     */
    private fun updateControlPoint(control: Int) {
        val tempR = (R - RT) * control
        //move half
        mLeftTopPoint1.x = mCenterPoint.x + tempR - tempR * C
        mLeftTopPoint1.y = mCenterPoint.y - R
        mLeftTopPoint2.x = mCenterPoint.x
        mLeftTopPoint2.y = mCenterPoint.y - R * C
        mLeftBottomPoint1.x = mCenterPoint.x
        mLeftBottomPoint1.y = mCenterPoint.y + R * C
        mLeftBottomPoint2.x = mCenterPoint.x + tempR - tempR * C
        mLeftBottomPoint2.y = mCenterPoint.y + R
        //other half
        mRightTopPoint1.x = mCenterPoint.x + tempR + tempR * C
        mRightTopPoint1.y = mCenterPoint.y - R
        mRightTopPoint2.x = mCenterPoint.x + 2f * tempR
        mRightTopPoint2.y = mCenterPoint.y - R * C
        mRightBottomPoint1.x = mCenterPoint.x + 2f * tempR
        mRightBottomPoint1.y = mCenterPoint.y + R * C
        mRightBottomPoint2.x = mCenterPoint.x + tempR + tempR * C
        mRightBottomPoint2.y = mCenterPoint.y + R
    }

    /**
     * 绘制相反的背景颜色
     */
    private fun drawBackGround(canvas: Canvas, control: Int) {
        canvas.drawColor(if (control == 1) mBackColor else mFontColor)
    }

    /**
     * 绘制右侧移动的一半
     */
    private fun drawMoveHalf(canvas: Canvas, control: Int) {
        mPathMove.reset()
        mPathMove.moveTo(
            mCenterPoint.x + R * control - RT * control + 1f * control,
            mCenterPoint.y - R
        )
        mPathMove.cubicTo(
            mLeftTopPoint1.x,
            mLeftTopPoint1.y,
            mLeftTopPoint2.x,
            mLeftTopPoint2.y,
            mCenterPoint.x,
            mCenterPoint.y
        )
        mPathMove.cubicTo(
            mLeftBottomPoint1.x,
            mLeftBottomPoint1.y,
            mLeftBottomPoint2.x,
            mLeftBottomPoint2.y,
            mCenterPoint.x + R * control - RT * control + 1f * control,
            mCenterPoint.y + R
        )
        mPaint.style = Paint.Style.FILL
        mPaint.color = if (control == 1) mFontColor else mBackColor
        canvas.drawPath(mPathMove, mPaint)
        if (RT > 0f) {
            canvas.drawRect(
                mCenterPoint.x + R * control - RT * control,
                mCenterPoint.y - R,
                mCenterPoint.x + R * control,
                mCenterPoint.y + R,
                mPaint
            )
        }
    }

    /**
     * 绘制右侧不动的另一半
     */
    private fun drawHalf(canvas: Canvas, control: Int) {
        mPath.reset()
        if (R > width) {
            return
        }
        mPath.moveTo(mCenterPoint.x + R * control - 1f * control, mCenterPoint.y - R)
        mPath.cubicTo(
            mRightTopPoint1.x,
            mRightTopPoint1.y,
            mRightTopPoint2.x,
            mRightTopPoint2.y,
            mCenterPoint.x + 2f * R * control,
            mCenterPoint.y
        )
        mPath.cubicTo(
            mRightBottomPoint1.x,
            mRightBottomPoint1.y,
            mRightBottomPoint2.x,
            mRightBottomPoint2.y,
            mCenterPoint.x + R * control - 1f * control,
            mCenterPoint.y + R
        )
        mPaint.style = Paint.Style.FILL
        mPaint.color = if (control == 1) mFontColor else mBackColor
        canvas.drawPath(mPath, mPaint)
    }

    /**
     * 执行动画
     */
    private fun postAnim() {
        postDelayed({
            moveCenter()
            backAnim()
            invalidate()
        }, POSITION)
    }

    /**
     * 背景圆滑动
     */
    private fun moveCenter() {
        val times = (mCenterPoint.y / COR_PRO + 0.5f).toInt() * 4 - K * 2
        mCenterPoint.x += COR * 2 / times
    }

    /**
     * 背景变化
     */
    private fun backAnim() {
        if (mCenterPoint.y - R <= 0f && RT >= 0f) {
            if (mControl == -1 && RT == 0f) {
                RT = -0.0001f
            } else {
                RT += COR_PRO * mControl
            }
        } else {
            R += COR_PRO * mControl
        }
        mControl = if (RT == R) -mControl else mControl
        //转完一次完整周期
        if (R < COR) {
            //下一次循环初始化
            R = COR
            RT = 0f
            mControl = 1
            swapColor()
            initPoint()
        }
        updateControlPoint(mControl)
    }

    /**
     * 颜色交换
     */
    private fun swapColor() {
        val tempColor = mFontColor
        mFontColor = mBackColor
        mBackColor = tempColor
    }

}