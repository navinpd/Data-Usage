package com.mobile.data.data.remote.repository

import android.app.Application
import android.util.Log
import com.mobile.common.AssetFileLoader
import com.mobile.common.JsonParser
import com.mobile.data.R
import com.mobile.data.data.remote.NetworkService
import com.mobile.data.data.remote.model.mobileData.DataApiModel
import com.mobile.data.data.remote.model.mobileData.DataModel
import com.mobile.data.presentation.viewmodel.UsedDataViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileNotFoundException
import javax.inject.Inject

internal class DataRepository @Inject constructor(
    private val networkService: NetworkService,
    private val jsonParser: JsonParser,
    private val assetFileLoader: AssetFileLoader,
    private val application: Application,
) {

    private lateinit var usedDataViewModel: UsedDataViewModel

    companion object {
        const val IS_CACHED_RESPONSE = false
        const val DATA_JSON = "report.json"
        const val TAG = "DataRepository"
    }

    fun getMobileData(usedDataViewModel: UsedDataViewModel) {
        if (IS_CACHED_RESPONSE) {
            try {
                val jsonService = assetFileLoader.loadFileAsStream(application, DATA_JSON)
                    .bufferedReader()
                    .readText()
                usedDataViewModel.updateUsedDataFromRepository(
                    DataModel(
                        jsonParser
                            .parse(jsonService, DataApiModel::class.java), null
                    )
                )
                return
            } catch (exception: FileNotFoundException) {
                Log.e(TAG, exception.message!!)
            }
        }

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
                    usedDataViewModel.updateUsedDataFromRepository(
                        DataModel(
                            null,
                            Throwable(
                                application.getString(
                                    R.string.error_in_response,
                                    response.code()
                                )
                            )
                        )
                    )
                }
            }

            override fun onFailure(call: Call<DataApiModel>, t: Throwable) {
                usedDataViewModel.updateUsedDataFromRepository(DataModel(null, t))
            }
        })
    }

}