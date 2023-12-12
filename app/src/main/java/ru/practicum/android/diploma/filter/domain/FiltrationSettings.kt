package ru.practicum.android.diploma.filter.domain

data class FiltrationSettings(
    val country: Area?,
    val region: Area?,
    val industry: Industry?,
    val salary: Int,
    val fSalaryRequired: Boolean = false
)
