package com.mobile.common


fun String.getLastChar(): String {
    val length = this.length
    val lastCharItem = this.toCharArray()[length - 1]
    return lastCharItem.toString()
}

fun String.getYear(): Int {
    return this.substring(0, 4).toInt()
}