package com.mika.dagger.function

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Created by mika on 2019/10/13.
 */
@Module
class MsgModule(private val activity: DaggerDemoActivity) {

//    @Inject
//    lateinit var demoName: String

    @Provides
    fun msgViewModel(factory: MsgViewModelFactory): MsgViewModel {
        return ViewModelProviders.of(activity, factory)[MsgViewModel::class.java]
    }

    /**
     * 相同类型provide 使用named区分，并且要clean project重新make module
     */
    @Provides
//    @Named("msgName")
    fun provideMsgName(): String = "mika demo"

    @Provides
    @Named("msgName2")
    fun provideMsgName2(): String = "mika demo 2"

    @Provides
    fun provideMsgId(): Int = 120


}