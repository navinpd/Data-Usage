package com.mobile.data.presentation.mapper

import com.mobile.common.Mapper
import com.mobile.common.format
import com.mobile.data.presentation.model.AnnualRecord
import com.mobile.data.presentation.model.Records
import javax.inject.Inject

internal class AnnualResultMapper @Inject constructor() :
    Mapper<List<Records>, List<AnnualRecord>> {
    override fun map(from: List<Records>): List<AnnualRecord> {
        val annualRecords = mutableListOf<AnnualRecord>()
        var year = ""
        var currentSum = 0.0

        for (record in from) {
            if (year == "" || currentSum == 0.0) {
                year = record.year
                currentSum = record.volumeRecords
            } else if (record.year == year) {
                currentSum += record.volumeRecords
            } else {
                val annualRecord = AnnualRecord("Annual Year: $year",
                    "Used Data: ${currentSum.format(5)}")
                annualRecords.add(annualRecord)
                year = record.year
                currentSum = record.volumeRecords
            }
        }
        if(year != "") {
            val annualRecord = AnnualRecord("Annual Year: $year",
                "Used Data: ${currentSum.format(5)}")
            annualRecords.add(annualRecord)
        }
        return annualRecords
    }
}