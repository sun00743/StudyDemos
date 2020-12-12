package com.mika.materialcomponents.shape

import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath

/**
 * Created by mika on 2020/11/21.
 */
class MikaDemoEdgeTreatment : EdgeTreatment() {

    override fun getEdgePath(length: Float, center: Float, interpolation: Float, shapePath: ShapePath) {

//        shapePath.lineTo(40f, -20f)

        //内偏移曲线带有两个锚点
        shapePath.cubicToPoint(48f, -24f, 88f, 64f, 106f, 0f)

        //外偏移曲线
        shapePath.quadToPoint(126f, -40f, 200f, 80f)

//        shapePath.lineTo(length, 0f)
    }

}