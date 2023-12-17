package ru.practicum.android.diploma.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.domain.models.IndustryResult

interface FilterInteractor {
    suspend fun getIndustries(): Flow<IndustryResult<Industry>>
}
