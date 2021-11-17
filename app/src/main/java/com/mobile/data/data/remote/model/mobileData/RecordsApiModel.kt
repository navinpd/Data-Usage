package com.mobile.data.data.remote.model.mobileData


import com.google.gson.annotations.SerializedName
import java.io.Serializable


internal class RecordsApiModel(

    @SerializedName("volume_of_mobile_data") val volumeOfMobileData: String,
    @SerializedName("quarter") val quarter: String,
    @SerializedName("_id") val Id: Int

) : Serializable