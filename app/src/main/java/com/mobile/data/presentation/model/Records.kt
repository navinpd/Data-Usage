package com.mobile.data.presentation.model

import android.os.Parcelable
import com.mobile.data.presentation.viewmodel.QUARTER
import kotlinx.parcelize.Parcelize

@Parcelize
internal class Records(
    val id: Int,
    val year: String,
    val volumeRecords: Double,
    val quarter: QUARTER,
) : Parcelable