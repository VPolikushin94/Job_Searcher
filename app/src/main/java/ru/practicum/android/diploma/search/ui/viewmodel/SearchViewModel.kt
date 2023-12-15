package ru.practicum.android.diploma.search.ui.viewmodel

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.models.SearchedVacancy
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
            searchVacancy(it, false)
        }

    private val _screenState = MutableLiveData<SearchScreenState>(
        SearchScreenState.Placeholder(
            SearchPlaceholderType.PLACEHOLDER_NOT_SEARCHED_YET
        )
    )
    val screenState: LiveData<SearchScreenState> = _screenState

    private val _btnFilterState = MutableLiveData<Boolean>()
    val btnFilterState: LiveData<Boolean> = _btnFilterState

    private val _triggerClearAdapter = MutableLiveData(false)
    val triggerClearAdapter: LiveData<Boolean> = _triggerClearAdapter

    private var _searchedText: String = ""
    private var hasSearchBlocked = false

    private var page = 0
    private var pages = 0
    private var isNextPageLoading = false
    var rvState: Parcelable? = null

    fun searchVacancy(searchText: String, isPagingSearch: Boolean) {
        if (_searchedText != searchText && !isPagingSearch) {
            page = 0
            pages = 0
            _triggerClearAdapter.value = true
        }
        if (isSearchCanceled(searchText, isPagingSearch)) {
            return
        }

        if (searchText.isNotEmpty()) {
            if (isPagingSearch) {
                isNextPageLoading = true
                _screenState.value = SearchScreenState.Loading(true)
            } else {
                _screenState.value = SearchScreenState.Loading(false)
            }
            _triggerClearAdapter.value = false
            viewModelScope.launch {
                _searchedText = searchText
                searchInteractor.searchVacancy(
                    searchText,
                    page,
                    PAGE_SIZE
                ).collect {
                    isNextPageLoading = false
                    processResult(it)
                }
            }
        }
    }

    fun getSearchedText(): String {
        return _searchedText
    }
    @Suppress(
        "ReturnCount",
        "CollapsibleIfStatements"
    )
    private fun isSearchCanceled(searchText: String, isPagingSearch: Boolean): Boolean {
        if (searchText.isEmpty()) {
            _screenState.value = SearchScreenState.Placeholder(SearchPlaceholderType.PLACEHOLDER_GOT_EMPTY_LIST)
            return true
        }
        if (
            screenState.value != SearchScreenState.Placeholder(SearchPlaceholderType.PLACEHOLDER_SERVER_ERROR) &&
            screenState.value != SearchScreenState.Placeholder(SearchPlaceholderType.PLACEHOLDER_NO_INTERNET)
        ) {
            if ((_searchedText == searchText || hasSearchBlocked) && !isPagingSearch) {
                return true
            }
        }

        if (isNextPageLoading) {
            return true
        }
        return page == pages && pages != 0
    }

    fun cacheVacancyList(vacancyList: List<SearchedVacancy>) {
        viewModelScope.launch {
            searchInteractor.cacheVacancyList(vacancyList)
        }
    }

    fun getCachedVacancySearchResult() {
        _screenState.value = SearchScreenState.Loading(false)
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
            page = searchResult.page + 1
            pages = searchResult.pages
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
            _searchedText = ""
            _screenState.value = SearchScreenState.Placeholder(SearchPlaceholderType.PLACEHOLDER_NOT_SEARCHED_YET)
        } else {
            hasSearchBlocked = false
        }
    }

    fun searchVacancyDebounce(searchText: String) {
        if (searchText.isNotEmpty()) {
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

        private const val PAGE_SIZE = 20
    }
}
