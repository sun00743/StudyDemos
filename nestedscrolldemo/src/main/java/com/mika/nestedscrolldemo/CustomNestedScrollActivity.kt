package com.mika.nestedscrolldemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_custom_nested_scroll.*

class CustomNestedScrollActivity : AppCompatActivity() {

    companion object {
        fun starter(context: Activity) {
            context.startActivity(Intent(context, CustomNestedScrollActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_nested_scroll)

        scroll_child_recycler.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val itemView = TextView(this@CustomNestedScrollActivity)
                val params = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120)
                itemView.layoutParams = params
                return object :RecyclerView.ViewHolder(itemView) {}
            }

            override fun getItemCount(): Int = 50

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                (holder.itemView as? TextView)?.let {
                    it.text = position.toString()
                }
            }
        }
    }
}
