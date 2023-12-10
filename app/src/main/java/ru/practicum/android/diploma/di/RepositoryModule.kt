package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.search.data.convertor.VacancyDtoConvertor
import ru.practicum.android.diploma.search.data.repository.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchRepository

val repositoryModule = module {

    factory { VacancyDtoConvertor() }

    single<SearchRepository> {
        SearchRepositoryImpl(
            networkClient = get(),
            vacancyDtoConvertor = get()
        )
    }
}
