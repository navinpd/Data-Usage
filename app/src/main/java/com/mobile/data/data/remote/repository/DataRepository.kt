package com.mobile.data.data.remote.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mobile.data.data.remote.NetworkService
import com.mobile.data.data.remote.model.mobileData.DataApiModel
import com.mobile.data.data.remote.model.mobileData.DataModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class DataRepository constructor(
    private val networkService: NetworkService,
) {

    val mobileLiveData = MutableLiveData<DataModel>()

    fun getMobileData() {
        val usedData = networkService.getDataUsage()

        usedData.enqueue(object : Callback<DataApiModel> {
            override fun onResponse(
                call: Call<DataApiModel>,
                response: Response<DataApiModel>,
            ) {

                if (!response.isSuccessful || response.code() != 200) {
                    mobileLiveData.value = DataModel(null, Throwable("Error in network response ${response.code()}"))
                } else {
                    mobileLiveData.value = DataModel(response.body(), null)
                }
            }

            override fun onFailure(call: Call<DataApiModel>, t: Throwable) {
                mobileLiveData.value = DataModel(null, t)
            }
        })
    }

}