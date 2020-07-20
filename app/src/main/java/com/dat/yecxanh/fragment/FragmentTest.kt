package com.dat.yecxanh.fragment

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dat.yecxanh.R
import com.dat.yecxanh.base.BaseFragment
import kotlinx.android.synthetic.main.test_layout_fragment.*

class FragmentTest : BaseFragment() {

    private var testViewModel: TestViewModel? = null

    override fun getLayoutId(): Int {
        return R.layout.test_layout_fragment
    }

    override fun bindView(view: View?) {
        testViewModel = ViewModelProviders.of(this).get(TestViewModel::class.java)
        // testViewModel!!.text.observe(this, Observer { s -> button.text = s })
    }

}