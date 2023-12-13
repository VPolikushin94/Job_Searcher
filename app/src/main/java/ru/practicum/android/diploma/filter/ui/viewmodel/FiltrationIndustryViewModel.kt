package ru.practicum.android.diploma.filter.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.ui.models.FilterIndustryScreenState

class FiltrationIndustryViewModel : ViewModel() {

    private val _screenState = MutableLiveData(false)

}
