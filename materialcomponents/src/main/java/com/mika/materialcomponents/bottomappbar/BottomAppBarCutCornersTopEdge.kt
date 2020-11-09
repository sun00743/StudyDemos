package com.mika.materialcomponents.bottomappbar

import android.annotation.SuppressLint
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment
import com.google.android.material.shape.ShapePath

/**
 * Created by mika on 2020/10/21.
 */
class BottomAppBarCutCornersTopEdge(fabMargin: Float, roundedCornerRadius: Float, cradleVerticalOffset: Float)
    : BottomAppBarTopEdgeTreatment(fabMargin, roundedCornerRadius, cradleVerticalOffset) {

    private var fabMargin = 0f
    private var cradleVerticalOffset = 0f

    init {
        this.fabMargin = fabMargin
        this.cradleVerticalOffset = cradleVerticalOffset
    }

    @SuppressLint("RestrictedApi")
    override fun getEdgePath(length: Float, center: Float, interpolation: Float, shapePath: ShapePath) {
        val fabDiameter = fabDiameter
        if (fabDiameter == 0f) {
            shapePath.lineTo(length, 0f)
            return
        }
        val diamondSize = fabDiameter / 2f
        val middle = center + horizontalOffset
        val verticalOffsetRatio = cradleVerticalOffset / diamondSize
        if (verticalOffsetRatio >= 1.0f) {
            shapePath.lineTo(length, 0f)
            return
        }
        shapePath.lineTo(middle - (fabMargin + diamondSize - cradleVerticalOffset), 0f)
        shapePath.lineTo(middle, (diamondSize - cradleVerticalOffset + fabMargin) * interpolation)
        shapePath.lineTo(middle + (fabMargin + diamondSize - cradleVerticalOffset), 0f)
        shapePath.lineTo(length, 0f)
    }

}