package com.mika.dagger

import dagger.Module
import dagger.Provides

/**
 * Created by mika on 2019/10/13.
 */
@Module
class BaseDataModule {

    @Provides
//    @Named("demoName")
    fun provideName(): String = "dagger demo manager"

}