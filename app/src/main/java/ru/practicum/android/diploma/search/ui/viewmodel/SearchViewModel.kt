package ru.practicum.android.diploma.search.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.ui.models.SearchPlaceholderType
import ru.practicum.android.diploma.search.ui.models.SearchScreenState
import ru.practicum.android.diploma.util.debounce

class SearchViewModel : ViewModel() {

    private var isClickAllowed = true

    private val searchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY_MILLIS, viewModelScope, true) {
            searchVacancy(it)
        }

    private val _screenState =
        MutableLiveData<SearchScreenState>(SearchScreenState.Placeholder(SearchPlaceholderType.PLACEHOLDER_NOT_SEARCHED_YET))
    val screenState: LiveData<SearchScreenState> = _screenState

    private val _btnFilterState = MutableLiveData<Boolean>()
    val btnFilterState: LiveData<Boolean> = _btnFilterState

    fun searchVacancy(searchText: String) {
        if (searchText.isNotEmpty()) {
            viewModelScope.launch {

            }
        }
    }

    fun searchVacancyDebounce(searchText: String) {
        if (searchText.isNotEmpty()) {
            _screenState.value = SearchScreenState.Placeholder(SearchPlaceholderType.PLACEHOLDER_EMPTY)
            searchDebounce(searchText)
        } else {
            _screenState.value = SearchScreenState.Placeholder(SearchPlaceholderType.PLACEHOLDER_NOT_SEARCHED_YET)
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
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
    }
}
