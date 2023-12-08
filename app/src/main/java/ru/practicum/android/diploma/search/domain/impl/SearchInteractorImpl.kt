package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.models.VacancySearchParams
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.SearchedVacancy

class SearchInteractorImpl(
    private val searchRepository: SearchRepository
) : SearchInteractor {

    override fun searchVacancy(text: String): Flow<Resource<List<SearchedVacancy>>> {
        return searchRepository.searchVacancy(
            VacancySearchParams(text)
        )
    }
}
