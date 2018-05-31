package com.mika.demo.study

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mika.demo.study.livedata.NameActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            this@MainActivity.startActivity(Intent(this@MainActivity, NameActivity::class.java))
        }
    }


}
