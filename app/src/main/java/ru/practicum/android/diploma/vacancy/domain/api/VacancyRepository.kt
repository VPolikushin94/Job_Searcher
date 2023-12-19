package ru.practicum.android.diploma.vacancy.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

interface VacancyRepository {
    suspend fun getSelectedVacancy(id: String): Flow<Resource<DetailsVacancy>>
}
