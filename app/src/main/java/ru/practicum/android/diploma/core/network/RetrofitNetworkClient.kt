package ru.practicum.android.diploma.core.network

import android.accounts.NetworkErrorException
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.core.dto.Request
import ru.practicum.android.diploma.core.dto.Response
import ru.practicum.android.diploma.search.data.dto.VacancySearchRequest
import ru.practicum.android.diploma.util.NetworkResultCode
import ru.practicum.android.diploma.util.isInternetConnected

class RetrofitNetworkClient(
    private val context: Context,
    private val hhApiService: HhApiService,
) : NetworkClient {

    @Suppress(
        "TooGenericExceptionCaught",
        "SwallowedException"
    )
    override suspend fun request(dto: Request): Response {
        if (!isInternetConnected(context)) {
            return Response().apply { resultCode = NetworkResultCode.RESULT_NO_INTERNET }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is VacancySearchRequest -> hhApiService.searchVacancy(dto.vacancySearchParams)
                    else -> throw NetworkErrorException("Wrong dto")
                }
                response.apply { resultCode = NetworkResultCode.RESULT_OK }
            } catch (e: Throwable) {
                Response().apply { resultCode = NetworkResultCode.RESULT_ERROR }
            }
        }
    }
}

