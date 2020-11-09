package com.mika.nestedscrolldemo.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.NestedScrollingChild

/**
 * Created by mika on 2019/8/26.
 * 简单的标题随内容滚动渐显或渐隐behavior
 */
class SimpleTitleBehavior: CoordinatorLayout.Behavior<View> {

    constructor()

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    private var distanceY = 0f

    /**
     * behavior 执行者 确定依赖于coordinator得哪一个子view
     */
    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
//        return super.layoutDependsOn(parent, child, dependency)
        return dependency is NestedScrollingChild
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        //当依赖的view 变化时， 在此方法执行behavior的变化

        //随着向上滚动，动态dy变小，反之亦然
        var currentDy = dependency.y - child.height
        //初始值为0，是最初状态，为初始dy赋值
        if (distanceY == 0f) {
            distanceY = currentDy
        }
        if (currentDy < 0f) {
            currentDy = 0f
        }
        //随着动态dy变小，逐渐显示
        child.alpha = 1f - (currentDy / distanceY)
        return true
    }

}