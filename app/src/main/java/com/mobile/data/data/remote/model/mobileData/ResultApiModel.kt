package com.mobile.data.data.remote.model.mobileData


import com.google.gson.annotations.SerializedName
import java.io.Serializable


internal class ResultApiModel(

    @SerializedName("resource_id") val resourceId: String,
    @SerializedName("fields") val fields: List<FieldsApiModel>,
    @SerializedName("records") val records: List<RecordsApiModel>,
    @SerializedName("_links") val LinksApiModel: LinksApiModel,
    @SerializedName("total") val total: Int

) : Serializable