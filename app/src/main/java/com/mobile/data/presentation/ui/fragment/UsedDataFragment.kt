package com.mobile.data.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.mobile.data.R
import com.mobile.data.databinding.DataUsedFragmentBinding
import com.mobile.data.presentation.model.AnnualRecord
import com.mobile.data.presentation.ui.adapter.AnnualViewAdapter
import com.mobile.data.presentation.viewmodel.DataViewState
import com.mobile.data.presentation.viewmodel.UsedDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class UsedDataFragment :
    BaseFragment<DataUsedFragmentBinding>(R.layout.data_used_fragment) {

    @Inject
    lateinit var viewModel: UsedDataViewModel
    lateinit var annualViewAdapter: AnnualViewAdapter
    private var listOfRecords = mutableListOf<AnnualRecord>()

    companion object {
        fun newInstance() = UsedDataFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        annualViewAdapter = AnnualViewAdapter(listOfRecords)
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
                    listOfRecords.addAll(value.show)

                    annualViewAdapter.notifyDataSetChanged()

                    Toast.makeText(this.context, "Successfully received data", Toast.LENGTH_LONG)
                        .show()
                }

                is DataViewState.ShowError -> {
                    Toast.makeText(this.context, value.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel.requestUsedData()
    }

}