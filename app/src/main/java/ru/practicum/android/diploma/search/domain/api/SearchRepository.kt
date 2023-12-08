package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.models.VacancySearchParams
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.SearchedVacancy

interface SearchRepository {

    fun searchVacancy(vacancySearchParams: VacancySearchParams): Flow<Resource<List<SearchedVacancy>>>
}
