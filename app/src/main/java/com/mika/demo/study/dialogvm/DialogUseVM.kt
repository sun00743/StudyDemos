package com.mika.demo.study.dialogvm

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.mika.demo.study.R
import kotlinx.android.synthetic.main.dialog_use_vm.*

class DialogUseVM(context: Context): AppCompatDialog(context) {

    private val mViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(DialogViewModel::class.java)
    }

    init {
        setContentView(R.layout.dialog_use_vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel.startTimer()

        editText.requestFocus()
    }

    override fun dismiss() {
        super.dismiss()
    }

}