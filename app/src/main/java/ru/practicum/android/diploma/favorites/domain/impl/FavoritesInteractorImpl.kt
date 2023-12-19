package ru.practicum.android.diploma.favorites.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.models.SearchedVacancy
import ru.practicum.android.diploma.favorites.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.favorites.domain.models.Resource
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

class FavoritesInteractorImpl : FavoritesInteractor {
    override fun getVacancyList(): Flow<Resource<List<SearchedVacancy>>> {
        return flow { emit(Resource.Success(emptyList())) }
    }

    override suspend fun insertVacancy(searchVacancy: DetailsVacancy) {
        TODO()
    }

    override suspend fun deleteVacancy(searchVacancy: DetailsVacancy) {
        TODO()
    }

    override suspend fun inFavourites(id: String): Boolean {
        TODO()
    }
}
