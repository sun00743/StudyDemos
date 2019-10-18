package com.mika.dagger.function

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by mika on 2019/10/15.
 */

class MsgViewModelFactory

@Inject
constructor(private val name: Msg)
    :ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MsgViewModel(name.content) as T
    }

}