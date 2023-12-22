package ru.practicum.android.diploma.core.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String?,
    val employerId: String,
    val employerName: String?,
    val employerLogo: String?,
    val employerCity: String?,
    val employmentId: String?,
    val employmentName: String?,
    val experienceId: String?,
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
