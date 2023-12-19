package ru.practicum.android.diploma.favorites.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.models.SearchedVacancy
import ru.practicum.android.diploma.favorites.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.favorites.domain.api.FavouritesRepository
import ru.practicum.android.diploma.favorites.domain.models.Resource
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

class FavoritesInteractorImpl(private val favouritesRepository: FavouritesRepository) : FavoritesInteractor {
    override suspend fun getVacancyList(): Flow<Resource<List<SearchedVacancy>>> {
        return favouritesRepository.getVacancyList()
    }

    override suspend fun insertVacancy(vacancy: DetailsVacancy) {
        favouritesRepository.insertVacancy(vacancy)
    }

    override suspend fun deleteVacancy(vacancy: DetailsVacancy) {
        favouritesRepository.deleteVacancy(vacancy)
    }

    override suspend fun getVacancyById(id: String): DetailsVacancy {
        return favouritesRepository.getVacancyById(id)
    }

    override suspend fun inFavourites(id: String): Boolean {
        return favouritesRepository.inFavourites(id)
    }
}
