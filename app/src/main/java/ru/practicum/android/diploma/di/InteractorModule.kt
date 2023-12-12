package ru.practicum.android.diploma.di

import org.koin.dsl.module
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
}
