package com.mobile.data.presentation.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mobile.data.data.remote.model.mobileData.DataModel
import com.mobile.data.data.remote.repository.DataRepository
import com.mobile.data.presentation.mapper.AnnualResultMapper
import com.mobile.data.presentation.mapper.DataResultDomainMapper
import com.mobile.data.presentation.model.AnnualRecord
import com.mobile.data.presentation.model.Records
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class UsedDataViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val dataResultDomainMapper: DataResultDomainMapper,
    private val annualResultMapper: AnnualResultMapper,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val LOCAL_DATA_KEY = "LOCAL_DATA"
    private val dataState = MutableLiveData<DataViewState>()

    var records: List<Records> = mutableListOf()
    val dataViewState: LiveData<DataViewState>
        get() = dataState

    fun getQuarterlyData(year: String): List<Records> {
        if(year.length < 4)
            return emptyList()

        val localRecords = mutableListOf<Records>()

        val models = getLocalRecords()

        models.forEach {
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
            val model = Gson().toJson(records)

            sharedPreferences.edit().putString(LOCAL_DATA_KEY, model).apply()

            val annualRecords = annualResultMapper.map(records)
            onDataRetrieved(annualRecords)
        } else {
            if (dataModel.throwable!!.message?.startsWith("Unable to resolve host") == true) {
                val models = getLocalRecords()
                onDataRetrieved(annualResultMapper.map(models))
            } else {
                onDataFetchFailed(dataModel.throwable!!)
            }
        }
    }

    fun getLocalRecords(): List<Records> {
        val data = sharedPreferences.getString(LOCAL_DATA_KEY, "")
        if(data.isNullOrBlank())
            return emptyList()
        return GsonBuilder().create().fromJson(data, Array<Records>::class.java).toList()
    }

    fun requestUsedData() {
        if (records.isEmpty()) {
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