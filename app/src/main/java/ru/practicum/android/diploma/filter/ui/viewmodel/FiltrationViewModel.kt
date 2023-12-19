package ru.practicum.android.diploma.filter.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FilterSavingInteractor
import ru.practicum.android.diploma.filter.domain.models.FiltrationSettings
import ru.practicum.android.diploma.util.debounce

class FiltrationViewModel(
    private val filterSavingInteractor: FilterSavingInteractor
) : ViewModel() {

    private val observeLiveData = MutableLiveData(false)
    fun observeData(): LiveData<Boolean> = observeLiveData

    private val filtrationSettingsLiveData = MutableLiveData<FiltrationSettings>()
    fun observeFiltrationSettings(): LiveData<FiltrationSettings> = filtrationSettingsLiveData

    private var isClickAllowed = true
    private val onTrackClickDebounce = debounce<Boolean>(CLICK_DEBOUNCE_DELAY, viewModelScope, false) {
        isClickAllowed = it
    }

    init {
        getFiltrationSettings()
    }

    fun setSalary(inputText: String) {
        filterSavingInteractor.setSalary(inputText)
    }

    fun updateSalary(inputText: String) {
        if (inputText != (filtrationSettingsLiveData.value?.salary ?: "")) observeLiveData.postValue(true)
    }

    fun getFiltrationSettings() {
        viewModelScope.launch {
            val settings = filterSavingInteractor.getFilters()
            if (filtrationSettingsLiveData.value != null) observeLiveData.postValue(true)
            postSettings(settings)
        }
    }

    private fun postSettings(settings: FiltrationSettings) {
        filtrationSettingsLiveData.postValue(settings)
    }

    fun saveSalaryOnlyItem(isChecked: Boolean) {
        filterSavingInteractor.setSalaryOnly(isChecked)
        observeLiveData.postValue(true)
    }

    fun deleteAllFilters() {
        filterSavingInteractor.allDelete()
        getFiltrationSettings()
    }

    companion object {
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
