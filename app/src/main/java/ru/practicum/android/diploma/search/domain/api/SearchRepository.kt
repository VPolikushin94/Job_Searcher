package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.models.SearchedVacancy
import ru.practicum.android.diploma.core.models.VacancySearchParams
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.SearchVacancyResult

interface SearchRepository {

    fun searchVacancy(vacancySearchParams: VacancySearchParams): Flow<Resource<SearchVacancyResult>>

    suspend fun getCachedVacancySearchResult(): SearchVacancyResult

    suspend fun cacheVacancyList(vacancyList: List<SearchedVacancy>)
}
