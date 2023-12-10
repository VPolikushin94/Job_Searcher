package ru.practicum.android.diploma.search.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.models.VacancySearchParams
import ru.practicum.android.diploma.core.models.toMap
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.search.data.convertor.VacancyDtoConvertor
import ru.practicum.android.diploma.search.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.models.ErrorType
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.SearchVacancyResult
import ru.practicum.android.diploma.search.domain.models.SearchedVacancy
import ru.practicum.android.diploma.util.NetworkResultCode

class SearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyDtoConvertor: VacancyDtoConvertor,
) : SearchRepository {

    private var vacancyList: List<SearchedVacancy> = emptyList()
    private var found: Int = 0
    override fun searchVacancy(
        vacancySearchParams: VacancySearchParams,
    ): Flow<Resource<SearchVacancyResult>> = flow {
        val response = networkClient.request(VacancySearchRequest(vacancySearchParams.toMap()))

        when (response.resultCode) {
            NetworkResultCode.RESULT_OK -> {
                val vacancyResponse = response as VacancySearchResponse
                vacancyList = vacancyResponse.vacancyList.map {
                    vacancyDtoConvertor.map(it)
                }
                found = vacancyResponse.found
                emit(
                    Resource.Success(
                        SearchVacancyResult(vacancyList, found)
                    )
                )
            }

            NetworkResultCode.RESULT_NO_INTERNET -> emit(Resource.Error(ErrorType.NO_INTERNET))
            else -> emit(Resource.Error(ErrorType.SERVER_ERROR))
        }
    }

    override suspend fun getCachedVacancySearchResult(): SearchVacancyResult {
        return SearchVacancyResult(
            vacancyList,
            found
        )
    }
}
