package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.favorites.domain.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterSavingInteractor
import ru.practicum.android.diploma.filter.domain.impl.FilterInteractorImpl
import ru.practicum.android.diploma.filter.domain.impl.FilterSavingInteractorImpl
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.impl.VacancyInteractorImpl

val interactorModule = module {

    single<SearchInteractor> {
        SearchInteractorImpl(
            searchRepository = get()
        )
    }

    single<VacancyInteractor> {
        VacancyInteractorImpl(get())
    }

    single<FavoritesInteractor> {
        FavoritesInteractorImpl()
    }

    single<FilterInteractor> {
        FilterInteractorImpl(get())
    }

    single<FilterSavingInteractor> {
        FilterSavingInteractorImpl(get())
    }

}
