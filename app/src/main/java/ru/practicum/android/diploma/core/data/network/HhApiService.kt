package ru.practicum.android.diploma.core.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.filter.data.dto.IndustryResponseDto
import ru.practicum.android.diploma.search.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.vacancy.data.details.DetailVacancyDto
import ru.practicum.android.diploma.vacancy.data.dto.SimilarVacancyResponse

interface HhApiService {
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: Practicum Vacancy"
    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: String): DetailVacancyDto

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: Practicum Vacancy"
    )
    @GET("vacancies")
    suspend fun searchVacancy(@QueryMap vacancySearchParams: Map<String, String>): VacancySearchResponse

    @GET("/industries")
    suspend fun getIndustry(): List<IndustryResponseDto>

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: Practicum Vacancy"
    )
    @GET("/vacancies/{vacancy_id}/similar_vacancies")
    suspend fun getSimilarVacancy(
        @Path("vacancy_id") vacancy: String,
    ): SimilarVacancyResponse
}
