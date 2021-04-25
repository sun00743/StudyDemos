package com.mika.demo.study.livedata.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mika.demo.study.R
import com.mika.demo.study.livedata.extend.ExpandFragment
import kotlinx.android.synthetic.main.activity_name.*

class NameActivity : AppCompatActivity() {

    private val mModel: NameViewModel by viewModels { ViewModelProvider.NewInstanceFactory() }
    private var count : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            count ++
            mModel.getCurrentName().value = "mika_$count"
        }

        lifecycle.addObserver(mModel)

        mModel.getCurrentName().observe(this, Observer<String> {
            Log.d("mika", "currentName: $it")
        })

        val expand = ExpandFragment.newInstance(null, null)

        supportFragmentManager.beginTransaction()
                .add(R.id.livedata_expand_container, expand)
                .commit()

//        lifecycleRegistry.currentState = Lifecycle.State.CREATED

        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra("key_mika", "this is result !")
        })

/*
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({

            supportFragmentManager.beginTransaction()
                    .replace(R.id.livedata_expand_container, SimpleFragment.newInstance())
                    .commit()
        }, 2000)

        handler.postDelayed({

            supportFragmentManager.beginTransaction()
                    .replace(R.id.livedata_expand_container, expand)
                    .commit()
        }, 5000)
*/
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
