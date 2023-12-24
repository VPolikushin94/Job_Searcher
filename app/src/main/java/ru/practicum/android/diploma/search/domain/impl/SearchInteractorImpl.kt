package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.api.FilterSavingRepository
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.SearchVacancyResult
import ru.practicum.android.diploma.search.domain.models.VacancySearchParams

class SearchInteractorImpl(
    private val searchRepository: SearchRepository,
    private val filterSavingRepository: FilterSavingRepository
) : SearchInteractor {

    override fun searchVacancy(searchText: String, page: Int, perPage: Int): Flow<Resource<SearchVacancyResult>> {
        val industryId = getSavedIndustryId()
        val salary = getSalary()
        val isOnlyWithSalary = isOnlyWithSalary()
        return searchRepository.searchVacancy(
            VacancySearchParams(
                searchText,
                null,
                salary,
                isOnlyWithSalary,
                industryId,
                page,
                perPage
            )
        )
    }

    private fun getSavedIndustryId(): String? {
        return filterSavingRepository.getSavedIndustries()?.id
    }

    private fun getSalary(): Int? {
        val salary = filterSavingRepository.getSalary()
        return if (salary.isEmpty()) {
            null
        } else {
            salary.toInt()
        }
    }

    private fun isOnlyWithSalary(): Boolean? {
        val isFlagChecked = filterSavingRepository.getSalaryOnly()
        return if (isFlagChecked) {
            true
        } else {
            null
        }
    }

    override suspend fun areFiltersChanged(): Boolean {
        return filterSavingRepository.areFiltersChanged()
    }

    override suspend fun areFiltersEmpty(): Boolean {
        return filterSavingRepository.areFiltersEmpty()
    }
}
