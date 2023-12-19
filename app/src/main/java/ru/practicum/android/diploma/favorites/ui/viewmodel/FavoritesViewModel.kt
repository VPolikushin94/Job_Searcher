package ru.practicum.android.diploma.favorites.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.models.SearchedVacancy
import ru.practicum.android.diploma.core.models.toVacancy
import ru.practicum.android.diploma.favorites.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.favorites.domain.models.Resource
import ru.practicum.android.diploma.favorites.ui.models.FavoritesPlaceholderType
import ru.practicum.android.diploma.favorites.ui.models.FavoritesScreenState

class FavoritesViewModel(
    private val favoritesInteractor: FavoritesInteractor,
    private val application: Application
) : ViewModel() {

    private var isClickAllowed = true

    private val _screenState = MutableLiveData<FavoritesScreenState>(
        FavoritesScreenState.Placeholder(
            FavoritesPlaceholderType.PLACEHOLDER_EMPTY_LIST
        )
    )
    val screenState: LiveData<FavoritesScreenState> = _screenState

    init {
        getVacancyList()
    }

    private fun getVacancyList() {
        _screenState.value = FavoritesScreenState.Loading
        viewModelScope.launch {
            favoritesInteractor.getVacancyList()
                .collect {
                    processResult(it)
                }
        }
    }

    private fun processResult(resource: Resource<List<SearchedVacancy>>) {
        when (resource) {
            is Resource.Success -> {
                setContentState(resource.data)
            }

            is Resource.Error -> {
                _screenState.postValue(
                    FavoritesScreenState.Placeholder(
                        FavoritesPlaceholderType.PLACEHOLDER_GOT_EMPTY_LIST_ERROR
                    )
                )
            }
        }
    }

    private fun setContentState(vacancyList: List<SearchedVacancy>) {
        if (vacancyList.isEmpty()) {
            _screenState.postValue(
                FavoritesScreenState.Placeholder(
                    FavoritesPlaceholderType.PLACEHOLDER_EMPTY_LIST
                )
            )
        } else {
            _screenState.postValue(
                FavoritesScreenState.Content(
                    vacancyList.map { it.toVacancy(application) }
                )
            )
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
    }
}
