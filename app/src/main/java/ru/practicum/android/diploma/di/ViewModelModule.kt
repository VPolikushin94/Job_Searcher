package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.ui.view_model.FilterViewModel
import ru.practicum.android.diploma.search.ui.view_model.SearchViewModel
import ru.practicum.android.diploma.vacancy.ui.view_model.VacancyViewModel

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
