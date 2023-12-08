package ru.practicum.android.diploma.search.domain.models

data class SearchedVacancy(
    val id: Int,
    val img: String,
    val vacancy: String,
    val area: String,
    val category: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currency: String
)
