package com.mobile.data.util

import androidx.annotation.StringRes

interface StringLocalizer {
    fun getString(@StringRes id: Int): String

    fun getString(@StringRes id: Int, double: Double): String

    fun getString(@StringRes id: Int, string: String): String
}
