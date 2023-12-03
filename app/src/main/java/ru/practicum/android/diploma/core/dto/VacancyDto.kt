package ru.practicum.android.diploma.core.dto

data class VacancyDto(
    val id: String,
    val name: String?,
    val employer: EmployerDto,
    val areaDto: AreasDto,
    val salary: SalaryDto
)

data class SalaryDto(
    val currency: String?,
    val from: Int?,
    val gross: Boolean,
    val to: Int?
)

data class AreasDto(
    val id: String,
    val name: String?
)

data class EmployerDto(
    val id: String,
    val name: String?,
    val url: String?,
    val logo_urls: Map<String, String?>
)
