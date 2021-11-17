package com.mobile.data.presentation.mapper

import com.mobile.common.Mapper
import com.mobile.data.data.remote.model.mobileData.ResultApiModel
import com.mobile.data.presentation.model.Fields
import com.mobile.data.presentation.model.Records
import com.mobile.data.presentation.model.Result


internal class ResultDomainMapper constructor(
    private val fieldsDomainMapper: FieldsDomainMapper,
    private val recordsDomainMapper: RecordsDomainMapper,
    private val linksDomainMapper: LinksDomainMapper
) : Mapper<ResultApiModel, Result> {
    override fun map(from: ResultApiModel): Result {
        val field = mutableListOf<Fields>()
        val records = mutableListOf<Records>()
        for (i in from.fields) {
            fieldsDomainMapper.map(i).let { field.add(it) }
        }
        for (i in from.records) {
            recordsDomainMapper.map(i).let { records.add(it) }
        }

        return linksDomainMapper.map(from.LinksApiModel).let {
            Result(
                resourceId = from.resourceId,
                fields = field,
                records = records,
                Links = it,
                total = from.total
            )
        }
    }
}