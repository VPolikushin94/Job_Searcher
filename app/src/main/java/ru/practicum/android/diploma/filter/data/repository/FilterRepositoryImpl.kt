package ru.practicum.android.diploma.filter.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.core.dto.Request
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.filter.data.dto.IndustryResponse
import ru.practicum.android.diploma.filter.data.mapper.IndustryResponseMapper
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.util.NetworkResultCode
import ru.practicum.android.diploma.util.Resource

class FilterRepositoryImpl(
    private val networkClient: NetworkClient,
) : FilterRepository {
    override fun getIndustries(): Flow<Resource<List<Industry>>> = flow {
        val response = networkClient.request(Request.IndustryRequest)
        when (response.resultCode) {
            NetworkResultCode.RESULT_NO_INTERNET -> {
                emit(Resource.Error(NO_INTERNET))
            }

            NetworkResultCode.RESULT_OK -> {
                val result =
                    ((response as IndustryResponse).industries.flatMap { industryResponseDto ->
                        IndustryResponseMapper.map(industryResponseDto)
                    }).sortedBy { it.name }
                emit(Resource.Success(result))
            }

            else -> emit(Resource.Error(SERVER_ERROR))
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        const val NO_INTERNET = "Check internet connection"
        const val SERVER_ERROR = "Server error"
    }
}
