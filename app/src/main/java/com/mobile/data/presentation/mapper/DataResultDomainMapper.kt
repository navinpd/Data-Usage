package com.mobile.data.presentation.mapper

import com.mobile.common.Mapper
import com.mobile.data.data.remote.model.mobileData.DataApiModel
import com.mobile.data.presentation.model.DataResult


internal class DataResultDomainMapper constructor(
    private val resultDomainMapper: ResultDomainMapper
) : Mapper<DataApiModel, DataResult> {

    override fun map(from: DataApiModel): DataResult {
        val result = resultDomainMapper.map(from.resultApiModel)
        return DataResult(
            help = from.help,
            success = from.success,
            result = result
        )
    }
}