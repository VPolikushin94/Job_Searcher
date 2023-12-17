package ru.practicum.android.diploma.core.db.dbConverter

import ru.practicum.android.diploma.core.db.entity.VacancyEntity
import ru.practicum.android.diploma.core.models.SearchedVacancy

class VacancyDbMapper {
    fun map(vacancyEntity: VacancyEntity): SearchedVacancy {
        return SearchedVacancy(
            id = vacancyEntity.id.toInt(),
            img = vacancyEntity.employerLogo,
            vacancy = vacancyEntity.name,
            area = vacancyEntity.employerCity.toString(),
            employerName = vacancyEntity.employmentName.toString(),
            salaryFrom = vacancyEntity.salaryFrom,
            salaryTo = vacancyEntity.salaryTo,
            currency = vacancyEntity.salaryCurrency.toString()
        )
    }
}
