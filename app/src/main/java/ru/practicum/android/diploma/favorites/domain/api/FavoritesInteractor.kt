package ru.practicum.android.diploma.favorites.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.models.SearchedVacancy
import ru.practicum.android.diploma.favorites.domain.models.Resource
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

interface FavoritesInteractor {

    suspend fun getVacancyList(): Flow<Resource<List<SearchedVacancy>>>
    suspend fun inFavourites(id: String): Boolean
    suspend fun insertVacancy(vacancy: DetailsVacancy)
    suspend fun deleteVacancy(vacancy: DetailsVacancy)
    suspend fun getVacancyById(id: String): DetailsVacancy
}
