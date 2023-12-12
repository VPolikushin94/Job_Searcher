package ru.practicum.android.diploma.vacancy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

class VacancyViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val vacancyInteractor: VacancyInteractor
) : ViewModel() {

    private var isClickAllowed = true

    init {
        getDetailsVacancy()
    }

    private val _screenState = MutableLiveData<VacancyState>(VacancyState.Loading)
    val screenState = _screenState

    private val _onClickDebounce = MutableLiveData<Boolean>()
    val onClickDebounce = _onClickDebounce

    private fun getDetailsVacancy() {
        viewModelScope.launch {
            val id = savedStateHandle.get<String>(BUNDLE_KEY)
            vacancyInteractor.getSelectedVacancy(id).collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun processResult(detailsVacancy: DetailsVacancy?, message: String?) {
        when (message) {
            SERVER_ERROR -> {
                _screenState.value = VacancyState.Error
            }

            else -> _screenState.value = detailsVacancy?.let { VacancyState.Success(it) }
        }
    }

    fun clickDebounce() {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch {
                delay(CLICK_DEBOUNCE_DELAY_MILLIS)
                isClickAllowed = true
            }
        }
        _onClickDebounce.value = current
    }

    companion object {
        const val BUNDLE_KEY = "bundle_key"
        private const val SERVER_ERROR = "server error"
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }
}
