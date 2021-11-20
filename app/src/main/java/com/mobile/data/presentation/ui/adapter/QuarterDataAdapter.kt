package com.mobile.data.presentation.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mobile.data.presentation.model.Records
import com.mobile.data.presentation.ui.fragment.SlideScreenCardFragment
import com.mobile.data.util.PageNotifier

internal class QuarterDataAdapter(fragmentActivity: FragmentActivity,
                                  val records: List<Records>)
    : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = records.size

    override fun createFragment(position: Int): Fragment {
        return SlideScreenCardFragment.getInstance(records[position])
    }
}