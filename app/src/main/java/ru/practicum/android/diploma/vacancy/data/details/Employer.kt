package ru.practicum.android.diploma.vacancy.data.details

import com.google.gson.annotations.SerializedName

data class Employer(
    @SerializedName("id")
    val id: String,
    val name: String,
    val url: String,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrls?
)
