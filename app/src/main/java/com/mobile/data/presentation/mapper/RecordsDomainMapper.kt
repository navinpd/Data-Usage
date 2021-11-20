package com.mobile.data.presentation.mapper

import com.mobile.common.Mapper
import com.mobile.common.getLastChar
import com.mobile.common.getYear
import com.mobile.data.data.remote.model.mobileData.RecordsApiModel
import com.mobile.data.presentation.model.Records
import javax.inject.Inject


internal class RecordsDomainMapper @Inject
constructor() : Mapper<RecordsApiModel, Records> {
    override fun map(from: RecordsApiModel): Records {

        return Records(
            id = from.Id,
            year = from.quarter.getYear(),
            quarter = from.quarter.getLastChar().toInt(),
            volumeRecords = from.volumeOfMobileData.toDouble()
        )
    }
}




