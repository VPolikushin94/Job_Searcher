package ru.practicum.android.diploma.vacancy.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

interface VacancyInteractor {
    suspend fun getSelectedVacancy(id: String): Flow<Pair<DetailsVacancy?, String?>>
}
