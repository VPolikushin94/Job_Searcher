package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.ui.viewmodel.FiltrationLocationCountryViewModel
import ru.practicum.android.diploma.filter.ui.viewmodel.FiltrationLocationViewModel
import ru.practicum.android.diploma.filter.ui.viewmodel.FiltrationViewModel
import ru.practicum.android.diploma.search.ui.viewmodel.SearchViewModel
import ru.practicum.android.diploma.vacancy.ui.viewmodel.VacancyViewModel

val viewModeModule = module {

    viewModel {
        FiltrationViewModel()
    }

    viewModel {
        SearchViewModel(
            searchInteractor = get()
        )
    }

    viewModel {
        VacancyViewModel(get(), get())
    }

    viewModel {
        FiltrationLocationViewModel()
    }

    viewModel {
        FiltrationLocationCountryViewModel()
    }
}
