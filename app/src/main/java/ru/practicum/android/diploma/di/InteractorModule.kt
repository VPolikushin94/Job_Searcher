package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.favorites.domain.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.impl.SearchInteractorImpl

val interactorModule = module {

    single<SearchInteractor> {
        SearchInteractorImpl(
            searchRepository = get()
        )
    }

    single<FavoritesInteractor> {
        FavoritesInteractorImpl()
    }
}
