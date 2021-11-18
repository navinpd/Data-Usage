package com.mobile.data.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mobile.data.R
import com.mobile.data.databinding.ViewPagerAdapterBinding
import com.mobile.data.presentation.model.Records

internal class SlideScreenCardFragment() :
    BaseFragment<ViewPagerAdapterBinding>(R.layout.view_pager_adapter) {

    companion object {
        const val RECORD = "ScreenSlidePageFragment.RECORD"

        fun getInstance(records: Records): SlideScreenCardFragment {
            val screenSlidePageFragment = SlideScreenCardFragment()
            val bundle = Bundle()
            bundle.putParcelable(RECORD, records)
            screenSlidePageFragment.arguments = bundle
            return screenSlidePageFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.view_pager_adapter, container, false)
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments != null) {
            val records = requireArguments().get(RECORD) as Records
            viewBinding.run {
                quarterData.text = records.volumeRecords.toString()
                quarterNumber.text = records.quarter.javaClass.simpleName
            }
        }
    }

    override fun bindView(view: View) = ViewPagerAdapterBinding.bind(view)

}