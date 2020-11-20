package com.mika.materialcomponents

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.shape.MaterialShapeDrawable
import com.mika.materialcomponents.botoomnavigation.BottomNavigationActivity
import com.mika.materialcomponents.bottomappbar.BottomAppBarCutCornersTopEdge
import com.mika.materialcomponents.shape.ShapeImageViewActivity
import com.mika.materialcomponents.topappbar.TopAppBarActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottom_app_bar)

        (bottomAppBar.background as MaterialShapeDrawable).apply {
            //custom top edge
            val topEdge = BottomAppBarCutCornersTopEdge(
                    bottomAppBar.fabCradleMargin,
                    bottomAppBar.fabCradleRoundedCornerRadius,
                    bottomAppBar.cradleVerticalOffset
            )
            shapeAppearanceModel = shapeAppearanceModel.toBuilder()
                    .setTopEdge(topEdge)
                    .build()
        }


        //kotlin包里有静态类持有成员变量，
        var i = 1
        go_top_app_bar.setOnClickListener {
            goActivity(TopAppBarActivity::class.java)
            i = 2
        }

        go_bottom_navigation.setOnClickListener {
            goActivity(BottomNavigationActivity::class.java)
        }

        go_chip.setOnClickListener {
            goActivity(ChipsActivity::class.java)
        }

    }

    fun goShapeImageView(view: View) {
        goActivity(ShapeImageViewActivity::class.java)
    }


}