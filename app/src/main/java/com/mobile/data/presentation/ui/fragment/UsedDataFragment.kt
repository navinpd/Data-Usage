package com.mobile.data.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mobile.data.R
import com.mobile.data.databinding.DataUsedFragmentBinding
import com.mobile.data.presentation.model.AnnualRecord
import com.mobile.data.presentation.ui.activities.UsedDataActivity
import com.mobile.data.presentation.ui.adapter.AnnualViewAdapter
import com.mobile.data.presentation.viewmodel.DataViewState
import com.mobile.data.presentation.viewmodel.UsedDataViewModel
import com.mobile.data.util.StringLocalizer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class UsedDataFragment :
    BaseFragment<DataUsedFragmentBinding>(R.layout.data_used_fragment) {

    @Inject
    lateinit var stringLocalizer: StringLocalizer

    private val viewModel by viewModels<UsedDataViewModel>()
    lateinit var annualViewAdapter: AnnualViewAdapter
    private var listOfRecords = mutableListOf<AnnualRecord>()

    companion object {
        fun newInstance() = UsedDataFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        annualViewAdapter =
            AnnualViewAdapter(usedData = listOfRecords, stringLocalizer = stringLocalizer)
        viewModel.requestUsedData()
        return inflater.inflate(R.layout.data_used_fragment, container, false)
    }

    override fun bindView(view: View) = DataUsedFragmentBinding.bind(view)

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.run {
            usedDataRecyclerView.adapter = annualViewAdapter
        }
        viewModel.dataViewState.observe(this, Observer { value ->
            when (value) {
                is DataViewState.ShowLoading ->
                    viewBinding.progressBar.isVisible = true

                is DataViewState.HideLoading -> {
                    viewBinding.progressBar.isVisible = false
                }

                is DataViewState.ShowData -> {
                    listOfRecords.clear()
                    if (value.show.isEmpty()) {
                        viewBinding.errorMessage.text = getString(R.string.no_internet_error)
                    } else {
                        listOfRecords.addAll(value.show)

                        annualViewAdapter.notifyDataSetChanged()
                        annualViewAdapter.onClickListener = selectItemClickListener
                    }
                }

                is DataViewState.ShowError -> {
                    Toast.makeText(this.context, value.message, Toast.LENGTH_LONG).show()
                    viewBinding.errorMessage.text = value.message
                }
            }
        })
    }

    private val selectItemClickListener =
        View.OnClickListener { view ->
            val selectedYear = view.tag as String
            Toast.makeText(
                this.context,
                getString(R.string.selected_year, selectedYear),
                Toast.LENGTH_LONG
            ).show()

            (activity as UsedDataActivity).launchDetailedYearView(selectedYear)
        }

}