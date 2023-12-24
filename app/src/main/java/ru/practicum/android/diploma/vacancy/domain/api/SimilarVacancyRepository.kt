package ru.practicum.android.diploma.vacancy.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.models.Vacancy
import ru.practicum.android.diploma.util.Resource

interface SimilarVacancyRepository {
    suspend fun getSimilarVacancy(id: String): Flow<Resource<List<Vacancy>>>
}
