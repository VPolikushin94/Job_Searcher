package ru.practicum.android.diploma.core.network

import android.content.Context
import ru.practicum.android.diploma.core.dto.Response

class RetrofitNetworkClient(
    private val context: Context,
    private val hhApiService: HhApiService
) : NetworkClient {

    override suspend fun request(dto: Any): Response {
        TODO("Not yet implemented")
    }
}
