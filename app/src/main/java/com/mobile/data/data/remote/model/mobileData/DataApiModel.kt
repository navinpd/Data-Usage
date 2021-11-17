package com.mobile.data.data.remote.model.mobileData

import com.google.gson.annotations.SerializedName
import java.io.Serializable


internal class DataApiModel(

    @SerializedName("help") val help: String,
    @SerializedName("success") val success: Boolean,
    @SerializedName("result") val resultApiModel: ResultApiModel

) : Serializable