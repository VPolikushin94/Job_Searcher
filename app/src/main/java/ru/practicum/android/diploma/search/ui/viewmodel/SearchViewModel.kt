package ru.practicum.android.diploma.search.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.util.debounce

class SearchViewModel : ViewModel() {

    private var isClickAllowed = true

    private val searchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY_MILLIS, viewModelScope, true) {
            searchVacancy(it)
        }

    private val _screenState = MutableLiveData(false)

    fun searchVacancy(searchText: String) {
        if (searchText.isNotEmpty()) {
            viewModelScope.launch {

            }
        }
    }

    fun searchVacancyDebounce(searchText: String) {
        searchDebounce(searchText)
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
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
    }
}
