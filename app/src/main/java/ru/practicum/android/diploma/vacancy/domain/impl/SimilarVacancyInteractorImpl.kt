package ru.practicum.android.diploma.vacancy.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.core.models.Vacancy
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.domain.api.SimilarVacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.api.SimilarVacancyRepository

class SimilarVacancyInteractorImpl(
    private val similarVacancyRepository: SimilarVacancyRepository
) : SimilarVacancyInteractor {
    override suspend fun getSimilarVacancy(id: String): Flow<Pair<List<Vacancy>?, String?>> {
        return similarVacancyRepository.getSimilarVacancy(id).map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, "")
                else -> Pair(null, result.message)
            }
        }
    }
}
