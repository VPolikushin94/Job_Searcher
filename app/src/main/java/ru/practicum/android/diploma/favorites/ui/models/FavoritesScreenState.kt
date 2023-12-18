package ru.practicum.android.diploma.favorites.ui.models

import ru.practicum.android.diploma.core.models.Vacancy

sealed interface FavoritesScreenState {

    data object Loading : FavoritesScreenState

    data class Content(val vacancyList: List<Vacancy>) : FavoritesScreenState

    data class Placeholder(val placeholderType: FavoritesPlaceholderType) : FavoritesScreenState
}
