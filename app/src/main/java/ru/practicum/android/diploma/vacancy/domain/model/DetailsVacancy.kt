package ru.practicum.android.diploma.vacancy.domain.model

data class DetailsVacancy(
    val id: Int,
    val name: String?,
    val employerId: String,
    val employerName: String?,
    val employerLogo: String?,
    val employerCity: String?,
    val employmentId: Int,
    val employmentName: String?,
    val experienceId: Int,
    val experienceName: String?,
    val salaryTo: Int?,
    val salaryFrom: Int?,
    val salaryCurrency: String?,
    val description: String?,
    val keySkills: String?,
    val contactPerson: String?,
    val email: String?,
    val telephone: String?,
    val comment: String?,
    val url: String?
)
