package ru.practicum.android.diploma.filter.domain.models

data class FiltrationSettings(
    val industry: Industry?,
    val salary: String,
    val salaryOnly: Boolean = false
)
