package com.mobile.data.presentation.model

import com.mobile.data.presentation.viewmodel.QUARTER

internal class Records(
    val id: Int,
    val year: String,
    val volumeRecords: Double,
    val quarter: QUARTER
)