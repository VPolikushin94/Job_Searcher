package ru.practicum.android.diploma.vacancy.data

import ru.practicum.android.diploma.vacancy.data.details.Contacts
import ru.practicum.android.diploma.vacancy.data.details.DetailVacancyDto
import ru.practicum.android.diploma.vacancy.data.details.KeySkills
import ru.practicum.android.diploma.vacancy.data.details.Phone
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

fun DetailVacancyDto.mapToDetailsVacancy(): DetailsVacancy =
    DetailsVacancy(
        id = this.id.toInt(),
        name = this.name,
        employerId = this.employer.id,
        employerName = this.employer.name,
        employerLogo = this.employer.logoUrls?.original,
        employerCity = this.area.name,
        employmentId = this.employment.id,
        employmentName = this.employment.name,
        experienceId = this.experience.id,
        experienceName = this.experience.name,
        salaryTo = this.salary?.to,
        salaryFrom = this.salary?.from,
        salaryCurrency = this.salary?.currency,
        description = this.description,
        keySkills = this.keySkills?.mapKeySkillToString(),
        contactPerson = this.contacts?.name,
        email = this.contacts?.email,
        telephone = getTelephone(this.contacts?.phone),
        comment = getComment(this.contacts),
        url = this.url
    )

private fun List<KeySkills>?.mapKeySkillToString(): String? {
    return if (this.isNullOrEmpty()) {
        null
    } else {
        val marker = "  •   "
        val enter = "\n"
        this.joinToString("") { marker + it.name + enter }
    }
}

private fun getTelephone(phone: List<Phone>?): String {
    return if (phone == null) {
        ""
    } else {
        phone.first().country +
            phone.first().city +
            phone.first().number
    }
}

private fun getComment(contacts: Contacts?): String? {
    return if (contacts?.phone == null) {
        ""
    } else {
        contacts.phone.first().comment
    }
}
