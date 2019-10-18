package com.mika.nestedscrolldemo.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.NestedScrollingChild

/**
 * Created by mika on 2019/8/26.
 */
class SimpleTitleBehavior: CoordinatorLayout.Behavior<View> {

    constructor()

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    private var distanceY = 0f

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
//        return super.layoutDependsOn(parent, child, dependency)
        return dependency is NestedScrollingChild
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        var currentDy = dependency.y - child.height
        if (distanceY == 0f) distanceY = currentDy
        if (currentDy < 0f) currentDy = 0f
        child.alpha = 1f - (currentDy / distanceY)
        return true
    }

}