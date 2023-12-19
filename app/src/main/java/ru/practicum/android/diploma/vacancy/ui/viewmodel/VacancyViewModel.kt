package ru.practicum.android.diploma.vacancy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

class VacancyViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val favoritesInteractor: FavoritesInteractor,
    private val vacancyInteractor: VacancyInteractor,
) : ViewModel() {

    private var isClickAllowed = true

    init {
        getData()
    }

    private val _screenState = MutableLiveData<VacancyState>(VacancyState.Loading)
    val screenState = _screenState

    private val _onClickDebounce = MutableLiveData<Boolean>()
    val onClickDebounce = _onClickDebounce

    private var _inFavouritesMutable = MutableLiveData<Boolean>()
    val inFavouritesMutable = _inFavouritesMutable

    private fun getData() {
        val id = savedStateHandle.get<String>(BUNDLE_KEY)
        viewModelScope.launch {
            if (favoritesInteractor.inFavourites(id.toString())) {
                getFavouritesVacancy(id.toString())
            } else {
                getDetailsVacancy(id.toString())
            }
        }
    }

    private fun getDetailsVacancy(id: String) {
        viewModelScope.launch {
            vacancyInteractor.getSelectedVacancy(id).collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun getFavouritesVacancy(id: String) {
        viewModelScope.launch {
            val vacancy = favoritesInteractor.getVacancyById(id)
            _screenState.value = VacancyState.Success(vacancy)
        }
    }

    private fun processResult(detailsVacancy: DetailsVacancy?, message: String?) {
        when (message) {
            SERVER_ERROR -> {
                _screenState.value = VacancyState.Error
            }

            else -> {
                if (detailsVacancy != null) {
                    _screenState.value = VacancyState.Success(detailsVacancy)
                } else {
                    _screenState.value = VacancyState.Error
                }
            }
        }
    }

    fun inFavourites(id: String) {
        viewModelScope.launch {
            _inFavouritesMutable.value = favoritesInteractor.inFavourites(id)
        }
    }

    fun addFavourites(vacancy: DetailsVacancy, isFavourites: Boolean) {
        viewModelScope.launch {
            if (isFavourites) {
                favoritesInteractor.insertVacancy(vacancy)
            } else {
                favoritesInteractor.deleteVacancy(vacancy)
            }
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
