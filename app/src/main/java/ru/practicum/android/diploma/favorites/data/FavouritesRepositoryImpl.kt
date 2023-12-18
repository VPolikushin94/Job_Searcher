package ru.practicum.android.diploma.favorites.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.core.db.AppDatabase
import ru.practicum.android.diploma.core.db.converter.VacancyDbMapper
import ru.practicum.android.diploma.core.models.SearchedVacancy
import ru.practicum.android.diploma.favorites.domain.api.FavouritesRepository
import ru.practicum.android.diploma.favorites.domain.models.ErrorType
import ru.practicum.android.diploma.favorites.domain.models.Resource
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

class FavouritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val favouritesDbMapper: FavouritesDbMapper,
    private val vacancyDbMapper: VacancyDbMapper
) : FavouritesRepository {

    override suspend fun insertVacancy(vacancy: DetailsVacancy) {
        appDatabase.vacancyDao().insertVacancy(favouritesDbMapper.mapDetails(vacancy))
    }

    override suspend fun deleteVacancy(vacancy: DetailsVacancy) {
        appDatabase.vacancyDao().deleteVacancy(favouritesDbMapper.mapDetails(vacancy))
    }

    override suspend fun getVacancyById(id: String): DetailsVacancy {
        return favouritesDbMapper.mapEntity(appDatabase.vacancyDao().getCurrentVacancy(id))
    }

    override suspend fun getVacancyList(): Flow<Resource<List<SearchedVacancy>>> = flow {
        try {
            val vacancyListEntity = appDatabase.vacancyDao().getVacancyList()
            val listVacancy = vacancyListEntity.map {
                vacancyDbMapper.map(it)
            }
            if (listVacancy.isEmpty()) {
                emit(Resource.Error(errorType = ErrorType.CANT_GET_LIST))
            } else {
                emit(Resource.Success(listVacancy))
            }
        } catch (e: Throwable) {
            emit(Resource.Error(errorType = ErrorType.LIST_EMPTY))
        }
    }

    override suspend fun inFavourites(id: String): Boolean {
        val vacancyListId = appDatabase.vacancyDao().getListId()
        return vacancyListId.contains(id)
    }
}
