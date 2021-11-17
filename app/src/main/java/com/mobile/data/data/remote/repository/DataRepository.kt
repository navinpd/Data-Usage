package com.mobile.data.data.remote.repository

import com.mobile.data.data.remote.NetworkService
import com.mobile.data.data.remote.model.mobileData.DataApiModel
import com.mobile.data.data.remote.model.mobileData.DataModel
import com.mobile.data.presentation.viewmodel.UsedDataViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class DataRepository constructor(
    private val networkService: NetworkService,
) {

    private lateinit var usedDataViewModel: UsedDataViewModel

    fun getMobileData(usedDataViewModel: UsedDataViewModel) {
        this.usedDataViewModel = usedDataViewModel
        val usedData = networkService.getDataUsage()

        usedData.enqueue(object : Callback<DataApiModel> {
            override fun onResponse(
                call: Call<DataApiModel>,
                response: Response<DataApiModel>,
            ) {

                if (response.isSuccessful && response.code() == 200) {
                    usedDataViewModel.updateUsedDataFromRepository(DataModel(response.body(), null))
                } else {
                    usedDataViewModel.updateUsedDataFromRepository(DataModel(null,
                        Throwable("Error in network response ${response.code()}")))
                }
            }

            override fun onFailure(call: Call<DataApiModel>, t: Throwable) {
                usedDataViewModel.updateUsedDataFromRepository(DataModel(null, t))
            }
        })
    }

}