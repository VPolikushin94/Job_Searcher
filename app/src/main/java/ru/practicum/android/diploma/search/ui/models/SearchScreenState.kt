package ru.practicum.android.diploma.search.ui.models

import ru.practicum.android.diploma.search.domain.models.SearchedVacancy

sealed interface SearchScreenState {

    data object Loading : SearchScreenState

    data class Content(val vacancyList: List<SearchedVacancy>) : SearchScreenState

    data class Placeholder(val placeholderType: SearchPlaceholderType) : SearchScreenState
}
