package com.mika.materialcomponents.topappbar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import com.mika.materialcomponents.R
import kotlinx.android.synthetic.main.top_app_bar_normal.*

class TopAppBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        setContentView(R.layout.activity_top_app_bar)


//        setSupportActionBar(findViewById(R.id.toolbar))
//        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }


        //appbar ActionMode 回调
        val callback = getActionModeCallBack()

        //menu点击事件
        top_app_bar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search -> {
                    val actionMode = startSupportActionMode(callback)
                    actionMode?.title = "1 selected"
                    true
                }
                else -> {
                    false
                }
            }
        }

        var i = 2
        fun upstate() {
            i = 3
        }

    }

    /**
     * appbar ActionMode 回调
     */
    private fun getActionModeCallBack(): ActionMode.Callback {
        return object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menuInflater.inflate(R.menu.menu_top_app_bar_action, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return when (item?.itemId) {
                    R.id.action_refresh -> {
                        // Handle more item (inside overflow menu) press
                        true
                    }
                    else -> false
                }
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
            }
        }
    }


}