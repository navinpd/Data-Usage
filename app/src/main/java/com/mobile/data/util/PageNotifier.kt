package com.mobile.data.util

import android.util.Log
import javax.inject.Inject

class PageNotifier @Inject constructor() {
    private val TAG : String = this.javaClass.simpleName

    fun movedToPage(message: String) {
        Log.d(TAG, message)
    }

    fun orientationChanged(message: String) {
        Log.d(TAG, message)
    }

    fun logSelectedYear(message: String) {
        Log.d(TAG, message)
    }

}