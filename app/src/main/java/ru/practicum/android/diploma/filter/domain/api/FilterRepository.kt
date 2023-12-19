package ru.practicum.android.diploma.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

interface FilterRepository {
    fun getIndustries(): Flow<Resource<List<Industry>>>
}
