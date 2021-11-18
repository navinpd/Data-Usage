package com.mobile.data.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mobile.data.R
import com.mobile.data.presentation.model.Records

internal class ScreenSlidePageFragment() : Fragment() {

    companion object {
        const val RECORD = "ScreenSlidePageFragment.RECORD"

        fun getInstance(records: Records): ScreenSlidePageFragment {
            val screenSlidePageFragment = ScreenSlidePageFragment()
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
        val view = inflater.inflate(R.layout.view_pager_adapter, container, false)
        if (arguments != null) {
            val records = requireArguments().get(RECORD) as Records
            view.findViewById<TextView>(R.id.quarter_data).text = records.volumeRecords.toString()
            view.findViewById<TextView>(R.id.quarter_number).text = records.quarter.javaClass.simpleName
        }

        return view
    }

}