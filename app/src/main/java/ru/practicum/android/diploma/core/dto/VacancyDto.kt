package ru.practicum.android.diploma.core.dto

data class VacancyDto(
    val vacancyId: String,
    val name: String,
    val employer: String,
    val area: String,
    val salaryFrom: String,
    val salaryTo: String,
    val currency: String
)
