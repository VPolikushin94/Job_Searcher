package ru.practicum.android.diploma.vacancy.data

import ru.practicum.android.diploma.vacancy.data.details.DetailVacancyDto
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

fun DetailVacancyDto.mapToDetailsVacancy(): DetailsVacancy =
    DetailsVacancy(
        id = this.id.toInt(),
        name = this.name,
        employerId = this.employer.id,
        employerName = this.employer.name,
        employerLogo = this.employer.logoUrls.toString(),
        employerCity = this.area.name,
        employmentId = this.employment.id.toInt(),
        employmentName = this.employment.name,
        experienceId = this.experience.id.toInt(),
        experienceName = this.experience.name,
        salaryTo = this.salary?.to,
        salaryFrom = this.salary?.from,
        salaryCurrency = this.salary?.currency,
        description = this.description,
        keySkills = mapKeySkillToString(this.keySkills),
        contactPerson = this.contacts?.name,
        email = this.contacts?.email,
        telephone = this.phone.number,
        comment = this.phone.comment,
        url = this.url
    )

private fun mapKeySkillToString(skill: String?): String {
    return if (skill.isNullOrEmpty()) {
        ""
    } else {
        val marker = "• "
        val enter = "\n"
        marker + skill + enter
    }
}
