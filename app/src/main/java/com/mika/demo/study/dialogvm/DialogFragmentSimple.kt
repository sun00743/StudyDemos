package com.mika.demo.study.dialogvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.mika.demo.study.R

class DialogFragmentSimple: DialogFragment() {

//    private val mViewModel by lazy {
//        ViewModelProvider.NewInstanceFactory().create(DialogViewModel::class.java)
//    }

    private val mViewModel: DialogViewModel by viewModels { ViewModelProvider.NewInstanceFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_use_vm, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.use()
    }

}