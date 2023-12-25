package ru.practicum.android.diploma.vacancy.data.repository

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.core.models.Vacancy
import ru.practicum.android.diploma.core.models.toVacancy
import ru.practicum.android.diploma.search.data.convertor.VacancyDtoConvertor
import ru.practicum.android.diploma.util.NetworkResultCode
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.data.dto.SimilarVacancyRequest
import ru.practicum.android.diploma.vacancy.data.dto.SimilarVacancyResponse
import ru.practicum.android.diploma.vacancy.domain.api.SimilarVacancyRepository

class SimilarVacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyDtoConvertor: VacancyDtoConvertor,
    private val application: Application
) : SimilarVacancyRepository {
    override suspend fun getSimilarVacancy(id: String): Flow<Resource<List<Vacancy>>> =
        flow<Resource<List<Vacancy>>> {
            val response = networkClient.request(SimilarVacancyRequest(id))
            when (response.resultCode) {
                NetworkResultCode.RESULT_NO_INTERNET -> {
                    emit(Resource.Error(NO_INTERNET))
                }

                NetworkResultCode.RESULT_OK -> {
                    val resultResponse = (response as SimilarVacancyResponse).vacancyList.map { vacancyDto ->
                        vacancyDtoConvertor.map(vacancyDto)
                    }
                    val vacancy = resultResponse.map { searchedVacancy ->
                        searchedVacancy.toVacancy(application)
                    }
                    emit(Resource.Success(vacancy))
                }

                else -> emit(Resource.Error(SERVER_ERROR))
            }

        }.flowOn(Dispatchers.IO)

    companion object {
        const val NO_INTERNET = "Check internet connection"
        const val SERVER_ERROR = "Server error"
    }

}
