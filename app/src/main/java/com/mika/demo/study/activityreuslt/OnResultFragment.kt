package com.mika.demo.study.activityreuslt

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Created by mika on 2020/11/5.
 */
class OnResultFragment: Fragment() {

    var mListener: OnActivityResultListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View(activity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startActivityForResult(Intent(activity, arguments?.getSerializable("cls") as Class<*>), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mListener?.onResult(requestCode, data)
    }

}