package ru.practicum.android.diploma.core.models

data class Vacancy(
    val id: Int,
    val img: String?,
    val vacancy: String,
    val area: String,
    val employerName: String,
    val salary: String
)
