package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.filter.data.repository.FilterRepositoryImpl
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.search.data.convertor.VacancyDtoConvertor
import ru.practicum.android.diploma.search.data.repository.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.vacancy.data.VacancyRepositoryImpl
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
        FilterRepositoryImpl(get())
    }

}
