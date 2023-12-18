package ru.practicum.android.diploma.filter.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.ui.utils.SingleLiveEvent
import ru.practicum.android.diploma.util.debounce

class FiltrationViewModel : ViewModel() {

    private val _screenState = MutableLiveData(false)

    private val showIndustryTrigger = SingleLiveEvent<Industry?>()
    fun getShowIndustryTrigger(): LiveData<Industry?> = showIndustryTrigger

    private var isClickAllowed = true
    private val onTrackClickDebounce = debounce<Boolean>(CLICK_DEBOUNCE_DELAY, viewModelScope, false) {
        isClickAllowed = it
    }

    companion object {
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
