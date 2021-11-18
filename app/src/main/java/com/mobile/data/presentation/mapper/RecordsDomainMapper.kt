package com.mobile.data.presentation.mapper

import com.mobile.common.Mapper
import com.mobile.common.getLastChar
import com.mobile.common.getYear
import com.mobile.data.data.remote.model.mobileData.RecordsApiModel
import com.mobile.data.presentation.model.*
import com.mobile.data.presentation.model.Records
import com.mobile.data.presentation.viewmodel.QUARTER
import javax.inject.Inject


internal class RecordsDomainMapper @Inject
constructor() : Mapper<RecordsApiModel, Records> {
    override fun map(from: RecordsApiModel): Records {
        val currentQuarter: QUARTER = when (from.quarter.getLastChar()) {
            "1" -> QUARTER.QUARTER_1
            "2" -> QUARTER.QUARTER_2
            "3" -> QUARTER.QUARTER_3
            "4" -> QUARTER.QUARTER_4
            else -> QUARTER.QUARTER_1
        }

        return Records(
            id = from.Id,
            year = from.quarter.getYear(),
            quarter = currentQuarter,
            volumeRecords = from.volumeOfMobileData.toDouble()
        )
    }
}




