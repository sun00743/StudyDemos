package com.mika.demo.study.livedata.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.mika.demo.study.R
import kotlinx.android.synthetic.main.simple_fragment.*

class SimpleFragment : Fragment() {

    companion object {
        fun newInstance() = SimpleFragment()
    }

    private val viewModel: SimpleViewModel by viewModels { ViewModelProvider.NewInstanceFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.simple_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        set_result.setOnClickListener {
            setFragmentResult("demo_result", Bundle().apply {
                putString("value", "mmimsaidmamkads")
                Log.d("mika_fragment", "set result finish")
            })
        }
    }

}