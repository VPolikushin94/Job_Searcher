package ru.practicum.android.diploma.vacancy.data.vacancyDto

data class DetailVacancyDto(
    val id: String,
    val name: String,
    val description: String?,
    val keySkills: String?,
    val area: Area,
    val employer: Employer,
    val employment: Employment,
    val experience: Experience,
    val salary: Salary?,
    val contacts: Contacts?,
    val phone: Phone
)
