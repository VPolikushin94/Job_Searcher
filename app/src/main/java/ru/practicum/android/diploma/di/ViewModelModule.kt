package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.ui.viewmodel.FiltrationIndustryViewModel
import ru.practicum.android.diploma.filter.ui.viewmodel.FiltrationViewModel
import ru.practicum.android.diploma.favorites.ui.viewmodel.FavoritesViewModel
import ru.practicum.android.diploma.search.ui.viewmodel.SearchViewModel
import ru.practicum.android.diploma.vacancy.ui.viewmodel.SimilarVacancyViewModel
import ru.practicum.android.diploma.vacancy.ui.viewmodel.VacancyViewModel

val viewModeModule = module {

    viewModel {
        FiltrationViewModel(
            filterSavingInteractor = get()
        )
    }

    viewModel {
        SearchViewModel(
            searchInteractor = get(),
            application = androidApplication()
        )
    }

    viewModel {
        VacancyViewModel(get(), get(), get())
    }

    viewModel {
        SimilarVacancyViewModel()
    }

    viewModel {
        FiltrationIndustryViewModel(
            filterInteractor = get(),
            filterSavingInteractor = get()
        )
    }

    viewModel {
        FavoritesViewModel(
            favoritesInteractor = get(),
            application = androidApplication()
        )
    }
}
