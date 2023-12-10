package ru.practicum.android.diploma.core.network

import ru.practicum.android.diploma.core.dto.Request
import ru.practicum.android.diploma.core.dto.Response

interface NetworkClient {
    suspend fun request(dto: Request): Response

}
