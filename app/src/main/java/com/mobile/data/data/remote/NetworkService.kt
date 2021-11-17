package com.mobile.data.data.remote

import com.mobile.data.data.remote.model.mobileData.DataApiModel
import retrofit2.Call
import retrofit2.http.GET

internal interface NetworkService {

    @GET("https://data.gov.sg/api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f")
    fun getDataUsage() : Call<DataApiModel>

}