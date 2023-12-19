package ru.practicum.android.diploma.vacancy.ui.viewmodel

import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

sealed class VacancyState {
    data object Loading : VacancyState()
    data object Error : VacancyState()
    class Success(val data: DetailsVacancy) : VacancyState()
}
