package ru.practicum.android.diploma.search.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.core.models.Vacancy
import ru.practicum.android.diploma.core.models.toVacancy
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.models.ErrorType
import ru.practicum.android.diploma.search.domain.models.Resource
import ru.practicum.android.diploma.search.domain.models.SearchVacancyResult
import ru.practicum.android.diploma.search.ui.models.SearchPlaceholderType
import ru.practicum.android.diploma.search.ui.models.SearchScreenState
import ru.practicum.android.diploma.util.SingleEventLiveData
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val application: Application,
) : ViewModel() {

    private var isClickAllowed = true

    private val searchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY_MILLIS, viewModelScope, true) {
        searchVacancy(it, false, true, false)
    }

    private val _screenState = MutableLiveData<SearchScreenState>(
        SearchScreenState.Placeholder(
            SearchPlaceholderType.PLACEHOLDER_NOT_SEARCHED_YET, false
        )
    )
    val screenState: LiveData<SearchScreenState> = _screenState

    private val _btnFilterState = MutableLiveData<Boolean>()
    val btnFilterState: LiveData<Boolean> = _btnFilterState

    private val _triggerClearAdapter = SingleEventLiveData<Boolean>()
    val triggerClearAdapter: LiveData<Boolean> = _triggerClearAdapter

    private var _searchedText: String = ""
    private var hasSearchBlocked = false

    private var page = 0
    private var pages = 0
    private var found = 0
    private var isNextPageLoading = false

    private var cachedVacancyList: MutableList<Vacancy> = mutableListOf()

    fun searchVacancy(
        searchText: String,
        isPagingSearch: Boolean,
        isDebounceSearch: Boolean,
        isUpdatedFilterSearch: Boolean
    ) {
        if (_searchedText != searchText && !isPagingSearch) {
            clearPagingVariables()
        }
        if (isSearchCanceled(searchText, isPagingSearch, isDebounceSearch, isUpdatedFilterSearch)) {
            return
        }
        if (searchText.isNotEmpty()) {
            if (isPagingSearch) {
                isNextPageLoading = true
                _screenState.value = SearchScreenState.Loading(true)
            } else {
                _screenState.value = SearchScreenState.Loading(false)
            }
            viewModelScope.launch(Dispatchers.IO) {
                _searchedText = searchText
                searchInteractor.searchVacancy(
                    searchText, page, PAGE_SIZE
                ).collect {
                    processResult(it)
                    isNextPageLoading = false
                }
            }
        }
    }

    private fun clearPagingVariables() {
        page = 0
        pages = 0
        found = 0
        cachedVacancyList.clear()
        _triggerClearAdapter.value = true
    }

    fun getSearchedText(): String {
        return _searchedText
    }

    fun areFiltersEmpty() {
        viewModelScope.launch {
            val isEmpty = searchInteractor.areFiltersEmpty()
            _btnFilterState.postValue(isEmpty)
        }
    }

    private suspend fun areFiltersChanged(): Boolean {
        return searchInteractor.areFiltersChanged()
    }

    @Suppress(
        "ReturnCount", "CollapsibleIfStatements", "CyclomaticComplexMethod", "ComplexCondition"
    )
    private fun isSearchCanceled(
        searchText: String, isPagingSearch: Boolean, isDebounceSearch: Boolean, isUpdatedFilterSearch: Boolean
    ): Boolean {
        if (searchText.isEmpty()) {
            _screenState.value = SearchScreenState.Placeholder(SearchPlaceholderType.PLACEHOLDER_GOT_EMPTY_LIST, false)
            return true
        }
        if (screenState.value != SearchScreenState.Placeholder(
                SearchPlaceholderType.PLACEHOLDER_SERVER_ERROR, false
            ) && screenState.value != SearchScreenState.Placeholder(
                SearchPlaceholderType.PLACEHOLDER_NO_INTERNET, false
            ) || isDebounceSearch
        ) {
            if ((_searchedText == searchText || hasSearchBlocked) && !isPagingSearch && !isUpdatedFilterSearch) {
                return true
            }
        }
        if (isNextPageLoading) {
            return true
        }
        return page == pages && pages != 0
    }

    fun getCachedVacancySearchResult() {
        viewModelScope.launch(Dispatchers.IO) {
            if (areFiltersChanged()) {
                withContext(Dispatchers.Main) {
                    clearPagingVariables()
                    searchVacancy(_searchedText, false, false, true)
                }
            } else {
                if (screenState.value is SearchScreenState.Content || screenState.value == SearchScreenState.Placeholder(
                        SearchPlaceholderType.PLACEHOLDER_SERVER_ERROR, true
                    ) || screenState.value == SearchScreenState.Placeholder(
                        SearchPlaceholderType.PLACEHOLDER_NO_INTERNET, true
                    )
                ) {
                    _screenState.postValue(
                        SearchScreenState.Content(
                            cachedVacancyList, found
                        )
                    )
                }
            }
        }
    }

    private fun setContentState(searchResult: SearchVacancyResult) {
        if (searchResult.vacancyList.isEmpty()) {
            _screenState.postValue(
                SearchScreenState.Placeholder(
                    SearchPlaceholderType.PLACEHOLDER_GOT_EMPTY_LIST, false
                )
            )
        } else {
            val vacancyList = searchResult.vacancyList.map { it.toVacancy(application) }
            cachedVacancyList.addAll(vacancyList)
            _screenState.postValue(
                SearchScreenState.Content(
                    cachedVacancyList, searchResult.found
                )
            )
            found = searchResult.found
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
                            SearchPlaceholderType.PLACEHOLDER_NO_INTERNET, isNextPageLoading
                        )
                    )

                    ErrorType.SERVER_ERROR -> _screenState.postValue(
                        SearchScreenState.Placeholder(
                            SearchPlaceholderType.PLACEHOLDER_SERVER_ERROR, isNextPageLoading
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
            _screenState.value = SearchScreenState.Placeholder(
                SearchPlaceholderType.PLACEHOLDER_NOT_SEARCHED_YET, false
            )
        } else {
            hasSearchBlocked = false
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

        private const val PAGE_SIZE = 20
    }
}
