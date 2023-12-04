package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.ui.viewmodel.FilterViewModel
import ru.practicum.android.diploma.search.ui.viewmodel.SearchViewModel
import ru.practicum.android.diploma.vacancy.ui.viewmodel.VacancyViewModel

val viewModeModule = module {

    viewModel {
        FilterViewModel()
    }

    viewModel {
        SearchViewModel()
    }

    viewModel {
        VacancyViewModel()
    }
}
