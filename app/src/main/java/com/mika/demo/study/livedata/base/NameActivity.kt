package com.mika.demo.study.livedata.base

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.mika.demo.study.R
import com.mika.demo.study.livedata.extend.ExpandFragment

import kotlinx.android.synthetic.main.activity_name.*

class NameActivity : AppCompatActivity() {

    private lateinit var mModel: NameViewModel
    private var count : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            count ++
            mModel.getCurrentName().value = "mika_$count"
        }

        mModel = ViewModelProviders.of(this)[NameViewModel::class.java]

        mModel.getCurrentName().observe(this, Observer<String> {
            Log.d("mika", "currentName: $it")
        })


        supportFragmentManager.beginTransaction()
                .add(R.id.livedata_expand_container, ExpandFragment.newInstance(null, null))
                .commit()
    }



}
