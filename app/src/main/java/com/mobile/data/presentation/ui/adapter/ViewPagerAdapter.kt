package com.mobile.data.presentation.ui.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mobile.data.presentation.model.Records
import com.mobile.data.presentation.ui.fragment.ScreenSlidePageFragment

internal class ViewPagerAdapter(fragmentActivity: FragmentActivity, val records: List<Records>)
    : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = records.size

    override fun createFragment(position: Int): Fragment {
        return ScreenSlidePageFragment.getInstance(records[position])
    }
}