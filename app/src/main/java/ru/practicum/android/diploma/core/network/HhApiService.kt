package ru.practicum.android.diploma.core.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.vacancy.data.dto.VacancyDetailsResponse

interface HhApiService {
    @Headers(
        "Authorization: ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: Practicum vacancy"
    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: String): VacancyDetailsResponse

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: Practicum Vacancy"
    )
    @GET("vacancies")
    suspend fun searchVacancy(@QueryMap vacancySearchParams: Map<String, String>): VacancySearchResponse
}
