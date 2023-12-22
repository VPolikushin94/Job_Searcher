package ru.practicum.android.diploma.vacancy.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.util.NetworkResultCode
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsResponse
import ru.practicum.android.diploma.vacancy.domain.api.VacancyRepository
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
) : VacancyRepository {

    override suspend fun getSelectedVacancy(id: String): Flow<Resource<DetailsVacancy>> = flow {
        val response = networkClient.request(VacancyDetailsRequest(id))
        when (response.resultCode) {
            NetworkResultCode.RESULT_OK -> {
                val vacancyDetailsResponse = (response as VacancyDetailsResponse).vacancyItem.mapToDetailsVacancy()
                emit(Resource.Success(vacancyDetailsResponse))
            }

            else -> emit(Resource.Error(SERVER_ERROR))
        }
    }

    companion object {
        const val SERVER_ERROR = "server error"
    }
}
