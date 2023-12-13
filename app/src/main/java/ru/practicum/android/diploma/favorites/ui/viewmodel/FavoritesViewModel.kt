package ru.practicum.android.diploma.favorites.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorites.ui.models.FavoritesPlaceholderType
import ru.practicum.android.diploma.favorites.ui.models.FavoritesScreenState

class FavoritesViewModel : ViewModel() {

    private var isClickAllowed = true

    private val _screenState = MutableLiveData<FavoritesScreenState>(
        FavoritesScreenState.Placeholder(
            FavoritesPlaceholderType.PLACEHOLDER_EMPTY_LIST
        )
    )
    val screenState: LiveData<FavoritesScreenState> = _screenState

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
