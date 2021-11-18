package com.mobile.data.presentation.ui.adapter.viewholder

import com.mobile.data.databinding.AnnualDataViewAdapterBinding
import com.mobile.data.presentation.model.AnnualRecord
import com.mobile.data.presentation.model.Records

internal class AnnualViewViewHolder(
    private val binding: AnnualDataViewAdapterBinding,
) : BaseViewHolder(binding.root) {

    fun bindView(result: AnnualRecord) {
        binding.run {
            annualYear.text = result.year
            totalUsedData.text = result.volumeRecord.toString()
        }
    }

}