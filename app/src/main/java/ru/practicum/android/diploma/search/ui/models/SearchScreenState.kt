package ru.practicum.android.diploma.search.ui.models

import ru.practicum.android.diploma.core.models.SearchedVacancy

sealed interface SearchScreenState {

    data class Loading(val isPaging: Boolean) : SearchScreenState

    data class Content(val vacancyList: List<SearchedVacancy>, val found: Int) : SearchScreenState

    data class Placeholder(val placeholderType: SearchPlaceholderType) : SearchScreenState
}
