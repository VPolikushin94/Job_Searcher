package ru.practicum.android.diploma.vacancy.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.core.models.Vacancy
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.util.DataResult
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.domain.api.SimilarVacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.api.SimilarVacancyRepository

class SimilarVacancyInteractorImpl(
    private val similarVacancyRepository: SimilarVacancyRepository
) : SimilarVacancyInteractor {
    override suspend fun getSimilarVacancy(id: String): Flow<DataResult<Vacancy>> {
        return similarVacancyRepository.getSimilarVacancy(id).map { result ->
            when (result) {
                is Resource.Success -> {
                    DataResult(result.data)
                }

                is Resource.Error -> {
                    DataResult(isError = true)
                }
            }
        }
    }
}
