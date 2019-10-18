package com.mika.dagger

import dagger.Component
import javax.inject.Singleton

/**
 * Created by mika on 2019/10/13.
 */
@Component(modules = [BaseDataModule::class])
@Singleton
interface BaseComponent {

    @Component.Builder
    interface Builder {
        fun build(): BaseComponent
    }

//    @Named("demoName")
    fun getDemoName(): String

//    fun injectApp(application: Application)

}