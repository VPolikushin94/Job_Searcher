package ru.practicum.android.diploma.vacancy.domain.model

data class DetailsVacancy(
    val id: String,
    val name: String,
    val employerId: String?,
    val employerName: String?,
    val employerLogo: String?,
    val employerCity: String?,
    val employmentId: String?,
    val employmentName: String?,
    val experienceId: String?,
    val experienceName: String?,
    val salaryTo: String?,
    val salaryFrom: String?,
    val salaryCurrency: String?,
    val address: String?,
    val description: String?,
    val keySkills: String?,
    val contactPerson: String?,
    val eMail: String?,
    val telephone: String?,
    val comment: String?
)
