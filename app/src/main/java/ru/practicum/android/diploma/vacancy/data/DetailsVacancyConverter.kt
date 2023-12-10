package ru.practicum.android.diploma.vacancy.data

import ru.practicum.android.diploma.vacancy.data.vacancyDto.DetailVacancyDto
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

class DetailsVacancyConverter {

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
            salaryTo = this.salary?.to.toString(),
            salaryFrom = this.salary?.from.toString(),
            salaryCurrency = this.salary?.currency,
            description = this.description,
            keySkills = this.keySkills,
            contactPerson = this.contacts?.name,
            email = this.contacts?.email,
            telephone = this.phone.number,
            comment = this.phone.comment
        )
    }
