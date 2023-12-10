package ru.practicum.android.diploma.vacancy.data.vacancyDto

import com.google.gson.annotations.SerializedName

data class LogoUrls(
    val original: String,
    @SerializedName("240")
    val size240: String,
    @SerializedName("90")
    val size90: String
)
