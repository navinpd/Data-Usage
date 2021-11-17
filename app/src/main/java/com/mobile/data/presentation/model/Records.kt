package com.mobile.data.presentation.model

import com.mobile.data.presentation.viewmodel.QUARTER

internal class Records(
    val id: Int,
    val year: Int,
    val volumeRecords: Double,
    val quarter: QUARTER
)