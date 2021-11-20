package com.mobile.data.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class Records(
    val id: Int,
    val year: String,
    val volumeRecords: Double,
    val quarter: Int,
) : Parcelable