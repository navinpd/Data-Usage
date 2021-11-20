package com.mobile.data.util

import android.app.Application
import androidx.annotation.StringRes
import javax.inject.Inject

class StringLocalizerImpl @Inject constructor(
    private val application: Application
) : StringLocalizer {
    override fun getString(@StringRes id: Int): String {
        return application.getString(id)
    }

    override fun getString(id: Int, double: Double): String {
        return application.getString(id, double)
    }
    override fun getString(id: Int, string: String): String {
        return application.getString(id, string)
    }
}
