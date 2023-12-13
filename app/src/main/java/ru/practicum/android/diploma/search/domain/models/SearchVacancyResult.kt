package ru.practicum.android.diploma.search.domain.models

import ru.practicum.android.diploma.core.models.SearchedVacancy

data class SearchVacancyResult(
    val vacancyList: List<SearchedVacancy>,
    val found: Int
)
