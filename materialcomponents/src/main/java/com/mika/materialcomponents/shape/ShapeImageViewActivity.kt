package com.mika.materialcomponents.shape

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.shape.*
import com.mika.materialcomponents.R
import com.mika.materialcomponents.intToDp
import kotlinx.android.synthetic.main.activity_shape_image_view.*

class ShapeImageViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shape_image_view)

        val shapeModel = ShapeAppearanceModel.Builder()
//                .setAllCorners(RoundedCornerTreatment())
                .setAllCorners(CornerFamily.ROUNDED, intToDp(8))
                .setAllEdges(TriangleEdgeTreatment(intToDp(16), true))
                .build()
        textView.background = MaterialShapeDrawable(shapeModel).apply {
            setTint(Color.BLUE)
            paintStyle = Paint.Style.FILL

            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
            initializeElevationOverlay(this@ShapeImageViewActivity)
            shadowRadius = 48
            setShadowColor(Color.BLACK)
        }


        val shapeModel2 = ShapeAppearanceModel.Builder()
                .setAllCorners(CornerFamily.ROUNDED, intToDp(8))
                .setRightEdge(OffsetEdgeTreatment(TriangleEdgeTreatment(intToDp(16), false), 40f))
                .build()
        container.clipChildren = false
        image.background = MaterialShapeDrawable(shapeModel2).apply {
            strokeWidth = intToDp(2)
            strokeColor = ColorStateList.valueOf(Color.YELLOW)
            setTint(Color.LTGRAY)
            paintStyle = Paint.Style.FILL_AND_STROKE

            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_DEFAULT
            initializeElevationOverlay(this@ShapeImageViewActivity)
            shadowRadius = 48
            setShadowColor(Color.BLUE)
        }
    }

}