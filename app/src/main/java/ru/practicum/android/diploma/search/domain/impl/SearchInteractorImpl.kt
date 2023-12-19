package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.SearchVacancyResult
import ru.practicum.android.diploma.search.domain.models.VacancySearchParams

class SearchInteractorImpl(
    private val searchRepository: SearchRepository,
) : SearchInteractor {

    override fun searchVacancy(searchText: String, page: Int, perPage: Int): Flow<Resource<SearchVacancyResult>> {
        return searchRepository.searchVacancy(
            VacancySearchParams(
                searchText,
                null,
                null,
                null,
                null,
                page,
                perPage
            )
        )
    }

    override fun getSavedIndustries(): Industry? {
        return searchRepository.getSavedIndustries()
    }

    override fun getSalary(): String {
        return searchRepository.getSalary()
    }

    override fun getSalaryOnly(): Boolean {
        return searchRepository.getSalaryOnly()
    }
}
