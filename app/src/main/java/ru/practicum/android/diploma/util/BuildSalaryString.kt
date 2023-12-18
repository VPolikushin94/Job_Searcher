package ru.practicum.android.diploma.util

import android.app.Application
import ru.practicum.android.diploma.R

fun buildSalaryString(
    salaryFrom: Int?,
    salaryTo: Int?,
    currency: String,
    context: Application
): String {
    return buildString {
        if (salaryFrom == null && salaryTo == null) {
            this.append(context.getString(R.string.no_salary))
        } else {
            salaryFrom?.let {
                val tmp = context.getString(R.string.salary_from, formatNumber(salaryFrom))
                this.append("$tmp ")
            }
            salaryTo?.let {
                val tmp = context.getString(R.string.salary_to, formatNumber(salaryTo))
                this.append("$tmp ")
            }
            val tmp = when (currency) {
                context.getString(R.string.rur) -> context.getString(R.string.ruble)
                context.getString(R.string.eur) -> context.getString(R.string.euro)
                context.getString(R.string.usd) -> context.getString(R.string.dollar)
                else -> currency
            }
            this.append(tmp)
        }
    }
}
