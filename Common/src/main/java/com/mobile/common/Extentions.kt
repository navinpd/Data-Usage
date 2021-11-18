package com.mobile.common

import android.util.TypedValue
import androidx.viewpager2.widget.ViewPager2
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

fun Int.dpToPx(viewPager : ViewPager2): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        viewPager.resources.displayMetrics
    ).toInt()
}