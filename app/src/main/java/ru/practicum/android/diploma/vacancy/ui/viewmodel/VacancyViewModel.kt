package ru.practicum.android.diploma.vacancy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

class VacancyViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val vacancyInteractor: VacancyInteractor
) : ViewModel() {

    init {
        getDetailsVacancy()
    }

    private val _screenState = MutableLiveData<VacancyState>(VacancyState.Loading)
    val screenState = _screenState

    private fun getDetailsVacancy() {
        viewModelScope.launch {
            val id = savedStateHandle.get<String>(BUNDLE_KEY)
            vacancyInteractor.getSelectedVacancy(id).collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun processResult(detailsVacancy: DetailsVacancy, message: String?) {
        when (message) {
            ERROR -> _screenState.value = VacancyState.Error
            else -> _screenState.value = VacancyState.Success(detailsVacancy)
        }
    }

    companion object {
        const val BUNDLE_KEY = "bundle_key"
        private const val ERROR = "error"
    }
}
