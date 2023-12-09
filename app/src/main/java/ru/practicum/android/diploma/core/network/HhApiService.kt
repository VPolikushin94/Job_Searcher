package ru.practicum.android.diploma.core.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import ru.practicum.android.diploma.core.dto.Response

interface HhApiService {
    @Headers(
        "Authorization: Bearer HH_ACCESS_TOKEN",
        "HH-User-Agent: Practicum vacancy"
    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: String): Response
}
