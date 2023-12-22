package ru.practicum.android.diploma.filter.domain.converter

import ru.practicum.android.diploma.core.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.core.models.SearchedVacancy

class VacancyDbMapper {
    fun map(vacancyEntity: VacancyEntity): SearchedVacancy {
        return SearchedVacancy(
            id = vacancyEntity.id.toInt(),
            img = vacancyEntity.employerLogo,
            vacancy = vacancyEntity.name.toString(),
            area = vacancyEntity.employerCity.toString(),
            employerName = vacancyEntity.employerName.toString(),
            salaryFrom = vacancyEntity.salaryFrom,
            salaryTo = vacancyEntity.salaryTo,
            currency = vacancyEntity.salaryCurrency.toString()
        )
    }
}
