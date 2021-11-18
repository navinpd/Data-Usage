package com.mobile.data.presentation.viewmodel

import android.os.Parcelable
import androidx.lifecycle.*
import com.mobile.data.data.remote.model.mobileData.DataModel
import com.mobile.data.data.remote.repository.DataRepository
import com.mobile.data.presentation.mapper.AnnualResultMapper
import com.mobile.data.presentation.mapper.DataResultDomainMapper
import com.mobile.data.presentation.model.AnnualRecord
import com.mobile.data.presentation.model.Records
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
internal class UsedDataViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val dataResultDomainMapper: DataResultDomainMapper,
    private val annualResultMapper: AnnualResultMapper,
) : ViewModel() {

    var records: List<Records> = mutableListOf()

    private val dataState = MutableLiveData<DataViewState>()
    val dataViewState: LiveData<DataViewState>
        get() = dataState

    fun getQuarterlyData(year: String): List<Records> {
        val localRecords = mutableListOf<Records>()
        records.forEach {
            if (it.year == year.substring(year.length - 4, year.length)) {
                localRecords.add(it)
            }
        }
        return localRecords
    }

    fun updateUsedDataFromRepository(dataModel: DataModel) {
        hideLoading()
        if (dataModel.throwable == null) {
            val dataResult = dataResultDomainMapper.map(dataModel.dataApiModel!!)
            records = dataResult.result.records
            val annualRecords = annualResultMapper.map(records)
            onDataRetrieved(annualRecords)
        } else {
            onDataFetchFailed(dataModel.throwable!!)
        }
    }

    fun requestUsedData() {
        if(records.isEmpty()) {
            showLoading()
            dataRepository.getMobileData(this)
        }
    }

    private fun showLoading() {
        dataState.value = DataViewState.ShowLoading
    }

    private fun hideLoading() {
        dataState.value = DataViewState.HideLoading
    }

    private fun onDataRetrieved(records: List<AnnualRecord>) {
        dataState.postValue(DataViewState.ShowData(records))
    }

    private fun onDataFetchFailed(throwable: Throwable) {
        dataState.value = DataViewState.ShowError(throwable.message!!)
    }
}

internal sealed class DataViewState {
    object ShowLoading : DataViewState()
    object HideLoading : DataViewState()
    data class ShowError(val message: String) : DataViewState()
    data class ShowData(val show: List<AnnualRecord>) : DataViewState()
}

internal sealed class QUARTER : Parcelable {
    @Parcelize
    data class QUARTER_1(var int: Int) : QUARTER()

    @Parcelize
    data class QUARTER_2(var int: Int) : QUARTER()

    @Parcelize
    data class QUARTER_3(var int: Int) : QUARTER()

    @Parcelize
    data class QUARTER_4(var int: Int) : QUARTER()
}