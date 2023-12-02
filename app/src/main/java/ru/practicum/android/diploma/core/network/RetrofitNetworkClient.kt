package ru.practicum.android.diploma.core.network

import ru.practicum.android.diploma.core.dto.Response

class RetrofitNetworkClient(private val hhApiService: HhApi) : NetworkClient {

    override suspend fun request(dto: Any): Response {
        TODO("Not yet implemented")
    }
}
