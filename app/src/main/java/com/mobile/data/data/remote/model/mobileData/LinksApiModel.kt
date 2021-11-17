package com.mobile.data.data.remote.model.mobileData


import com.google.gson.annotations.SerializedName
import java.io.Serializable


internal class LinksApiModel(

    @SerializedName("start") val start: String,
    @SerializedName("next") val next: String

) : Serializable