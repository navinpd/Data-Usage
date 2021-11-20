package com.mobile.data

import com.mobile.data.data.remote.model.mobileData.*
import com.mobile.data.presentation.model.*


internal object DataRelatedTestData {

    private val linkApiModel = LinksApiModel(
        start = "/api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f",
        next = "/api/action/datastore_search?offset=100&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f"
    )

    private val fieldsApiModel = FieldsApiModel(
        type = "int4",
        id = "_id"
    )

    private val recordsApiModel = RecordsApiModel(
        volumeOfMobileData = "2.33",
        quarter = "2013-Q1",
        Id = 59
    )

    val resultApiModel = ResultApiModel(
        resourceId = "a807b7ab-6cad-4aa6-87d0-e283a7353a0f",
        fields = arrayListOf(fieldsApiModel),
        records = arrayListOf(recordsApiModel),
        LinksApiModel = linkApiModel,
        total = 1
    )

    val dataApiModel = DataApiModel(
        help = "https://data.gov.sg/api/3/action/help_show?name=datastore_search",
        success = true,
        resultApiModel = resultApiModel
    )

    private val links = Links(
        start = "/api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f",
        next = "/api/action/datastore_search?offset=100&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f"
    )

    private val fields = Fields(
        type = "int4",
        id = "_id"
    )

    private val records =  Records(
        id = 59,
        year = "2013",
        volumeRecords = 2.33,
        quarter = 2
    )

    val result = com.mobile.data.presentation.model.Result(
        resourceId = "a807b7ab-6cad-4aa6-87d0-e283a7353a0f",
        fields = arrayListOf(fields),
        records = arrayListOf(records),
        Links = links,
        total = 1
    )

    val dataResult = DataResult(
        help = "https://data.gov.sg/api/3/action/help_show?name=datastore_search",
        success = true,
        result = result
    )

    val jsonRecords = "[{\"id\":1,\"quarter\":3,\"volumeRecords\":0.000384,\"year\":\"2004\"},{\"id\":2,\"quarter\":4,\"volumeRecords\":0.000543,\"year\":\"2004\"},{\"id\":3,\"quarter\":1,\"volumeRecords\":20.53504752,\"year\":\"2019\"}]"

}