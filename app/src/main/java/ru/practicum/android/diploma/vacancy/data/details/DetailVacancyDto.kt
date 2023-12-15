package ru.practicum.android.diploma.vacancy.data.details

import com.google.gson.annotations.SerializedName

data class DetailVacancyDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("keySkills")
    val keySkills: String?,
    @SerializedName("area")
    val area: Area,
    @SerializedName("employer")
    val employer: Employer,
    @SerializedName("employment")
    val employment: Employment,
    @SerializedName("experience")
    val experience: Experience,
    @SerializedName("salary")
    val salary: Salary?,
    @SerializedName("contacts")
    val contacts: Contacts?,
    @SerializedName("phone")
    val phone: Phone,
    @SerializedName("alternate_url")
    val url: String?,
)
