package com.mika.demo.study.livedata.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
        lifecycle.addObserver(mModel)

        mModel.getCurrentName().observe(this, Observer<String> {
            Log.d("mika", "currentName: $it")
        })

        supportFragmentManager.beginTransaction()
                .add(R.id.livedata_expand_container, ExpandFragment.newInstance(null, null))
                .commit()

//        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    override fun onStart() {
        super.onStart()
//        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    override fun onDestroy() {
        super.onDestroy()
//        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

//    override fun getLifecycle(): Lifecycle = lifecycleRegistry


}
