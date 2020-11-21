package com.mika.materialcomponents.shape

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.google.android.material.shape.*
import com.mika.materialcomponents.R
import com.mika.materialcomponents.intToDp
import kotlinx.android.synthetic.main.activity_shape_image_view.*

class ShapeImageViewActivity : AppCompatActivity() {

    private val mikaEdgeTreatment = MikaEdgeTreatment()

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
//                .setRightEdge(OffsetEdgeTreatment(TriangleEdgeTreatment(intToDp(16), false), 40f))
                .setAllEdges(mikaEdgeTreatment)
                .build()
        container.clipChildren = false
        val drawable = MaterialShapeDrawable(shapeModel2).apply {
            strokeWidth = intToDp(2)
            strokeColor = ColorStateList.valueOf(Color.YELLOW)
            setTint(Color.LTGRAY)
            paintStyle = Paint.Style.FILL_AND_STROKE

            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_DEFAULT
            initializeElevationOverlay(this@ShapeImageViewActivity)
            shadowRadius = 48
            setShadowColor(Color.BLUE)
        }
        image.background = drawable

        image.setOnClickListener {
            val ofFloat = ValueAnimator.ofFloat(0f, 36f)
            ofFloat.addUpdateListener {
                Log.d("mika_shape", "${it.animatedValue}")
                val animatedValue = it.animatedValue
                mikaEdgeTreatment.arcRadius = animatedValue as Float
                drawable.shapeAppearanceModel = shapeModel2
                image.postInvalidate()
            }
            ofFloat.start()
        }
    }

}