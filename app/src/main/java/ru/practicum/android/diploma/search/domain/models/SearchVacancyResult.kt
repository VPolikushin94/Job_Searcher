package ru.practicum.android.diploma.search.domain.models

data class SearchVacancyResult(
    val vacancyList: List<SearchedVacancy>,
    val found: Int
)
