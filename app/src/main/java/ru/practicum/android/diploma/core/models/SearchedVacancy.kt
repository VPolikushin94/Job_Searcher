package ru.practicum.android.diploma.core.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchedVacancy(
    val id: Int,
    val img: String?,
    val vacancy: String,
    val area: String,
    val employerName: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currency: String
) : Parcelable
