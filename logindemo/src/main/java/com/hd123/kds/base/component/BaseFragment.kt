package com.hd123.kds.base.component

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hd123.kds.widget.ToastHolder

open class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    open fun onCurrent() {
    }

    open fun onLeave() {
    }

}