package ru.practicum.android.diploma.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.util.DataResult
import ru.practicum.android.diploma.util.Resource

class FilterInteractorImpl(private val repository: FilterRepository) : FilterInteractor {
    override suspend fun getIndustries(): Flow<DataResult<Industry>> {
        return repository.getIndustries().map { result ->
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
