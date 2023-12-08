package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.SearchedVacancy

interface SearchInteractor {

    fun searchVacancy(text: String): Flow<Resource<List<SearchedVacancy>>>
}
