package com.mobile.data.data.remote.model.mobileData


import com.google.gson.annotations.SerializedName
import java.io.Serializable


internal class FieldsApiModel(

    @SerializedName("type") val type: String,
    @SerializedName("id") val id: String

) : Serializable