package com.mobile.data.presentation.mapper

import com.mobile.common.Mapper
import com.mobile.data.data.remote.model.mobileData.DataApiModel
import com.mobile.data.data.remote.model.mobileData.FieldsApiModel
import com.mobile.data.presentation.model.Fields


internal class FieldsDomainMapper constructor() : Mapper<FieldsApiModel, Fields> {
    override fun map(from: FieldsApiModel): Fields {
        return Fields(
            type = from.type,
            id = from.id
        )
    }
}