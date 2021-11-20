package com.mobile.data.presentation.ui.adapter.viewholder

import android.view.View
import com.mobile.data.R
import com.mobile.data.databinding.AnnualDataViewAdapterBinding
import com.mobile.data.presentation.model.AnnualRecord
import com.mobile.data.presentation.model.Records
import com.mobile.data.util.StringLocalizer

internal class AnnualViewViewHolder(
    private val binding: AnnualDataViewAdapterBinding,
    private val stringLocalizer: StringLocalizer
) : BaseViewHolder(binding.root) {

    fun bindView(result: AnnualRecord, onClickListener: View.OnClickListener?) {
        binding.run {
            annualYear.text = stringLocalizer.getString(R.string.annual_year, result.year)
            totalUsedData.text = stringLocalizer.getString(R.string.data_used, result.volumeRecord.toDouble())
        }
        itemView.tag = result.year
        itemView.setOnClickListener(onClickListener)
    }

}