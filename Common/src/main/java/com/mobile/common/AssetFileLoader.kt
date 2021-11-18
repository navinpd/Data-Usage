package com.mobile.common

import android.content.Context
import java.io.InputStream

class AssetFileLoader {
    fun loadFileAsStream(context: Context, filePath: String): InputStream {
        return context.assets.open(filePath)
    }
}