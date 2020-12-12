package com.mika.demo.study

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mika.demo.study.activityreuslt.OnActivityResultListener
import com.mika.demo.study.activityreuslt.OnResultFragment
import com.mika.demo.study.livedata.base.NameActivity
import com.mika.demo.study.video.VideoActivity
import com.mika.requester.Connector
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

    //这个放在baseActivity
    private val mActivityResultListenerHolder = OnResultFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val okHttpClient = OkHttpClient.Builder()
                //...
                .build()
        Connector.init(okHttpClient)

        button.setOnClickListener {
            startNameActivityForResult()
        }

        go_video.setOnClickListener {
            this.startActivity(Intent(this, VideoActivity::class.java))
        }

    }

    private fun startNameActivityForResult() {
        mActivityResultListenerHolder.mListener = object : OnActivityResultListener {
            override fun onResult(requestCode: Int, data: Intent?) {
                if (supportFragmentManager.findFragmentByTag("result") != null) {
                    supportFragmentManager.beginTransaction().remove(mActivityResultListenerHolder).commit()
                }

                Log.d("mika_demo", "onActivityResult: " + data?.toString())

                textView.text = data?.getStringExtra("key_mika")
            }
        }
        mActivityResultListenerHolder.arguments = Bundle().apply {
            putSerializable("cls", NameActivity::class.java)
        }

//        if (supportFragmentManager.findFragmentByTag("result") != null) {
//            supportFragmentManager.beginTransaction().remove(mActivityResultListenerHolder).commitNow()
//        }

        supportFragmentManager.beginTransaction().add(mActivityResultListenerHolder, "result").commit()
//        this@MainActivity.startActivityForResult()

        //上面代码封装一下
    }

}
