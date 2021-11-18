package com.mobile.common

import com.google.gson.Gson
import java.lang.Exception

class JsonParser {

    fun <T> parse(jsonString: String, clazz: Class<T>): T? =
        try {
            Gson().fromJson(jsonString, clazz)
        } catch (e: Exception) {
            null
        }
}