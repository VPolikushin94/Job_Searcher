package ru.practicum.android.diploma.search.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.models.ErrorType
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.SearchVacancyResult
import ru.practicum.android.diploma.search.ui.models.SearchPlaceholderType
import ru.practicum.android.diploma.search.ui.models.SearchScreenState
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
) : ViewModel() {

    private var isClickAllowed = true

    private val searchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY_MILLIS, viewModelScope, true) {
            searchVacancy(it)
        }

    private val _screenState = MutableLiveData<SearchScreenState>(
        SearchScreenState.Placeholder(
            SearchPlaceholderType.PLACEHOLDER_NOT_SEARCHED_YET
        )
    )
    val screenState: LiveData<SearchScreenState> = _screenState

    private val _btnFilterState = MutableLiveData<Boolean>()
    val btnFilterState: LiveData<Boolean> = _btnFilterState

    private var searchedText: String = ""
    private var hasSearchBlocked = false

    fun searchVacancy(searchText: String) {
        if (searchedText == searchText || hasSearchBlocked) {
            return
        }

        if (searchText.isNotEmpty()) {
            _screenState.value = SearchScreenState.Loading
            viewModelScope.launch {
                searchedText = searchText
                searchInteractor.searchVacancy(searchText)
                    .collect {
                        processResult(it)
                    }
            }
        }
    }

    fun getCachedVacancySearchResult() {
        _screenState.value = SearchScreenState.Loading
        viewModelScope.launch {
            val vacancyList = searchInteractor.getCachedVacancySearchResult()
            setContentState(vacancyList)
        }
    }

    private fun setContentState(searchResult: SearchVacancyResult) {
        if (searchResult.vacancyList.isEmpty()) {
            _screenState.postValue(
                SearchScreenState.Placeholder(
                    SearchPlaceholderType.PLACEHOLDER_GOT_EMPTY_LIST
                )
            )
        } else {
            _screenState.postValue(
                SearchScreenState.Content(
                    searchResult.vacancyList,
                    searchResult.found
                )
            )
        }
    }

    private fun processResult(searchedData: Resource<SearchVacancyResult>) {
        when (searchedData) {
            is Resource.Success -> {
                setContentState(searchedData.data)
            }

            is Resource.Error -> {
                when (searchedData.errorType) {
                    ErrorType.NO_INTERNET -> _screenState.postValue(
                        SearchScreenState.Placeholder(
                            SearchPlaceholderType.PLACEHOLDER_NO_INTERNET
                        )
                    )

                    ErrorType.SERVER_ERROR -> _screenState.postValue(
                        SearchScreenState.Placeholder(
                            SearchPlaceholderType.PLACEHOLDER_SERVER_ERROR
                        )
                    )
                }
            }
        }
    }

    fun blockSearch(hasBlocked: Boolean) {
        if (hasBlocked) {
            hasSearchBlocked = true
            searchedText = ""
            _screenState.value = SearchScreenState.Placeholder(SearchPlaceholderType.PLACEHOLDER_NOT_SEARCHED_YET)
        } else {
            hasSearchBlocked = false
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
