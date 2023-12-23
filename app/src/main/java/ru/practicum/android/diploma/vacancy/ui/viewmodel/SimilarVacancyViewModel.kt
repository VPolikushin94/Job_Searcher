package ru.practicum.android.diploma.vacancy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.models.Vacancy
import ru.practicum.android.diploma.vacancy.domain.api.SimilarVacancyInteractor

class SimilarVacancyViewModel(
    private val similarVacancyInteractor: SimilarVacancyInteractor,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var isClickAllowed = true
    private val _screenState = MutableLiveData<SimilarState>()
    val screenState = _screenState

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val id = savedStateHandle.get<String>(VacancyViewModel.BUNDLE_KEY)
            _screenState.value = SimilarState.Loading
            similarVacancyInteractor.getSimilarVacancy(id.toString()).collect { pair ->
                processResult(pair.first, pair.second.toString())
            }
        }
    }

    private fun processResult(vacancy: List<Vacancy>?, message: String) {
        when (message) {
            NO_INTERNET -> {
                _screenState.value = SimilarState.Error(message)
            }

            SERVER_ERROR -> {
                _screenState.value = SimilarState.Error(message)
            }

            else -> {
                if (vacancy != null) {
                    _screenState.value = SimilarState.Success(vacancy)
                }
            }
        }
    }

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch {
                delay(CLICK_DEBOUNCE_DELAY_MILLIS)
                isClickAllowed = true
            }
        }
        return current
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
        const val NO_INTERNET = "Check internet connection"
        const val SERVER_ERROR = "Server error"
    }
}
