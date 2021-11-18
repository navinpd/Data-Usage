package com.mobile.common

import java.text.DecimalFormat


fun String.getLastChar(): String {
    val length = this.length
    val lastCharItem = this.toCharArray()[length - 1]
    return lastCharItem.toString()
}

fun String.getYear(): String {
    return this.substring(0, 4)
}

fun Double.format(fracDigits: Int): String {
    val df = DecimalFormat()
    df.setMaximumFractionDigits(fracDigits)
    return df.format(this)
}