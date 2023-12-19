package ru.practicum.android.diploma.search.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.core.dto.Response
import ru.practicum.android.diploma.core.dto.VacancyDto

data class VacancySearchResponse(
    @SerializedName("items")
    val vacancyList: List<VacancyDto>,
    val found: Int,
    val pages: Int,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int
) : Response()
