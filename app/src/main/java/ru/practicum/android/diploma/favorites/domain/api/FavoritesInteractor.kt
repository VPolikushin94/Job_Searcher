package ru.practicum.android.diploma.favorites.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorites.domain.models.Resource
import ru.practicum.android.diploma.core.models.SearchedVacancy

interface FavoritesInteractor {

    fun getVacancyList(): Flow<Resource<List<SearchedVacancy>>>
}
