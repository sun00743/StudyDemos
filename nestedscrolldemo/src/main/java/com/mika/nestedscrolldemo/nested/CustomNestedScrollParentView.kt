package com.mika.nestedscrolldemo.nested

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent2

/**
 * Created by mika on 2019/8/3.
 */
class CustomNestedScrollParentView : LinearLayout, NestedScrollingParent2 {

    private var mHead: View? = null

    private var mHeadHeight = 0


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : super(context, attr)

    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int)
            : this(context, attr, defStyleAttr,0)

    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attr, defStyleAttr, defStyleRes)

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) {
            mHead = getChildAt(0)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mHeadHeight = mHead?.measuredHeight ?: 0
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mHead?.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
        val height = MeasureSpec.getSize(heightMeasureSpec) + (mHead?.measuredHeight ?: 0)
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, mode))
    }


    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return true
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(target, dx, dy, consumed)

        val headerScrollUp = dy > 0 && scrollY < mHeadHeight
        val headerScrollDown = dy < 0 && scrollY > 0 && !target.canScrollVertically(-1)
        if (headerScrollUp || headerScrollDown) {
            scrollBy(0, dy)
            consumed[1] = dy
        }
    }

    override fun onStopNestedScroll(target: View, type: Int) {
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
    }

    override fun scrollTo(x: Int, y: Int) {
        var sy = y
        if (y < 0) {
            sy = 0
        } else if (y > mHeadHeight) {
            sy = mHeadHeight
        }

        super.scrollTo(x, sy)
    }

}