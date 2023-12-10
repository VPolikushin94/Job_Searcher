package ru.practicum.android.diploma.search.data.convertor

import ru.practicum.android.diploma.core.dto.VacancyDto
import ru.practicum.android.diploma.search.domain.models.SearchedVacancy

class VacancyDtoConvertor {
    fun map(vacancyDto: VacancyDto): SearchedVacancy {
        return SearchedVacancy(
            vacancyDto.id.toInt(),
            vacancyDto.employer?.logoUrls?.get(LOGO_SIZE),
            vacancyDto.name ?: "",
            vacancyDto.area?.name ?: "",
            vacancyDto.employer?.name ?: "",
            vacancyDto.salary?.from,
            vacancyDto.salary?.to,
            vacancyDto.salary?.currency ?: ""
        )
    }

    companion object {
        private const val LOGO_SIZE = "240"
    }
}
