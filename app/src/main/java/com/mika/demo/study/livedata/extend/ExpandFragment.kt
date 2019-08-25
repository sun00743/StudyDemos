package com.mika.demo.study.livedata.extend

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.core.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mika.demo.study.R
import kotlinx.android.synthetic.main.fragment_expand.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ExpandFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_expand, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        StockLiveData.get(10).observe(this, Observer {
//            Log.d("mika", "StockLiveData: onUpdate: " + it?.num.toString())
//            expand_text.text = it?.num.toString()
//        })

        @Suppress("UNCHECKED_CAST")
        val viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repository = DataRepository()
                return TransformationsViewModel(repository) as T
            }
        })[TransformationsViewModel::class.java]

        viewModel.data.observe(this, Observer {
            Log.d("mika", "TransformationsViewModel_Observer: onUpdate: " + it?.num.toString())
//            expand_text.text = it?.num.toString()
        })

        var keyData = 100
        expand_text.setOnClickListener {
            viewModel.setKeyData(keyData++)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExpandFragment.
         */
        @JvmStatic
        fun newInstance(param1: String?, param2: String?) =
                ExpandFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
