package com.mika.materialcomponents.shape

import android.util.Log
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath

/**
 * Created by mika on 2020/11/21.
 */
class MikaEdgeTreatment : EdgeTreatment() {

    var arcRadius = 0f

    override fun getEdgePath(length: Float, center: Float, interpolation: Float, shapePath: ShapePath) {
//        super.getEdgePath(length, center, interpolation, shapePath)

//        val radius = length * interpolation

        // lineTo：以边的顶点（顺时针）为起点，链接每一个lineTo方法的坐标
        // X: 正值顺时针，负值相反
        // Y: 正值向内，负值向外
//        shapePath.lineTo(center - 48f, 0f)

        //正常的弧形要求top和bottom值对应，例：top：-48f, bottom: 48f
        shapePath.addArc(center - arcRadius, -arcRadius, center + arcRadius, arcRadius, 180f, 180f)
//        shapePath.lineTo(length, 0f)
//        shapePath.lineTo(64f, -48f)
//        shapePath.lineTo(length, 0f)
//        shapePath.addArc(
//                center - radius, -radius,
//                center + radius, radius,
//                180f,
//                180f
//        )
//        shapePath.lineTo(length, 0f)


//        shapePath.quadToPoint(center, length * interpolation, length, 0f)

        Log.d("mika_shape", "length: $length, center: $center, interpolation: $interpolation, shapePath: $shapePath")
    }

}