package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.SearchVacancyResult

interface SearchInteractor {

    fun searchVacancy(searchText: String, page: Int, perPage: Int): Flow<Resource<SearchVacancyResult>>

    suspend fun areFiltersChanged(): Boolean

    suspend fun areFiltersEmpty(): Boolean
}
