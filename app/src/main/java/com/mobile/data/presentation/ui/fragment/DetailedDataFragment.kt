package com.mobile.data.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.MarginPageTransformer
import com.mobile.common.dpToPx
import com.mobile.data.R
import com.mobile.data.databinding.DetailedDataFragmentBinding
import com.mobile.data.presentation.model.Records
import com.mobile.data.presentation.ui.adapter.QuarterDataAdapter
import com.mobile.data.presentation.viewmodel.UsedDataViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
internal class DetailedDataFragment :
    BaseFragment<DetailedDataFragmentBinding>(R.layout.detailed_data_fragment) {

    private val viewModel by viewModels<UsedDataViewModel>()

    companion object {
        const val YEAR = "DetailedDataFragment.YEAR"

        fun newInstance(year: String): DetailedDataFragment {
            val detailedDataFragment = DetailedDataFragment()
            val bundle = Bundle()
            bundle.putString(YEAR, year)
            detailedDataFragment.arguments = bundle
            return detailedDataFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.detailed_data_fragment,
            container, false)
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        val listItems = mutableListOf<Records>()
        if (arguments != null) {
            val detailInfo = requireArguments().getString(YEAR)
            if (!detailInfo.isNullOrEmpty()) {
                listItems.addAll(viewModel.getQuarterlyData(detailInfo))
            }
        }

        var pagerAdapter = QuarterDataAdapter(this.requireActivity(), listItems)
        viewBinding.run {
            viewPager.adapter = pagerAdapter
            viewPager.setPageTransformer(MarginPageTransformer(32.dpToPx(viewPager)))
        }
    }

    override fun bindView(view: View) = DetailedDataFragmentBinding.bind(view)
}