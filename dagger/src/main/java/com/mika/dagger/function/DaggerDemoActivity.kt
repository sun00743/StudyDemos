package com.mika.dagger.function

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.mika.dagger.R
import javax.inject.Inject

class DaggerDemoActivity : AppCompatActivity(), LifecycleOwner {

    @Inject
    lateinit var viewModel: MsgViewModel

    @Inject
//    @Named("msgName")
    lateinit var baseString: String

    private lateinit var lifecycleRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dagger_demo)

        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        inject()

        viewModel.getCurrentMsg().observe(this, Observer {
            Log.d("mika", it.content)
//            Log.d("mika", baseString)
        })

        viewModel.getCurrentMsg().value = Msg("dddddddd", 1)
    }

    private fun inject() {
        DaggerMsgComponent.builder()
                .msgModule(MsgModule(this))
                .build()
                .initActivity(this)
////                .msgModule()
//                .build()
//                .initActivity(this)
//        DaggerMsgComponent.builder()
//                .baseComponent(DApplication.baseComponent(this))
//                .msgModule(MsgModule(this))
//                .build()
//                .inject(this)
    }

    override fun onStart() {
        super.onStart()
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry
}
