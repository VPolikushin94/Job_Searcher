package ru.practicum.android.diploma.favorites.data

import ru.practicum.android.diploma.core.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

class FavouritesDbMapper {

    fun mapDetails(data: DetailsVacancy): VacancyEntity {
        return VacancyEntity(
            id = data.id.toString(),
            name = data.name,
            employerId = data.employerId,
            employerName = data.employerName,
            employerLogo = data.employerLogo,
            employerCity = data.employerCity,
            employmentId = data.employmentId,
            employmentName = data.employmentName,
            experienceId = data.experienceId,
            experienceName = data.experienceName,
            salaryTo = data.salaryTo,
            salaryFrom = data.salaryFrom,
            salaryCurrency = data.salaryCurrency,
            description = data.description,
            keySkills = data.keySkills,
            contactPerson = data.contactPerson,
            email = data.email,
            telephone = data.telephone,
            comment = data.comment,
            url = data.url
        )
    }

    fun mapEntity(data: VacancyEntity): DetailsVacancy {
        return DetailsVacancy(
            id = data.id.toInt(),
            name = data.name,
            employerId = data.employerId,
            employerName = data.employerName,
            employerLogo = data.employerLogo,
            employerCity = data.employerCity,
            employmentId = data.employmentId,
            employmentName = data.employmentName,
            experienceId = data.experienceId,
            experienceName = data.experienceName,
            salaryTo = data.salaryTo,
            salaryFrom = data.salaryFrom,
            salaryCurrency = data.salaryCurrency,
            description = data.description,
            keySkills = data.keySkills,
            contactPerson = data.contactPerson,
            email = data.email,
            telephone = data.telephone,
            comment = data.comment,
            url = data.url
        )
    }
}
