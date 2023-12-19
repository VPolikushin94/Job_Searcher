package ru.practicum.android.diploma.core.models

import android.app.Application
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.util.buildSalaryString

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

fun SearchedVacancy.toVacancy(application: Application): Vacancy {
    return Vacancy(
        this.id,
        this.img,
        this.vacancy,
        this.area,
        this.employerName,
        buildSalaryString(this.salaryFrom, this.salaryTo, this.currency, application)
    )
}
