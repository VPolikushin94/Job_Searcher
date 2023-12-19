package ru.practicum.android.diploma.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun formatNumber(number: Int): String {
    val dec = DecimalFormat("###,###,###,###,###", DecimalFormatSymbols(Locale.getDefault()))

    return dec.format(number).replace(",", " ")
}
