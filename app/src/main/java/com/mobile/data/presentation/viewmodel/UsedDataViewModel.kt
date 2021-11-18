package com.mobile.data.presentation.viewmodel

import androidx.lifecycle.*
import com.mobile.data.data.remote.model.mobileData.DataModel
import com.mobile.data.data.remote.repository.DataRepository
import com.mobile.data.presentation.mapper.DataResultDomainMapper
import com.mobile.data.presentation.model.DataResult
import com.mobile.data.presentation.model.Records
import javax.inject.Inject

internal class UsedDataViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val dataResultDomainMapper: DataResultDomainMapper,
) : ViewModel() {

    private val dataState = MutableLiveData<DataViewState>()
    val dataViewState: LiveData<DataViewState>
        get() = dataState

    fun updateUsedDataFromRepository(dataModel: DataModel) {
        hideLoading()
        if (dataModel.throwable == null) {
            val dataResult = dataResultDomainMapper.map(dataModel.dataApiModel!!)
            onDataRetrieved(dataResult.result.records)
        } else {
            onDataFetchFailed(dataModel.throwable!!)
        }
    }

    fun requestUsedData() {
        showLoading()
        dataRepository.getMobileData(this)
    }

    private fun showLoading() {
        dataState.postValue(DataViewState.ShowLoading)
    }

    private fun hideLoading() {
        dataState.postValue(DataViewState.HideLoading)
    }

    private fun onDataRetrieved(records: List<Records>) {
        dataState.postValue(DataViewState.ShowData(records))
    }

    private fun onDataFetchFailed(throwable: Throwable) {
        dataState.postValue(DataViewState.ShowError(throwable.message!!))
    }
}

internal sealed class DataViewState {
    object ShowLoading : DataViewState()
    object HideLoading : DataViewState()
    data class ShowError(val message: String) : DataViewState()
    data class ShowData(val show: List<Records>) : DataViewState()
}

internal sealed class QUARTER {
    object QUARTER_1 : QUARTER()
    object QUARTER_2 : QUARTER()
    object QUARTER_3 : QUARTER()
    object QUARTER_4 : QUARTER()
}