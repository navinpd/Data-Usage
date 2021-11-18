package com.mobile.data.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.data.databinding.AnnualDataViewAdapterBinding
import com.mobile.data.presentation.model.AnnualRecord
import com.mobile.data.presentation.ui.adapter.viewholder.AnnualViewViewHolder
import com.mobile.data.presentation.ui.adapter.viewholder.BaseViewHolder

internal class AnnualViewAdapter constructor(
    private val usedData: MutableList<AnnualRecord> = mutableListOf(),
) : RecyclerView.Adapter<BaseViewHolder>() {

    var onClickListener: View.OnClickListener? = null
//        set() = onClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return AnnualViewViewHolder(AnnualDataViewAdapterBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val positionItem = usedData[position]
        when (holder) {
            is AnnualViewViewHolder -> {
                holder.bindView(positionItem, onClickListener)
            }
        }
    }

    override fun getItemCount() = usedData.size

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}