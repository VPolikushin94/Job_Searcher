package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.data.FavouritesRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.api.FavouritesRepository
import ru.practicum.android.diploma.filter.data.repository.FilterRepositoryImpl
import ru.practicum.android.diploma.filter.data.repository.FilterSavingRepositoryImpl
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.api.FilterSavingRepository
import ru.practicum.android.diploma.search.data.convertor.VacancyDtoConvertor
import ru.practicum.android.diploma.search.data.repository.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.vacancy.data.repository.SimilarVacancyRepositoryImpl
import ru.practicum.android.diploma.vacancy.data.repository.VacancyRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.api.SimilarVacancyRepository
import ru.practicum.android.diploma.vacancy.domain.api.VacancyRepository

val repositoryModule = module {

    factory { VacancyDtoConvertor() }

    single<SearchRepository> {
        SearchRepositoryImpl(
            networkClient = get(),
            vacancyDtoConvertor = get()
        )
    }

    single<VacancyRepository> {
        VacancyRepositoryImpl(get())
    }

    single<FilterRepository> {
        FilterRepositoryImpl(get(), get())
    }

    single<FilterSavingRepository> {
        FilterSavingRepositoryImpl(get(), get())
    }

    single<FavouritesRepository> {
        FavouritesRepositoryImpl(
            appDatabase = get(),
            vacancyDbMapper = get(),
            favouritesDbMapper = get()
        )
    }

    single<SimilarVacancyRepository> {
        SimilarVacancyRepositoryImpl(
            networkClient = get(),
            vacancyDtoConvertor = get(),
            application = androidApplication()
        )
    }
}
