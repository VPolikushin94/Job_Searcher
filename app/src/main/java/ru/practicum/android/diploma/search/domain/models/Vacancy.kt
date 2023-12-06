package ru.practicum.android.diploma.search.domain.models

data class Vacancy(
    val id: Int,
    val img: String,
    val vacancyAndArea: String?,
    val category: String?,
    val salary: String?
)
