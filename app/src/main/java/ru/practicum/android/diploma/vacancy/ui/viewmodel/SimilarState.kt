package ru.practicum.android.diploma.vacancy.ui.viewmodel

import ru.practicum.android.diploma.core.models.Vacancy

sealed class SimilarState {
    data object Loading : SimilarState()
    data class Error(val message: String) : SimilarState()
    class Success(val similarList: List<Vacancy>) : SimilarState()
}
