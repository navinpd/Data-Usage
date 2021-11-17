package com.mobile.data.presentation.model

internal data class Result(
    val resourceId: String,
    val fields: List<Fields>,
    val records: List<Records>,
    val Links: Links,
    val total: Int
)