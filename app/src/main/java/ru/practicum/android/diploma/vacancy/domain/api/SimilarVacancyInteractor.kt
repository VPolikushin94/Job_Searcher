package ru.practicum.android.diploma.vacancy.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.models.Vacancy

interface SimilarVacancyInteractor {
    suspend fun getSimilarVacancy(id: String): Flow<Pair<List<Vacancy>?, String?>>
}
