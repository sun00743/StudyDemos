package com.mika.dagger.function

import com.mika.dagger.util.FeatureScope
import dagger.Component

/**
 * Created by mika on 2019/10/13.
 */
@Component(
        modules = [MsgModule::class]
//        , dependencies = [BaseComponent::class]
)
@FeatureScope
interface MsgComponent {

    @Component.Builder
    interface Builder{
//        fun baseComponent(baseComponent: BaseComponent): Builder
        fun msgModule(msgModule: MsgModule): Builder

        fun build(): MsgComponent
    }

//    @Named("msgName")
//    fun getSomeString(): String

    fun initActivity(activity: DaggerDemoActivity)

}