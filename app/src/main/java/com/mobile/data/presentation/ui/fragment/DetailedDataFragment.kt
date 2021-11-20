package com.mobile.data.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.mobile.common.dpToPx
import com.mobile.data.R
import com.mobile.data.databinding.DetailedDataFragmentBinding
import com.mobile.data.presentation.model.Records
import com.mobile.data.presentation.ui.adapter.QuarterDataAdapter
import com.mobile.data.presentation.viewmodel.UsedDataViewModel
import com.mobile.data.util.PageNotifier
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
internal class DetailedDataFragment :
    BaseFragment<DetailedDataFragmentBinding>(R.layout.detailed_data_fragment) {

    private val viewModel by viewModels<UsedDataViewModel>()

    @Inject
    lateinit var pageNotifier: PageNotifier
    private lateinit var onPageChangeCallback: ViewPager2.OnPageChangeCallback

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
        return inflater.inflate(
            R.layout.detailed_data_fragment,
            container, false
        )
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        val listItems = mutableListOf<Records>()
        if (arguments != null) {
            val year = requireArguments().getString(YEAR)
            if (!year.isNullOrEmpty()) {
                listItems.addAll(viewModel.getQuarterlyData(year))
                pageNotifier.logSelectedYear(getString(R.string.selected_year, year))
            }
        }

        onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                pageNotifier.movedToPage(getString(R.string.moved_to_page, position))
            }
        }

        val pagerAdapter = QuarterDataAdapter(this.requireActivity(), listItems)
        viewBinding.run {
            viewPager.adapter = pagerAdapter
            viewPager.setPageTransformer(MarginPageTransformer(32.dpToPx(viewPager)))
            viewPager.registerOnPageChangeCallback(onPageChangeCallback)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        pageNotifier.orientationChanged(getString(R.string.orientation_changed))
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding.viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }

    override fun bindView(view: View) = DetailedDataFragmentBinding.bind(view)
}